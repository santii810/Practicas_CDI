import java.util.ArrayList;

public class P3 {

	public static void main(String[] args) {

		if (args.length == 1) {

			int numHilos = Integer.parseInt(args[0]);

			ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(numHilos);
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < numHilos; i++) {
				String nombreHilo = "Thread number " + (i + 1);
				threadList.add(new MiHilo(nombreHilo));
				threadList.get(i).start();
			}

			for (MiHilo miHilo : threadList) {
				try {
					miHilo.join();
				} catch (InterruptedException e) {
				}
			}

			long endTime = System.currentTimeMillis() - startTime;
			// System.out.println("Program of exercise P4 has terminated. Time: " +
			// endTime);
			System.out.println(numHilos + "\t" + endTime);
		}
	}
}

class MiHilo extends Thread {
	public MiHilo(String nombreHilo) {
		super(nombreHilo);
	}

	@Override
	public void run() {
		// System.out.println("Inicio del thread " + currentThread().getName());
		for (int i = 0; i < 1000000; i++) {
			double d = Math.tan(Math.atan(Math.tan(
					Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(123456789.123456789))))))))));
			Math.cbrt(d);
		}
		// System.out.println("Fin del thread " + currentThread().getName());
	}
}
