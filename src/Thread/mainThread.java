package Thread;

public class mainThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		
		if (args.length > 2 || args.length == 0) {
			System.out.println("Error in arguments");
		} else {
			int numHilos = Integer.parseInt(args[0]);
			int tiempo = Integer.parseInt(args[1]);
			HiloT[] hilosT = new HiloT[numHilos];
			Runnable[] hilosR = new HiloR[numHilos];
		
			for (int i = 0; i < numHilos; i++) {
				hilosT[i] = new HiloT((i + 1), tiempo);
				hilosR[i] = new HiloR((i + 1), tiempo);
				// hilosT[i].run();
			}
			for (int i = 0; i < numHilos; i++) {
				hilosT[i].start();
			}			
		
			for (int i = 0; i < hilosR.length; i++) {
				new Thread(hilosR[i]).start();
			}
		
		}
		
	}

}
