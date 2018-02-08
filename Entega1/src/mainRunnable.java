
public class mainRunnable {

	public static void main(String[] args) {

		if (args.length > 2 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			int tiempo = Integer.parseInt(args[1]);
			Runnable[] hilosR = new HiloRunnable[numHilos];

			for (int i = 0; i < numHilos; i++) {
				hilosR[i] = new HiloRunnable((i + 1), tiempo);
			}		

			for (int i = 0; i < hilosR.length; i++) {
				new Thread(hilosR[i]).start();
			}

		}
		
		System.out.println("Program of exercise P4 has terminated");

	}

}

