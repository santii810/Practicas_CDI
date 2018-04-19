import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class MySockets {
	public static void main(String args[]) {
		new Server().start();
		new Client().start();
	}
}

class Server extends Thread {
	Socket socket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	MiMatriz matriz;

	public void run() {
		matriz = new MiMatriz();
		try {
			ServerSocket server = new ServerSocket(4444);
			while (true) {
				socket = server.accept();
				ois = new ObjectInputStream(socket.getInputStream());
				String message = (String) ois.readObject();
				System.out.println("Server Received: " + message);
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject("Server Reply");
				ois.close();
				oos.close();
				socket.close();
			}
		} catch (Exception e) {
		}
	}
}

class Client extends Thread {
	InetAddress host = null;
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;

	public void run() {
		try {
			for (int x = 0; x < 5; x++) {
				host = InetAddress.getLocalHost();
				socket = new Socket(host.getHostName(), 4444);
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject("Client Message " + x);
				ois = new ObjectInputStream(socket.getInputStream());
				String message = (String) ois.readObject();
				System.out.println("Client Received: " + message);
				ois.close();
				oos.close();
				socket.close();
			}
		} catch (Exception e) {
		}
	}
}

class MiMatriz {
	private static final int NUM_DIVISIONES = 4;
	private static final String INPUT = "src/monkey.png";
	static BufferedImage img;
	static int[][] original;
	static int[][] destino;
	Queue<MiFragmento> fragmentos;

	public MiMatriz() {
		fragmentos = new LinkedList<MiFragmento>();
		leerMatriz();
		dividirMatriz();
	}

	private void dividirMatriz() {
		int widthBlockSize = original.length / NUM_DIVISIONES;
		int heightBlockSize = original[0].length / NUM_DIVISIONES;
		int[][] fragmento = new int[widthBlockSize][heightBlockSize];
		int m = 0, n = 0;
		for (int i = 0; i < NUM_DIVISIONES; i++) {
			for (int j = 0; j < NUM_DIVISIONES; j++) {
				for (int k = i * widthBlockSize; k < (i + 1) * widthBlockSize; k++) {
					for (int l = j * heightBlockSize; l < (j + 1) * heightBlockSize; l++) {
						fragmento[m][n++] = original[k][l];
					}
					n = 0;
					m++;
				}
				m = 0;
				fragmentos.add(new MiFragmento(fragmento));
				fragmento = new int[widthBlockSize][heightBlockSize];
			}
		}

	}

	private void leerMatriz() {
		try {
			File file_in = new File(INPUT);
			img = ImageIO.read(file_in);
			final int width = img.getWidth();
			final int height = img.getHeight();
			original = new int[width][height];

			Raster raster_in = img.getData();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					final int d = raster_in.getSample(i, j, 0);
					original[i][j] = d;
				}
			}
			imprimir();

		} catch (Exception e) {
			System.out.println("Error leyendo imagen");
		}

	}

	//
	// public static void distribuirColumnas(ArrayList<MiThread> misHilos, int
	// numThreads, int f) {
	// int siguienteHilo = 0;
	// // Instancio todos los hilos
	// for (int i = 0; i < numThreads; i++) {
	// misHilos.add(new MiThread("Thread number " + i, f));
	// }
	//
	// // Estrategia de dividir por columnas
	// for (int i = 0; i < original[0].length; i++) {
	// misHilos.get(siguienteHilo++ % numThreads).columnas.add(i);
	// }
	// }
	//
	// private static void rellenarMatriz(int width, int height, int[][] original) {
	// for (int i = 0; i < width; i++) {
	// for (int j = 0; j < height; j++) {
	// original[i][j] = (int) (Math.random() * 10);
	// }
	// }
	// }
	//
	// Imprime la matriz por consola
	public static void imprimir() {
		System.out.println("Matriz origen");
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original.length; j++) {
				System.out.print(original[i][j] + "\t");
			}
			System.out.println();
		}
	}

}

class MiFragmento {
	int[][] fragmento;

	public MiFragmento(int[][] fragmento) {
		this.fragmento = fragmento;
	}

	public void imprimir() {
		for (int i = 0; i < fragmento.length; i++) {
			for (int j = 0; j < fragmento.length; j++) {
				System.out.print(fragmento[i][j] + "\t");
			}
			System.out.println();
		}
	}

}