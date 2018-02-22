
public class HiloThread extends Thread {
	int tiempo;

	public HiloThread(String nombreHilo) {
		super(nombreHilo);
	}
	@Override
	public void run() {
		System.out.println("Hello world, I’m a java thread number " + currentThread().getName());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bye, this was thread number " + currentThread().getName());
	}
}