import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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

	public MiThread(String nombreHilo, Contador cont) {
		super(nombreHilo);
		this.cont = cont;
	}

	@Override
	public void run() {
		int num1 = (int) (Math.random() * 10);
		// synchronized (cont) {
		for (int i = 0; i <= num1; i++) {
			int inicial = cont.get();
			cont.incrementar(1);
			int finalValue = cont.get();
			System.out.println("Hi, i'm " + currentThread().getName() + " the acum value is " + inicial
					+ " and I increment to " + finalValue);

			try {
				Thread.sleep((long) (Math.random() * 100));
			} catch (InterruptedException e) {
			}
		}
	}
	// }
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
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
			}
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
