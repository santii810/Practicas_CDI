package P4_b;

import java.util.ArrayList;

public class MiProblema {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int ejecuciones = Integer.parseInt(args[1]);
		String[] colores = new String[] { "rojo", "negro" };

		ClaseA objA = new ClaseA(10, ejecuciones);
		ArrayList<Thread> misHilos = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			String nombreHilo = Integer.toString(i + 1);
			String color = colores[(int) (Math.random()*2)];
			misHilos.add(new Thread(new MiThread(objA, color), nombreHilo));
		}

		for (Thread thread : misHilos) {
			thread.start();
		}
		for (Thread thread : misHilos) {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Program of exercise P4 has terminated");

	}

}

class ClaseA {
	private int espera;
	public int nActual;
	public String lastColor;

	public ClaseA(int espera, int nActual) {
		this.espera = espera;
		this.nActual = nActual;
		lastColor = "";
	}

	public void EnterAndWait(String color) {
		try {
			lastColor = color;
			System.out.println("Ejecutando hilo " + Thread.currentThread().getName() + " de color "
					+ color);
			Thread.sleep(espera);
			System.out.println("Hilo " + Thread.currentThread().getName() + " acabando de ejecutarse. Quedan "
					+ --nActual + " ejecuciones.");

		} catch (InterruptedException e) {
		}
	}
}

class MiThread implements Runnable {

	ClaseA objA;
	String color;

	public MiThread(ClaseA objA, String color) {
		this.objA = objA;
		this.color = color;
	}

	@Override
	public void run() {
		synchronized (objA) {
			while (objA.nActual > 0) {
				if (!objA.lastColor.equals(this.color)) {
					objA.EnterAndWait(this.color);
					objA.notifyAll();
				} else {
					try {
						objA.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
}
