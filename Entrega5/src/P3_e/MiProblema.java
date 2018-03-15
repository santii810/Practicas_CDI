package P3_e;
import java.util.ArrayList;

public class MiProblema {
	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);

		ClaseA objA = new ClaseA(10);
		ArrayList<Thread> misHilos = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			String nombreHilo = "Hilo " + i;
			misHilos.add(new Thread(new MiThread(objA), nombreHilo));
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

	public ClaseA(int espera) {
		this.espera = espera;
	}

	public void EnterAndWait() {
		try {
			System.out.println("Ejecutando " + Thread.currentThread().getName());
			Thread.sleep(espera);
			System.out.println(Thread.currentThread().getName() + " acabando de ejecutarse.");
		} catch (InterruptedException e) {
		}
	}
}

class MiThread implements Runnable {

	ClaseA objA;

	public MiThread(ClaseA objA) {
		this.objA = objA;
	}

	@Override
	public void run() {
		synchronized (objA) {
			objA.EnterAndWait();
		}
	}
}
