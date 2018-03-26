

import java.util.ArrayList;

public class MiProblema {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);

		ClaseA objA = new ClaseA(10, ejecuciones);
		ArrayList<Thread> misHilos = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			String nombreHilo = Integer.toString(i+1);
			misHilos.add(new Thread(new MiThread(objA), nombreHilo));
		}

		for (Thread thread : misHilos) {
			thread.start();
		}
		for (Thread thread : misHilos) {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Program of exercise P4 has terminated");

	}

}

class ClaseA {
	private int espera;
	public int nActual;
	public int nHUltimo;

	public ClaseA(int espera, int nActual) {
		this.espera = espera;
		this.nActual = nActual;
	}

	public void EnterAndWait() {
		try {
			nHUltimo = Integer.parseInt(Thread.currentThread().getName());
			System.out.println("Ejecutando hilo " + Thread.currentThread().getName());
			Thread.sleep(espera);
			System.out.println("Hilo " + Thread.currentThread().getName() + " acabando de ejecutarse. Quedan " + --nActual
					+ " ejecuciones.");

		} catch (InterruptedException e) {
		}
	}
}

class MiThread implements Runnable {

	ClaseA objA;

	public MiThread(ClaseA objA) {
		this.objA = objA;
	}

	@Override
	public void run() {
		synchronized (objA) {
			while (objA.nActual > 0) {
				if (!Thread.currentThread().getName().equals(Integer.toString(objA.nHUltimo))) {
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
