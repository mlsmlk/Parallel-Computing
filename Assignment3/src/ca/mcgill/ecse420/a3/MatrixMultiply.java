package ca.mcgill.ecse420.a3;

public class MatrixMultiply {

	private static final int MATRIX_SIZE = 2000;

	public static void main(String[] args) {

		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[] b = generateRandomVector(MATRIX_SIZE);

		long startTime = System.currentTimeMillis(); // Mark star time for sequential matrix multiplication
		SequentialMatrixVectorMultiply.sequentialMultiply(a, b, MATRIX_SIZE);
		long endTime = System.currentTimeMillis(); // Mark end time for sequential matrix multiplication
		long timeDuration = calculateTimeDuration(startTime, endTime); // Calculate duration for sequential matrix
																		// multiplication and print it
		System.out.println("Time duration for sequential matrix multiplication: " + timeDuration + "ms");

		startTime = System.currentTimeMillis(); // Mark star time for parallel matrix multiplication
		ParallelMatrixVectorMultiply.parallelMultiply(a, b, MATRIX_SIZE);
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

	/**
	 * Populates a vector of given size with randomly generated integers between
	 * 0-10.
	 * 
	 * @param numRows number of rows
	 * @return vector
	 */
	private static double[] generateRandomVector(int numRows) {
		double vector[] = new double[numRows];
		for (int row = 0; row < numRows; row++) {

			vector[row] = (double) ((int) (Math.random() * 10.0));

		}
		return vector;
	}

}
