
public class P4 {

	public static void main(String[] args) {

		if (args.length > 1 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			Runnable[] hilosR = new HiloR[numHilos];

			for (int i = 0; i < numHilos; i++) {
				hilosR[i] = new HiloR("Thread " + (i + 1));
			}

			for (int i = 0; i < hilosR.length; i++) {
				new Thread(hilosR[i]).start();
			}
		}
		System.out.println("Program of exercise P4 has terminated");
	}
}

class HiloR implements Runnable {
	String nombre;

	public HiloR(String nombreHilo) {
		this.nombre = nombreHilo;
	}

	@Override
	public void run() {
		System.out.println("Hello world, I’m a java thread number " + nombre);
	}

}
