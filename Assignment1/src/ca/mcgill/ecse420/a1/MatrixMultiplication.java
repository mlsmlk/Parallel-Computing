package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {

	private static final int NUMBER_THREADS = 4;
	private static final int MATRIX_SIZE = 4000;

	public static void main(String[] args) {

		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

		long startTime = System.currentTimeMillis(); // Mark star time for sequential matrix multiplication
		sequentialMultiplyMatrix(a, b);
		long endTime = System.currentTimeMillis(); // Mark end time for sequential matrix multiplication
		long timeDuration = calculateTimeDuration(startTime, endTime); // Calculate duration for sequential matrix
																		// multiplication and print it
		System.out.println("Time duration for sequential matrix multiplication: " + timeDuration + "ms");

		startTime = System.currentTimeMillis(); // Mark star time for parallel matrix multiplication
		parallelMultiplyMatrix(a, b);
		endTime = System.currentTimeMillis(); // Mark end time for parallel matrix multiplication
		timeDuration = calculateTimeDuration(startTime, endTime); // Calculate duration for parallel matrix
																	// multiplication and print it
		System.out.println("Time duration for parallel matrix multiplication: " + timeDuration + "ms");
	}

	/**
	 * Calculate the time interval between given times and print the result
	 * 
	 * @param startTime is the start time
	 * @param endTime   is the end time
	 * @return time duration
	 */
	public static long calculateTimeDuration(long startTime, long endTime) {
		return endTime - startTime; // Calculate the time duration in ms

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
		double[][] resultMatrix = new double[MATRIX_SIZE][MATRIX_SIZE]; // Initialize result matrix
		double temp = 0; // Initialize temporary variable for calculating the total multiplication for
							// given indices
		for (int i = 0; i < MATRIX_SIZE; i++) {// For each row of a
			for (int j = 0; j < MATRIX_SIZE; j++) { // For each column of b
				for (int k = 0; k < MATRIX_SIZE; k++) { // For each element in ith row of a and jth column of b
					temp += a[i][k] * b[k][j]; // Multiply elements and add to temp for reaching the final result
				}
				resultMatrix[i][j] = temp; // Define found result to necessary index of the result matrix
				temp = 0; // Reset temp for the next iteration
			}
		}
		// This command is used for debugging and testing purposes
//		System.out.println(Arrays.deepToString(resultMatrix));
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
		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS); // Create fixed thread pool with given
																					// thread number
		double[][] resultMatrix = new double[MATRIX_SIZE][MATRIX_SIZE]; // Initialize result matrix
		for (int i = 0; i < MATRIX_SIZE / 2; i++) { // For each row of a until reaching the half of the matrix
			for (int j = 0; j < MATRIX_SIZE; j++) { // For each column of b until reaching the half of the matrix
				// Task 1 handling elements multiplication with the first half of the matrix
				executor.execute(new ParallelMatrixMultiply(i, j, a, b, resultMatrix));
				// Task 2 handling elements multiplication with the second half of the matrix
				executor.execute(new ParallelMatrixMultiply(i + MATRIX_SIZE / 2, j, a, b, resultMatrix));
			}
		}
		executor.shutdown(); // Shutdown the pool so make sure the output is ready
		return resultMatrix;

	}

	/**
	 * Define thread task
	 * 
	 * @param a            is the first matrix
	 * @param b            is the second matrix
	 * @param resultMatrix is the result matrix
	 * @param i            is the row index for the first matrix
	 * @param j            is the column index for the second matrix
	 */
	static class ParallelMatrixMultiply implements Runnable {
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

		// Override run for this task
		public void run() {
			resultMatrix[i][j] = 0;
			for (int k = 0; k < MATRIX_SIZE; k++) {// For each element in ith row of a and jth column of b
				resultMatrix[i][j] += a[i][k] * b[k][j]; // Multiply elements and add to result matrix
			}
			// This command is used for debugging and testing purposes
//			System.out.println("Row: " + i + " Column: " + j + " Result: " + resultMatrix[i][j]);
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
