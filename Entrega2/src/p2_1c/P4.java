package p2_1c;
import java.util.ArrayList;

public class P4 {
	final static int NUMBER_OF_THREADS = 32;

	public static void main(String[] args) {
		ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i+1);
			threadList.add(new MiHilo(nombreHilo));
			new Thread(threadList.get(i)).start();
		}
		
		
		for (MiHilo miHilo : threadList) {
			while(miHilo.isAlive()){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Program of exercise P4 has terminated");
	}
}

class MiHilo extends Thread {
	public MiHilo(String nombreHilo) {
		super(nombreHilo);
	}
	@Override
	public void run() {
		System.out.println("Bye, this was " + currentThread().getName());
	}
}
