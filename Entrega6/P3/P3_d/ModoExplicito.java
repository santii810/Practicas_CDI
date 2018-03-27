
import java.util.ArrayList;

public class ModoExplicito {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);

		ClaseAExplicita objA = new ClaseAExplicita(2, ejecuciones, numThreads);
		ArrayList<MiThreadExplicito> misRunnable = new ArrayList<>();
		ArrayList<Thread> misThread = new ArrayList<>();

		for (int i = 0; i < numThreads; i++) {
			misRunnable.add(new MiThreadExplicito(objA));
		}

		misRunnable.get(misRunnable.size() - 1).setComp(misRunnable.get(0));
		for (int i = 0; i < numThreads - 1; i++) {
			misRunnable.get(i).setComp(misRunnable.get(i + 1));
		}

		for (int i = 0; i < numThreads; i++) {
			misThread.add(new Thread(misRunnable.get(i), Integer.toString(i + 1)));
		}
		long startTime = System.currentTimeMillis();

		for (Thread thread : misThread) {
			thread.start();
		}

		try {
			Thread.sleep(10);
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

		long endTime = System.currentTimeMillis() - startTime;
		System.out.println(ejecuciones + "\t" + endTime);
	}

}

class ClaseAExplicita {
	private int espera;
	public int nActual;
	public int nSiguiente;
	public int numThreads;

	public ClaseAExplicita(int espera, int nActual, int numThreads) {
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

class MiThreadExplicito implements Runnable {

	ClaseAExplicita objA;
	MiThreadExplicito comp;

	public MiThreadExplicito(ClaseAExplicita objA) {
		this.objA = objA;
	}

	public void setComp(MiThreadExplicito miThread) {
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
