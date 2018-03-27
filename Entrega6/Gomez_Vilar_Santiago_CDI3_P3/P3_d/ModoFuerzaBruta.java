import java.util.ArrayList;

public class ModoFuerzaBruta {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);

		ClaseAFB objA = new ClaseAFB(0, ejecuciones, numThreads);
		ArrayList<Thread> misHilos = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			String nombreHilo = Integer.toString(i+1);
			misHilos.add(new Thread(new MiThreadFB(objA), nombreHilo));
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

class ClaseAFB {
	private int espera;
	public int nActual;
	public int nSiguiente;
	public int numThreads;

	public ClaseAFB(int espera, int nActual, int numThreads) {
		this.espera = espera;
		this.nActual = nActual;
		this.numThreads = numThreads;
		this.nSiguiente = 1;
		
	}

	public void EnterAndWait() {
		try {
			nSiguiente = (Integer.parseInt(Thread.currentThread().getName())) % numThreads + 1;
			Thread.sleep(espera);
		 --nActual;
		} catch (InterruptedException e) {
		}
	}
}

class MiThreadFB implements Runnable {

	ClaseAFB objA;

	public MiThreadFB(ClaseAFB objA) {
		this.objA = objA;
	}

	@Override
	public void run() {
		synchronized (objA) {
			while (objA.nActual > 0) {
				if (Thread.currentThread().getName().equals(Integer.toString(objA.nSiguiente))) {
					objA.EnterAndWait();
					objA.notifyAll();
				}else {
					try {
						objA.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
}
