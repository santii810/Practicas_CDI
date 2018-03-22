
import java.util.ArrayList;

public class MiProblema {

	static final int NUM_HILOS = 3;

	public static void main(String args[]) {
		
		ClaseA objA = new ClaseA(100);
		ArrayList<MiThread> misRunnable = new ArrayList<>();
		ArrayList<Thread> misThread = new ArrayList<>();

		for (int i = 0; i < NUM_HILOS; i++) {
			misRunnable.add(new MiThread(objA));
		}

		misRunnable.get(0).setComp(misRunnable.get(1));
		misRunnable.get(1).setComp(misRunnable.get(0));
	
		
		for (int i = 0; i < NUM_HILOS; i++) {
			misThread.add(new Thread(misRunnable.get(i), "Hilo " + (i + 1)));
		}

		for (Thread thread : misThread) {
			thread.start();
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
		}
		
		synchronized (misRunnable.get(0)) {
			misRunnable.get(0).notify();			
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		for (Thread thread : misThread) {
			try {
				thread.interrupt();
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
			System.out.println("Ejecutando hilo " + Thread.currentThread().getName());
			Thread.sleep(espera);
			System.out.println("Hilo " + Thread.currentThread().getName() + " acabando de ejecutarse.");
		} catch (InterruptedException e) {
		}
	}
}

class MiThread implements Runnable {

	ClaseA objA;
	String color;
	MiThread comp;

	public MiThread(ClaseA objA) {
		this.objA = objA;
	}

	public void setComp(MiThread miThread) {
		this.comp = miThread;

	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					break;
					
				}
			}

			synchronized (comp) {
				this.objA.EnterAndWait();
				comp.notify();
			}
		}
	}
}
