import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class MiProblema {
	static final int CAPACIDAD = 20;

	public static void main(String args[]) {
		Buffer buffer = new Buffer(10);
		Productor productor = new Productor(buffer);
		Consumidor consumidor = new Consumidor(buffer);

		Thread t1 = new Thread(productor, "Productor");
		Thread t2 = new Thread(consumidor, "Consumidor");
		t1.start();
		t2.start();

		try {
			Thread.sleep(500);
			t1.interrupt();
			t2.interrupt();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Program of exercise P4 has terminated");

	}

}

class Buffer {
	private LinkedList<Integer> productos;
	private int capacidad;
	final Semaphore writer = new Semaphore(1);
	final Semaphore reader = new Semaphore(0);

	public Buffer(int capacidad) {
		productos = new LinkedList<>();
		this.capacidad = capacidad;
	}

	public void write(int a) {
		try {
			writer.acquire();
		productos.add(a);
		System.out.println(Thread.currentThread().getName() + " is writting, Product size is: " + productos.size());
		reader.release();
		} catch (InterruptedException e) {
		}
	}

	public void read() {
		try {
			reader.acquire();
		productos.remove(0);
		System.out.println(Thread.currentThread().getName() + " is reading, Product size is: " + productos.size());
		writer.release();
		} catch (InterruptedException e) {
		}
	}
}

class Productor implements Runnable {

	Buffer buffer;

	public Productor(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (true)
			try {
				Thread.sleep((long) (Math.random() * 5));
				buffer.write(3);
			} catch (InterruptedException e) {
				break;
			}
	}
}

class Consumidor implements Runnable {

	Buffer buffer;

	public Consumidor(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep((long) (Math.random() * 5));
				buffer.read();
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
	
