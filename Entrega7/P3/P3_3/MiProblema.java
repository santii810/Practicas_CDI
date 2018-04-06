import java.util.concurrent.ArrayBlockingQueue;

public class MiProblema {
	static final int CAPACIDAD = 20;

	public static void main(String args[]) {
		Buffer<Integer> buffer = new Buffer<Integer>(10);
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


class Productor implements Runnable {

	Buffer<Integer> buffer;

	public Productor(Buffer<Integer> buffer) {
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

	Buffer<Integer> buffer;

	public Consumidor(Buffer<Integer> buffer) {
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

class Buffer<E> extends ArrayBlockingQueue<E> {

	public Buffer(int capacidad) {
		super(capacidad);
	}
	
	public void write(E e) throws InterruptedException {
		super.put(e);
		System.out.println(Thread.currentThread().getName() + " is writting, Product size is: " + this.size());
	}

	public E read() throws InterruptedException {
		E toret = take();
		System.out.println(Thread.currentThread().getName() + " is reading, Product size is: " + this.size());
		return toret;
	}
}
