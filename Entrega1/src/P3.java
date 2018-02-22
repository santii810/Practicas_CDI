
public class P3 {

	public static void main(String[] args) {

		if (args.length > 2 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			int tiempo = Integer.parseInt(args[1]);

			Thread[] hilosR = new Thread[numHilos];

			for (int i = 0; i < numHilos; i++) {
				String nombreHilo = Integer.toString(i + 1);
				hilosR[i] = new Thread(new HiloRunnable(nombreHilo,tiempo), nombreHilo);
				new Thread(hilosR[i]).start();
			}

		}
		
		System.out.println("Program of exercise P3 has terminated");		
	}
}

class HiloRunnable implements Runnable {
	String nombre;
	int tiempo;

	public HiloRunnable(String nombreHilo, int tiempo) {
		this.nombre = nombreHilo;
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		System.out.println("Hello world, I’m a java thread number " + Thread.currentThread().getName());
		try {
			Thread.sleep(tiempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bye, this was thread number " + nombre);

	}

}