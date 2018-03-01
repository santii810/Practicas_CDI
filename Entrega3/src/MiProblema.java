import java.util.ArrayList;

public class MiProblema {
	final static int NUMBER_OF_THREADS = 50;
	// APARTADO B
	// public static void main(String[] args) {
	// ArrayList<MiThreadApartadoB> threadList = new
	// ArrayList<MiThreadApartadoB>(NUMBER_OF_THREADS);
	// for (int i = 0; i < NUMBER_OF_THREADS; i++) {
	// String nombreHilo = "Thread number " + (i + 1);
	// threadList.add(new MiThreadApartadoB(nombreHilo));
	// threadList.get(i).start();
	// }
	//
	// for (MiThreadApartadoB miHilo : threadList) {
	// try {
	// miHilo.join();
	// } catch (InterruptedException e) {
	// }
	// }
	// System.out.println("Program of exercise P4 has terminated");
	// }
//}
 
	// APARTADO C
	// public static void main(String[] args) {
	// ArrayList<MiThreadApartadoC> threadList = new
	// ArrayList<MiThreadApartadoC>(NUMBER_OF_THREADS);
	// for (int i = 0; i < NUMBER_OF_THREADS; i++) {
	// String nombreHilo = "Thread number " + (i + 1);
	// threadList.add(new MiThreadApartadoC(nombreHilo));
	// threadList.get(i).start();
	// }
	//
	// for (MiThreadApartadoC miHilo : threadList) {
	// try {
	// miHilo.join();
	// } catch (InterruptedException e) {
	// }
	// }
	// System.out.println("Program of exercise P4 has terminated");
	// }
	
	public static void main(String[] args) {
		ArrayList<MiThread> threadList = new ArrayList<MiThread>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i + 1);
			threadList.add(new MiThread(nombreHilo));
			threadList.get(i).start();
		}

		int randomTime = (int) (Math.random() * (NUMBER_OF_THREADS * 2));
		for (MiThread miThread : threadList) {
			try {
				Thread.sleep(randomTime);
			} catch (InterruptedException e) {
			}
			miThread.interrupt();
		}

		for (MiThread miHilo : threadList) {
			try {
				miHilo.join();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Program of exercise P4 has terminated");
	}
}

class MiThreadApartadoB extends Thread {

	static private Integer miSuma = 0;

	public MiThreadApartadoB(String nombreHilo) {
		super(nombreHilo);
	}

	@Override
	public void run() {
		for (int i = 0; i <= 100; i++) {
			miSuma += i;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out
				.println("Bye, this was " + currentThread().getName() + " and finish with sum = " + miSuma.toString());
	}
}

class MiThreadApartadoC extends Thread {

	private ThreadLocal<Integer> miSuma;

	public MiThreadApartadoC(String nombreHilo) {
		super(nombreHilo);
		miSuma = new ThreadLocal<>();
	}

	@Override
	public void run() {
		miSuma.set(0);
		for (int i = 0; i <= 100; i++) {
			miSuma.set(miSuma.get() + i);
		}
		System.out.println(
				"Bye, this was " + currentThread().getName() + " and finish with sum = " + miSuma.get().toString());
	}
}

class MiThread extends Thread {
	boolean negative = true;
	double pi = 0.0;

	public MiThread(String nombreHilo) {
		super(nombreHilo);
	}

	@Override
	public void run() {
		try {
			for (int i = 3; i < 100000; i += 2) {
				Thread.sleep(1);
				if (negative)
					pi -= 1.0 / i;
				else
					pi += 1.0 / i;
				negative = !negative;
			}

			pi += 1.0;
			pi *= 4.0;
		} catch (InterruptedException e) {
		} finally {
			pi += 1.0;
			pi *= 4.0;
			System.out.println("Bye, this was " + currentThread().getName() + " and finish with \t sum = " + pi);
		}
	

}}