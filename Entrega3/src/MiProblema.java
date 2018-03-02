import java.util.ArrayList;

public class MiProblema {

	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int numThreads = Integer.parseInt(args[2]);
		MiMatriz matriz = new MiMatriz(width, height);
//		matriz.imprimir();
		System.out.println();
		System.out.println();
		matriz.filtroMediano(7, 1, 1);
		matriz.imprimir();

		// ArrayList<MiThread> threadList = new ArrayList<>();
		// for (int i = 0; i < numThreads; i++) {
		// threadList.add(new MiThread("Thread number " + i, original, destino,
		// numThreads));
		// }
		//
		// for (MiThread miHilo : threadList) {
		// try {
		// miHilo.join();
		// } catch (InterruptedException e) {
		// }
		// }
		// System.out.println("Program of exercise P4 has terminated");
	}

}

class MiThread extends Thread {
	int[][] original;
	int[][] destino;
	int numThreads;

	public MiThread(String nombreHilo, int[][] original, int[][] destino, int numThreads) {
		super(nombreHilo);
		this.original = original;
		this.destino = destino;
		this.numThreads = numThreads;
	}

	@Override
	public void run() {

	}
}

class MiMatriz {
	static int[][] original;
	static int[][] destino;

	public MiMatriz(int width, int height) {
		original = new int[width][height];
		destino = new int[width][height];
		rellenarMatriz(width, height, original);
	}

	private static void rellenarMatriz(int width, int height, int[][] original) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				original[i][j] = (int) (Math.random() * 10);
			}
		}
	}

	public static void imprimir() {
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				System.out.print(original[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				System.out.print(destino[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void filtroMediano(int x, int y, int f) {
		double fraccion = 1 / Math.pow((2 * f + 1), 2);
		int acum = 0, reflejoX = 0, reflejoY = 0;
		for (int i = x - f; i <= x + f; i++) {
			for (int j = y - f; j <= y + f; j++) {
				reflejoX = i;
				reflejoY = j;
				if (x >= (original.length - 1))
					reflejoX = i - f * (i - x);
//				if (y >= (original[Math.abs(reflejoX)].length - 1))
//					reflejoY = y - (j - y);
				System.out.println("y " + reflejoY + "\t " + y + "-(" +j + "-" + y +") "  );
				acum += original[Math.abs(reflejoX)][Math.abs(reflejoY)];
			}
		}
		destino[x][y] = (int) (acum * fraccion);
		System.out.println();
		System.out.println();
	}
}