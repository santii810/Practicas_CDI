import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
		System.out.println("Program of exercise P3 has terminated");
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
				Mensaje message = (Mensaje) ois.readObject();
				System.out.println("Server Received: ");
				message.imprimir();

				switch (message.id) {
				case Mensaje.MSG_GET_FRAGMENT:
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(sendFragment()); 
					break;
				case Mensaje.MSG_SET_FRAGMENT:
					matriz.guardarFragmento(message.fragmento);
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(sendFragment()); 
					break;
				default:
					System.out.println("Mensaje no entendido por el servidor");
					break;
				}

				ois = new ObjectInputStream(socket.getInputStream());
				message = (Mensaje) ois.readObject();
				System.out.println("Server Received: ");
				message.imprimir();

				ois.close();
				oos.close();
				socket.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Crea un mensaje. Si la matriz no acabo de filtrarse llevará un fragmento y el
	 * codigo MSG_FILTRAT en caso contrario el codigo MSG_IMG_COMPLETE
	 * 
	 * @return
	 */
	private Object sendFragment() {
		MiFragmento fragmento = matriz.getFragmento();
		if (fragmento == null)
			return new Mensaje(Mensaje.MSG_IMG_COMPLETE);
		else
			return new Mensaje(Mensaje.MSG_FILTRAR, fragmento);
	}

}

class Client extends Thread {
	InetAddress host = null;
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	boolean completo = false;

	public void run() {
		try {
			while (!completo) {
				host = InetAddress.getLocalHost();
				socket = new Socket(host.getHostName(), 4444);

				// Enviar mensaje
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(new Mensaje(Mensaje.MSG_GET_FRAGMENT));

				// Recibir mensaje
				ois = new ObjectInputStream(socket.getInputStream());
				System.out.println("Client recived: ");
				Mensaje message = (Mensaje) ois.readObject();
				message.imprimir();

				switch (message.id) {
				case Mensaje.MSG_FILTRAR:
					message.fragmento.filtroMediano();
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(new Mensaje(Mensaje.MSG_SET_FRAGMENT, message.fragmento));
					System.out.println("filtrado mensaje");
					break;
				case Mensaje.MSG_IMG_COMPLETE:
					completo = true;
					break;
				default:
					System.out.println("Mensaje no entendido por el cliente");
					break;
				}
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
	private static final String INPUT = "monkey.png";
	static BufferedImage img;
	static int[][] original;
	static int[][] destino;
	Queue<MiFragmento> fragmentos;

	public MiMatriz() {
		fragmentos = new LinkedList<MiFragmento>();
		leerMatriz();
		dividirMatriz();
	}

	public void guardarFragmento(MiFragmento fragmento) {
		int col = fragmento.colInicio;
		int fil = fragmento.filaInicio;
		for (int i = 0; i < fragmento.fragmento.length; i++) {
			for (int j = 0; j < fragmento.fragmento.length; j++) {
				destino[fil][col++] = fragmento.fragmento[i][j];
			}
			fil++;
		}

	}

	private void dividirMatriz() {
		int widthBlockSize = original.length / NUM_DIVISIONES;
		int heightBlockSize = original[0].length / NUM_DIVISIONES;
		int[][] fragmento = new int[widthBlockSize][heightBlockSize];
		int m = 0, n = 0;
		for (int i = 0; i < NUM_DIVISIONES; i++) {
			for (int j = 0; j < NUM_DIVISIONES; j++) {
				int filaInicio = i * widthBlockSize, colInicio = j * heightBlockSize;
				for (int k = filaInicio; k < (i + 1) * widthBlockSize; k++) {
					for (int l = colInicio; l < (j + 1) * heightBlockSize; l++) {
						fragmento[m][n++] = original[k][l];
					}
					n = 0;
					m++;
				}
				m = 0;
				fragmentos.add(new MiFragmento(fragmento, colInicio, filaInicio));
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

		} catch (Exception e) {
			System.out.println("Error leyendo imagen");
		}

	}

	public MiFragmento getFragmento() {
		return fragmentos.poll();
	}

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

class MiFragmento implements Serializable {
	int[][] fragmento;
	int filaInicio;
	int colInicio;
	private final int FILTER_SIZE = 2;

	public MiFragmento(int[][] fragmento, int colInicio2, int filaInicio2) {
		this.fragmento = fragmento;
		this.filaInicio = filaInicio2;
		this.colInicio = colInicio2;
	}

	public void filtroMediano() {

		int[][] destino = new int[fragmento.length][fragmento.length];
		for (int i = 0; i < fragmento.length; i++) {
			for (int j = 0; j < fragmento.length; j++) {
				filtrarPixel(i, j, FILTER_SIZE, destino);
			}
		}
		fragmento = destino;

	}

	public void filtrarPixel(int x, int y, int f, int[][] destino) {
		double fraccion = 1 / Math.pow((2 * f + 1), 2);
		int acum = 0, reflejoX = 0, reflejoY = 0;
		for (int i = x - f; i <= x + f; i++) {
			for (int j = y - f; j <= y + f; j++) {
				reflejoX = i;
				reflejoY = j;
				if (i > (fragmento.length - 1) || i < 0)
					reflejoX = 2 * x - i;
				if (j > (fragmento[Math.abs(reflejoX)].length - 1) || j < 0)
					reflejoY = 2 * y - j;
				acum += fragmento[reflejoX][reflejoY];
			}
		}
		destino[x][y] = (int) (acum * fraccion);
	}

	public void imprimir() {
		System.out.println("Inicio (" + filaInicio + ", " + colInicio + ").");
		for (int i = 0; i < fragmento.length; i++) {
			for (int j = 0; j < fragmento.length; j++) {
				System.out.print(fragmento[i][j] + "\t");
			}
			System.out.println();
		}
	}

}

class Mensaje implements Serializable {

	// El servidor le manda al cliente el mensaje imagen completa para que pare
	public static final int MSG_IMG_COMPLETE = 10;
	// El servidor le manda al cliente un fragmento para filtrar
	public static final int MSG_FILTRAR = 11; 

	
	// El cliente le manda al servidor una peticion de trabajo
	public static final int MSG_GET_FRAGMENT = 20; 
	// El cliente devuelve el fragmento filtrado
	public static final int MSG_SET_FRAGMENT = 21; 

	public int id;
	public MiFragmento fragmento;

	public Mensaje(int id, MiFragmento fragmento2) {
		this.id = id;
		this.fragmento = fragmento2;
	}

	public Mensaje(int id) {
		this.id = id;
	}

	public void imprimir() {
		System.out.println(id);
		if (id == MSG_FILTRAR) {
			fragmento.imprimir();
		}
		System.out.println();
	}

}