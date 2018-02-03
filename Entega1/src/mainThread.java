
public class mainThread {
	public static void main(String[] args) {

		if (args.length > 2 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			int tiempo = Integer.parseInt(args[1]);
			HiloThread[] hilosT = new HiloThread[numHilos];

			for (int i = 0; i < numHilos; i++) {
				hilosT[i] = new HiloThread((i + 1), tiempo);
			}
			for (int i = 0; i < numHilos; i++) {
				hilosT[i].start();
			}
		}

	}

}
