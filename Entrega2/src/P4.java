import java.util.ArrayList;

public class P4 {
	final static int NUMBER_OF_THREADS = 32;

	public static void main(String[] args) {
		ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(NUMBER_OF_THREADS);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i + 1);
			threadList.add(new MiHilo(nombreHilo));
			new Thread(threadList.get(i)).start();
		}

		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Program of exercise P4 has terminated. Time: " + endTime);
	}
}

class MiHilo extends Thread {
	public MiHilo(String nombreHilo) {
		super(nombreHilo);
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000000; i++) {
			double d = Math.tan(Math.atan(Math.tan(
					Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(123456789.123456789))))))))));
			Math.cbrt(d);
		}
		//LOGGER.debug("Soy {}", Thread.currentThread().getName());
		System.out.println("Bye, this was " + currentThread().getName());
	}
}
