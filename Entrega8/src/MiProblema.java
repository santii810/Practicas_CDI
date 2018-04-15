import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MiProblema {

	public static void main(String[] args) {
		Matrix a = new Matrix(8);
		Matrix b = new Matrix(8);
		Matrix suma = new Matrix(8);
		
		a.initialize();
		b.initialize();

		System.out.println(a.toString());
		System.out.println(b.toString());
		try {
			suma = MatrixTask.add(a, b);
			System.out.println("\nResult from 'suma':\n" + suma.toString());
		} catch (ExecutionException ex) {
		}

		System.out.println("Program of P3 has terminated");
	}

}

class Matrix {

	int dim;
	double[][] data;
	int rowDisplace, colDisplace;

	public Matrix(int d) {
		dim = d;
		rowDisplace = colDisplace = 0;
		data = new double[d][d];
	}

	private Matrix(double[][] matrix, int x, int y, int d) {
		data = matrix;
		rowDisplace = x;
		colDisplace = y;
		dim = d;
	}

	public void initialize() {
		Random rand = new Random();
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				data[i][j] = (double) rand.nextInt(10);
			}
		}
	}

	public double get(int row, int col) {
		return data[row + rowDisplace][col + colDisplace];
	}

	public void set(int row, int col, double value) {
		data[row + rowDisplace][col + colDisplace] = value;
	}

	public int getDim() {
		return dim;
	}

	Matrix[][] split() {
		Matrix[][] result = new Matrix[2][2];
		int newDim = dim / 2;
		result[0][0] = new Matrix(data, rowDisplace, colDisplace, newDim);
		result[0][1] = new Matrix(data, rowDisplace, colDisplace + newDim, newDim);
		result[1][0] = new Matrix(data, rowDisplace + newDim, colDisplace, newDim);
		result[1][1] = new Matrix(data, rowDisplace + newDim, colDisplace + newDim, newDim);
		return result;
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				ret += data[i][j] + "\t";
			}
			ret += "\n";
		}
		return ret;
	}
}

class MatrixTask {

	static ExecutorService exec = Executors.newCachedThreadPool();

	static Matrix add(Matrix a, Matrix b) throws ExecutionException {
		int n = a.getDim();
		Matrix c = new Matrix(n);
		Future<?> future = exec.submit(new AddTask(a, b, c));
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// Finish the executor
		exec.shutdown();
		System.out.println("Exec: No more tasks");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// The example finish
		System.out.println("Exec: return value");
		return c;
	}

	static class AddTask implements Runnable {

		Matrix a, b, c;

		public AddTask(Matrix myA, Matrix myB, Matrix myC) {
			a = myA;
			b = myB;
			c = myC;
		}

		public void run() {
			try {
				int n = a.getDim();
				if (n == 1) {
					c.set(0, 0, a.get(0, 0) + b.get(0, 0));
				} else {
					Matrix[][] aa = a.split(), bb = b.split(), cc = c.split();
					Future<?>[][] future = (Future<?>[][]) new Future[2][2];
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							future[i][j] = exec.submit(new AddTask(aa[i][j], bb[i][j], cc[i][j]));
						}
					}
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							future[i][j].get();
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	static class MulTask implements Runnable {

		Matrix a, b, c, lhs, rhs;

		public MulTask(Matrix myA, Matrix myB, Matrix myC) {
			a = myA;
			b = myB;
			c = myC;
			lhs = new Matrix(a.getDim());
			rhs = new Matrix(a.getDim());
		}

		public void run() {
			try {
				if (a.getDim() == 1) {
					c.set(0, 0, a.get(0, 0) * b.get(0, 0));
				} else {
					Matrix[][] aa = a.split(), bb = b.split(), cc = c.split();
					Matrix[][] ll = lhs.split(), rr = rhs.split();
					Future<?>[][][] future = (Future<?>[][][]) new Future[2][2][2];
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							future[i][j][0] = exec.submit(new MulTask(aa[i][0], bb[0][i], ll[i][j]));
							future[i][j][1] = exec.submit(new MulTask(aa[1][i], bb[i][1], rr[i][j]));
						}
					}
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							for (int k = 0; k < 2; k++) {
								future[i][j][k].get();
							}
						}
					}
					Future<?> done = exec.submit(new AddTask(lhs, rhs, c));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
