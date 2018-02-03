package Runnable;

public class HiloR implements Runnable {
	int nombre;
	int tiempo;

	public HiloR(int nombreHilo, int tiempo) {
		this.nombre = nombreHilo;
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		System.out.println("Hello world, I’m a java thread number " + nombre);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bye, this was thread number " + nombre);

	}

}