
package p2_2e;

import java.util.ArrayList;

public class P3 {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Numero de argumentos incorrecto");
		} else {
			int numHilos = Integer.parseInt(args[0]);

			ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(numHilos);
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < numHilos; i++) {
				String nombreHilo = "Thread number " + (i + 1);
				threadList.add(new MiHilo(nombreHilo, numHilos));
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

	int numHilos;

	public MiHilo(String nombreHilo, int numHilos) {
		super(nombreHilo);
		this.numHilos = numHilos;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000000 / numHilos; i++) {
			double d = Math.tan(Math.atan(Math.tan(
					Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(123456789.123456789))))))))));
			Math.cbrt(d);
		}
	}
}
