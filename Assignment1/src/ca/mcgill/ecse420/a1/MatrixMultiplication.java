package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {

	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 2000;

	public static void main(String[] args) {

		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

		// Mark star time for sequential matrix multiplication
		long startTime = System.currentTimeMillis();
		sequentialMultiplyMatrix(a, b);
		long timeDurationSequential = System.currentTimeMillis() - startTime;
		System.out.println("Time duration for sequential matrix multiplication: " + timeDurationSequential + "ms");

		// Mark star time for parallel matrix multiplication
		startTime = System.currentTimeMillis();
		parallelMultiplyMatrix(a, b);
		long timeDurationParallel = System.currentTimeMillis() - startTime;
		System.out.println("Time duration for parallel matrix multiplication: " + timeDurationParallel + "ms");
	}

	/**
	 * Returns the result of a sequential matrix multiplication The two matrices are
	 * randomly generated
	 * 
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 */
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
		double[][] resultMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
		double temp = 0;
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				for (int k = 0; k < MATRIX_SIZE; k++) {
					temp += a[i][k] * b[k][j];
				}
				resultMatrix[i][j] = temp;
				temp = 0;
			}
		}
		return resultMatrix;
	}

	/**
	 * Returns the result of a concurrent matrix multiplication The two matrices are
	 * randomly generated
	 * 
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 */
	public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);
		double[][] resultMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
		for (int i = 0; i < MATRIX_SIZE / 2; i++) {
			for (int j = 0; j < MATRIX_SIZE / 2; j++) {
				executor.execute(
						new ParallelMatrixMultiply(i + MATRIX_SIZE / 2, j + MATRIX_SIZE / 2, a, b, resultMatrix));
				executor.execute(new ParallelMatrixMultiply(i, j, a, b, resultMatrix));
			}
		}
		// Shut down the executor
		executor.shutdown();
		return resultMatrix;
	}

	static class ParallelMatrixMultiply extends Thread {
		private double[][] a;
		private double[][] b;
		private double[][] resultMatrix;
		private int i;
		private int j;

		ParallelMatrixMultiply(final int i, final int j, final double[][] a, final double[][] b,
				final double[][] resultMatrix) {
			this.i = i;
			this.j = j;
			this.a = a;
			this.b = b;
			this.resultMatrix = resultMatrix;
		}

		public void run() {
			resultMatrix[i][j] = 0;
			for (int k = 0; k < MATRIX_SIZE; k++) {
				resultMatrix[i][j] += a[i][k] * b[k][j];
			}
		}
	}

	/**
	 * Populates a matrix of given size with randomly generated integers between
	 * 0-10.
	 * 
	 * @param numRows number of rows
	 * @param numCols number of cols
	 * @return matrix
	 */
	private static double[][] generateRandomMatrix(int numRows, int numCols) {
		double matrix[][] = new double[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				matrix[row][col] = (double) ((int) (Math.random() * 10.0));
			}
		}
		return matrix;
	}

}
