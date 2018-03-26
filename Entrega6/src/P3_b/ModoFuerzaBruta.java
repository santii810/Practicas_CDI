package P3_b;


import java.util.ArrayList;

public class ModoFuerzaBruta {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);

		ClaseA_FB objA = new ClaseA_FB(2, ejecuciones);
		ArrayList<Thread> misHilos = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			String nombreHilo = Integer.toString(i + 1);
			misHilos.add(new Thread(new MiThread_FB(objA), nombreHilo));
		}

		long startTime = System.currentTimeMillis();
		for (Thread thread : misHilos) {
			thread.start();
		}
		for (Thread thread : misHilos) {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}

		long endTime = System.currentTimeMillis() - startTime;
		System.out.println(ejecuciones + "\t" + endTime);
	}

}

class ClaseA_FB {
	private int espera;
	public int nActual;
	public int nHUltimo;

	public ClaseA_FB(int espera, int nActual) {
		this.espera = espera;
		this.nActual = nActual;
	}

	public void EnterAndWait() {
		try {
			nHUltimo = Integer.parseInt(Thread.currentThread().getName());
			Thread.sleep(espera);
			--nActual;
		} catch (InterruptedException e) {
		}
	}
}

class MiThread_FB implements Runnable {

	ClaseA_FB objA;

	public MiThread_FB(ClaseA_FB objA) {
		this.objA = objA;
	}

	@Override
	public void run() {
		synchronized (objA) {
			while (objA.nActual > 0) {
				if (!Thread.currentThread().getName().equals(Integer.toString(objA.nHUltimo))) {
					objA.EnterAndWait();
					objA.notifyAll();
				} else {
					try {
						objA.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
}
