
public class mainThread {
	public static void main(String[] args) {

		if (args.length > 1 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			HiloThread[] hilosT = new HiloThread[numHilos];

			for (int i = 0; i < numHilos; i++) {
				hilosT[i] = new HiloThread("Thread " + (i + 1));
			}
			for (int i = 0; i < numHilos; i++) {
				hilosT[i].start();
			}
		}
	}
}
