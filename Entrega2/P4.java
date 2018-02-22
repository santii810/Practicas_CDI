/*
 * b)	¿Cuál es el resultado de tu código? ¿En qué orden se imprimen los hilos?
El resultado de la ejecución no coincide al hacer múltiples pruebas, habitualmente 
finalizan unos cuantos hilos antes de que el programa principal finalice y otros después.
Los hilos no finalizan secuencialmente pero sí con un cierto orden, es decir, es muy 
probable que el hilo numero 1 finalice antes que el hilo numero 10.

 * 
 * 
 * 
 * 
 * d) ) ¿Funciona exactamente igual? ¿Afecta la ejecución de los subprocesos en ejecución?
 Aparentemente el resultado de las 2 formas es identico, sin embargo si lo haces con isAlive()
  es posible que el bucle de comprobacion compruebe hilos que aun no han empezado por lo que 
  no los comprobaria 
  No afecta a los subprocesos ya que el hilo que se pausa es el main
 
 * 
 * 
 * 
 * */

import java.util.ArrayList;

public class P4 {
	final static int NUMBER_OF_THREADS = 32;

	public static void main(String[] args) {
		ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(NUMBER_OF_THREADS);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i + 1);
			threadList.add(new MiHilo(nombreHilo));
			threadList.get(i).start();
		}
// apartado C
//		for (Thread thread : threadList) {
//			while (thread.isAlive()) {
//			}
//		}
		
//apartado d
		for (Thread thread : threadList) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
		
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Program of exercise P4 has terminated. Time: " + endTime);	}
}

class MiHilo extends Thread {
	public MiHilo(String nombreHilo) {
		super(nombreHilo);
	}

	@Override
	public void run() {
		System.out.println("Bye, this was " + currentThread().getName());
	}
}
