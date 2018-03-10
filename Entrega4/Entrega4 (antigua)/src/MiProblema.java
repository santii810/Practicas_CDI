import java.util.ArrayList;

public class MiProblema {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[0]);

		ArrayList<MiThread> misHilos = new ArrayList<>();

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < numThreads; i++) {
			misHilos.add(new MiThread("Hilo " + i));
		}
		for (MiThread miThread : misHilos) {
			miThread.start();
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (MiThread miThread : misHilos) {
			try {
				miThread.join();
			} catch (InterruptedException e) {
			}
		}

		long endTime = System.currentTimeMillis() - startTime;

	}
}

class MiThread extends Thread {

	static Contador contador;

	public MiThread(String nombreHilo) {
		super(nombreHilo);
		contador = new Contador();
	}

	@Override
	public void run() {
		int veces = (int) (Math.random() * 20);
		for (int i = 0; i < veces; i++) {
			int num = (int) (Math.random() * 20);
			contador.incrementar(num);
		}
		System.out.println(contador);
	}
}

class Contador {
	int acum;

	public Contador() {
		this.acum = 0;
	}

	synchronized public int incrementar(int n) {
		for (int i = 0; i < n; i++) {
			acum++;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return acum;
	}

	@Override
	public String toString() {
		return Integer.toString(acum);
	}

}
