package P4_1d;

/*
 * 
 * 
 * 
 * 
 * 
 * */

import java.util.ArrayList;

public class MiProblema {
	final static int NUMBER_OF_THREADS = 10;

	@SuppressWarnings("deprecation")
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
	}

}