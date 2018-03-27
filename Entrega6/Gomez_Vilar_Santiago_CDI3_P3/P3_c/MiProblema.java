
import java.util.ArrayList;

public class MiProblema {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);

		ClaseA objA = new ClaseA(2, ejecuciones, numThreads);
		ArrayList<MiThread> misRunnable = new ArrayList<>();
		ArrayList<Thread> misThread = new ArrayList<>();

		for (int i = 0; i < numThreads; i++) {
			misRunnable.add(new MiThread(objA));
		}

		misRunnable.get(misRunnable.size() - 1).setComp(misRunnable.get(0));
		for (int i = 0; i < numThreads - 1; i++) {
			misRunnable.get(i).setComp(misRunnable.get(i + 1));
		}

		for (int i = 0; i < numThreads; i++) {
			misThread.add(new Thread(misRunnable.get(i), Integer.toString(i + 1)));
		}

		for (Thread thread : misThread) {
			thread.start();
		}

	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		synchronized (misRunnable.get(0)) {
			misRunnable.get(0).notify();
		}

		for (Thread thread : misThread) {
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
	public int nSiguiente;
	public int numThreads;

	public ClaseA(int espera, int nActual, int numThreads) {
		this.espera = espera;
		this.nActual = nActual;
		this.numThreads = numThreads;
		this.nSiguiente = 1;

	}

	public void EnterAndWait() {
		try {
			nSiguiente = (Integer.parseInt(Thread.currentThread().getName())) % numThreads + 1;
			System.out.println("Ejecutando hilo " + Thread.currentThread().getName());
			Thread.sleep(espera);
			System.out.println("Hilo " + Thread.currentThread().getName() + " acabando de ejecutarse. Quedan "
					+ --nActual + " ejecuciones.");

		} catch (InterruptedException e) {
		}
	}
}

class MiThread implements Runnable {

	ClaseA objA;
	MiThread comp;

	public MiThread(ClaseA objA) {
		this.objA = objA;
	}

	public void setComp(MiThread miThread) {
		this.comp = miThread;

	}

	@Override
	public void run() {
		while (objA.nActual > 0) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					break;
				}
				synchronized (comp) {
					if (objA.nActual > 0)
						this.objA.EnterAndWait();
					comp.notify();
				}
			}
		}
	}
}
