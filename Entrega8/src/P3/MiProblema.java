import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MiProblema {

	public static void main(String args[]) {
		MySemaphore buffer = new MySemaphore(10);
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

class MySemaphore {
	private LinkedList<Integer> productos;
	private int capacidad;
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	private String ultimoHilo;

	public MySemaphore(int capacidad) {
		productos = new LinkedList<>();
		this.capacidad = capacidad;
		this.ultimoHilo = "";
		for (int i = 0; i < capacidad/2; i++) {
			this.productos.add(0);	
		}
	}

	public void write(int a) {
		lock.lock();
		try {
			while (productos.size() >= capacidad || ultimoHilo.equals(Thread.currentThread().getName()))
				try {
					notFull.await();
				} catch (InterruptedException e) {
				}
			productos.add(a);
			ultimoHilo = Thread.currentThread().getName();
			System.out.println(Thread.currentThread().getName() + " is writting, Product size is: " + productos.size());
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public void read() {
		lock.lock();
		try {
			while (productos.size() <= 0) {
				try {
					notEmpty.await();
				} catch (InterruptedException e) {
				}
			}
			productos.remove(0);
			ultimoHilo =  Thread.currentThread().getName();
			System.out.println(Thread.currentThread().getName() + " is reading, Product size is: " + productos.size());
			notFull.signal();
		} finally {
			lock.unlock();
		}
	}
}

class Productor implements Runnable {

	MySemaphore buffer;

	public Productor(MySemaphore buffer) {
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

	MySemaphore buffer;

	public Consumidor(MySemaphore buffer) {
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
