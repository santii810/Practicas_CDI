import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MiProblema {

	private static final int NUM_HILOS = 10;

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numIncrementos = Integer.parseInt(args[0]);

		ArrayList<MiThread> misHilos = new ArrayList<>();
		Contador cont = new Contador(0);
		for (int i = 0; i < NUM_HILOS; i++) {
			misHilos.add(new MiThread("Hilo " + i, cont, numIncrementos));
		}
		long startTime = System.nanoTime();

		for (MiThread miThread : misHilos) {
			miThread.start();
		}
		for (MiThread miThread : misHilos) {
			try {
				miThread.join();
			} catch (InterruptedException e) {
			}
		}
		long endTime = (System.nanoTime() - startTime)/1000;
		System.out.println(numIncrementos + "\t" + endTime);
	}
}

class MiThread extends Thread {

	private Contador cont;
	private ReentrantLock lock;
	static private int incrementos;

	public MiThread(String nombreHilo, Contador cont, int incrementos) {
		super(nombreHilo);
		this.cont = cont;
		lock = new ReentrantLock();
		this.incrementos = incrementos;
	}

	@Override
	public void run() {
		try {
			lock.lock();
			while (incrementos > 0) {
				cont.incrementar(1);
				incrementos--;
			}
		} finally {
			lock.unlock();
		}
	}
}

class Contador {
	private AtomicInteger acum;

	public Contador(int inicial) {
		acum = new AtomicInteger(inicial);
	}

	public int incrementar(int n) {

		for (int i = 0; i < n; i++) {
			acum.incrementAndGet();
		}
		return acum.get();
	}

	public int get() {
		return acum.get();

	}

	@Override
	public String toString() {
		return Integer.toString(acum.get());
	}
}
