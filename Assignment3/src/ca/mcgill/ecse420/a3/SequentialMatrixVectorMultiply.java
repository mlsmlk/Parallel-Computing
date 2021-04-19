package ca.mcgill.ecse420.a3;

public class SequentialMatrixVectorMultiply {

	/**
	 * Returns the result of a sequential matrix vector multiplication
	 * 
	 * @param a is the matrix
	 * @param b is the vector
	 * @return the result of the multiplication
	 */
	public static double[] sequentialMultiply(double[][] a, double[] b, int matrixSize) {
		double[] resultMatrix = new double[matrixSize]; // Initialize result matrix
		double temp = 0; // Initialize temporary variable for calculating the total multiplication for
		// given indices
		for (int i = 0; i < matrixSize; i++) {// For each row of a
			for (int j = 0; j < matrixSize; j++) { // For each column of a and row of b
				temp += a[i][j] * b[j]; // Multiply elements and add result
			}
			resultMatrix[i] = temp; // Define found result to necessary index of the result matrix
			temp = 0; // Reset temp for the next iteration
		}
		return resultMatrix;
	}
}
