import java.util.ArrayList;

public class MiProblema {

	public static void main(String[] args) {

		if (args.length != 4) {
			System.out.println("Numero de argumentos incorrecto");
			System.exit(1);
		}
		int filas = Integer.parseInt(args[0]);
		int columnas = Integer.parseInt(args[1]);
		int f = Integer.parseInt(args[2]);
		if(f >= filas/2 || f >= filas/2) {
			System.out.println("El valor de f no puede ser tan alto para una matriz tan pequeña");
			System.exit(1);
		}
		int numThreads = Integer.parseInt(args[3]);
		MiMatriz matriz = new MiMatriz(filas, columnas);
		ArrayList<MiThread> misHilos = new ArrayList<>();

		long startTime = System.currentTimeMillis();
		MiMatriz.distribuirColumnas(misHilos, numThreads, f);

		for (MiThread miThread : misHilos) {
			miThread.start();
		}
		for (MiThread miThread : misHilos) {
			try {
				miThread.join();
			} catch (InterruptedException e) {
			}
		}
		// MiMatriz.imprimir();
		// System.out.println("Program of exercise P4 has terminated");
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println(filas + "\t" + columnas + "\t" +numThreads + "\t" + endTime);
	}
}

class MiThread extends Thread {
	ArrayList<Integer> columnas;
	int f;

	public MiThread(String nombreHilo, int f) {
		super(nombreHilo);
		this.f = f;
		columnas = new ArrayList<>();
	}

	@Override
	public void run() {

		for (Integer columna : columnas) {
			for (int fila = 0; fila < MiMatriz.original.length; fila++) {
				try {
					filtroMediano(fila, columna, f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void filtroMediano(int x, int y, int f) {
		double fraccion = 1 / Math.pow((2 * f + 1), 2);
		int acum = 0, reflejoX = 0, reflejoY = 0;
		for (int i = x - f; i <= x + f; i++) {
			for (int j = y - f; j <= y + f; j++) {
				reflejoX = i;
				reflejoY = j;
				if (i > (MiMatriz.original.length - 1) || i < 0)
					reflejoX = 2 * x - i;
				if (j > (MiMatriz.original[Math.abs(reflejoX)].length - 1) || j < 0)
					reflejoY = 2 * y - j;

				// System.out
				// .println("i " + i + " x " + x + " rx " + reflejoX + " j " + j + " y " + y + "
				// ry " + reflejoY);

				acum += MiMatriz.original[reflejoX][reflejoY];
			}
		}
		MiMatriz.destino[x][y] = (int) (acum * fraccion);
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

	public static void distribuirColumnas(ArrayList<MiThread> misHilos, int numThreads, int f) {
		int siguienteHilo = 0;
		// Instancio todos los hilos
		for (int i = 0; i < original.length; i++) {
			misHilos.add(new MiThread("Thread number " + i, f));
		}

		// Estrategia de dividir por columnas
		for (int i = 0; i < original[0].length; i++) {
			
			misHilos.get(siguienteHilo++ % numThreads).columnas.add(i);
		}
	}

	private static void rellenarMatriz(int width, int height, int[][] original) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				original[i][j] = (int) (Math.random() * 10);
			}
		}
	}

	// Imprime la matriz por consola
	public static void imprimir() {
		System.out.println("Matriz origen");
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				System.out.print(original[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Matriz resultado");
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				System.out.print(destino[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

	}

}