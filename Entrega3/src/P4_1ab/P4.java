package P4_1ab;
/*
 * 
 * 
 * 
 * 
 * 
 * */




import java.util.ArrayList;

public class P4 {
	final static int NUMBER_OF_THREADS = 50;

	public static void main(String[] args) {
		ArrayList<MiHilo> threadList = new ArrayList<MiHilo>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			String nombreHilo = "Thread number " + (i+1);
			threadList.add(new MiHilo(nombreHilo));
			threadList.get(i).start();
		}
		
		for (MiHilo miHilo : threadList) {
			try {
				miHilo.join();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Program of exercise P4 has terminated");
	}
}

class MiHilo extends Thread {

	private Integer miSuma = 0;
	
	public MiHilo(String nombreHilo) {
		super(nombreHilo);
	}
	@Override
	public void run() {
		for (int i = 0; i <= 100; i++) {
			miSuma+= i;
		}		
		System.out.println("Bye, this was " + currentThread().getName() + " and finish with sum = "+ miSuma.toString());
	}
}