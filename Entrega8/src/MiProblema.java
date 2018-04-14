import java.util.Random;

public class MiProblema {

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

	public Matrix[][] split() {
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
				ret += data[i][j] + " ";
			}
			ret += "\n";
		}
		return ret;
	}

}