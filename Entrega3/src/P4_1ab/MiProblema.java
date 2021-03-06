package P4_1ab;
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
		ArrayList<MiThreadApartadoB> threadList = new ArrayList<MiThreadApartadoB>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i + 1);
			threadList.add(new MiThreadApartadoB(nombreHilo));
			threadList.get(i).start();
		}

		for (MiThreadApartadoB miHilo : threadList) {
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