import java.util.LinkedList;

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
	final MySemaphore writer = new MySemaphore(0);
	final MySemaphore reader = new MySemaphore(1);

	public Buffer(int capacidad) {
		productos = new LinkedList<>();
		this.capacidad = capacidad;
	}

	public void write(int a) {
		writer.up();
		productos.add(a);
		System.out.println(Thread.currentThread().getName() + " is writting, Product size is: " + productos.size());
		reader.down();
	}

	public void read() {
		reader.up();
		productos.remove(0);
		System.out.println(Thread.currentThread().getName() + " is reading, Product size is: " + productos.size());
		writer.down();
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

class MySemaphore {

	int flag;

	public MySemaphore(int initialValue) {
		this.flag = initialValue;
	}

	// initialize a sempahore with value zero
	public MySemaphore() {
		this(0);
	}

	public void up() {
		synchronized (this) {
			while (flag == 1) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			flag = 1;
		}
	}

	public void down() {
		synchronized (this) {
			flag = 0;
			notify();
		}
	}
}
