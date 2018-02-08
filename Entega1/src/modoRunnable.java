
public class modoRunnable {

	public static void main(String[] args) {

		if (args.length > 1 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			Thread[] hilosR = new Thread[numHilos];

			for (int i = 0; i < numHilos; i++) {
				String nombreHilo = "Thread " + (i + 1);
				hilosR[i] = new Thread(new HiloRun(nombreHilo), nombreHilo);
			}

			for (int i = 0; i < hilosR.length; i++) {
				new Thread(hilosR[i]).start();
			}
		}
		System.out.println("Program of exercise P4 has terminated");
	}
}

class HiloRun implements Runnable {
	String nombre;

	public HiloRun(String nombreHilo) {
		this.nombre = nombreHilo;
	}

	@Override
	public void run() {
		System.out.println("Hello world, I’m a java thread number " + nombre);
	}

}
