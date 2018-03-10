import java.lang.management.LockInfo;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MiProblema {

	private static final int NUM_HILOS = 10;

	public static void main(String[] args) {

		ArrayList<MiThread> misHilos = new ArrayList<>();
		Contador cont = new Contador(0);
		for (int i = 0; i < NUM_HILOS; i++) {
			misHilos.add(new MiThread("Hilo " + i, cont));
		}
		long startTime = System.currentTimeMillis();

		for (MiThread miThread : misHilos) {
			miThread.start();
		}
		for (MiThread miThread : misHilos) {
			try {
				miThread.join();
			} catch (InterruptedException e) {
			}
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Program of P4 has termitated");

	}

}

class MiThread extends Thread {

	private Contador cont;
	private ReentrantLock lock;

	public MiThread(String nombreHilo, Contador cont) {
		super(nombreHilo);
		this.cont = cont;
		lock = new ReentrantLock();
	}

	@Override
	public void run() {
		int num1 = (int) (Math.random() * 100);
		// synchronized (cont) {
		try {
			lock.lock();
			for (int i = 0; i <= num1; i++) {
				int inicial = cont.get();
				cont.incrementar(1);
				int finalValue = cont.get();
				System.out.println("Hi, i'm " + currentThread().getName() + " the acum value is " + inicial
						+ " and I increment to " + finalValue);
			}
		} finally {
			lock.unlock();
		}
		// }
	}
}

class Contador {
	// private int acum;
	private AtomicInteger acum;

	public Contador(int inicial) {
		acum = new AtomicInteger(inicial);
	}

	public int incrementar(int n) {

		for (int i = 0; i <= n; i++) {
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
