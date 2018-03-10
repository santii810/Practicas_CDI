import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MiProblema {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Numero de argumentos incorrecto");
			System.exit(1);
		}

		int numAumentos = Integer.parseInt(args[0]);
		int numThreads = Integer.parseInt(args[1]);
		
		ArrayList<MiThread> misHilos = new ArrayList<>();
		Contador cont = new Contador(0);
		for (int i = 0; i < numThreads; i++) {
			misHilos.add(new MiThread("Hilo " + i, cont, numAumentos));
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
	int numAumentos;

	public MiThread(String nombreHilo, Contador cont, int numAumentos) {
		super(nombreHilo);
		this.cont = cont;
		this.numAumentos = numAumentos;
	}

	@Override
	public void run() {
		for (int i = 0; i <= numAumentos; i++) {
			int inicial = cont.get();
			cont.incrementar(1);
			int finalValue = cont.get();
			System.out.println("Hi, i'm " + currentThread().getName() + " the acum value is " + inicial
					+ " and I increment to " + finalValue);
		}
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
