package P4_1c;

/*
 * 
 * 
 * 
 * 
 * 
 * */

import java.util.ArrayList;

public class MiProblema {
	final static int NUMBER_OF_THREADS = 50;

	public static void main(String[] args) {
		ArrayList<MiThreadApartadoC> threadList = new ArrayList<MiThreadApartadoC>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i + 1);
			threadList.add(new MiThreadApartadoC(nombreHilo));
			threadList.get(i).start();
		}

		for (MiThreadApartadoC miHilo : threadList) {
			try {
				miHilo.join();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Program of exercise P4 has terminated");
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