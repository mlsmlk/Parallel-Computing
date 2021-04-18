package ca.mcgill.ecse420.a3;

public class MatrixMultiply {

	private static final int MATRIX_SIZE = 2048;

	public static void main(String[] args) {
		double[][] matrix = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[] vector = generateRandomVector(MATRIX_SIZE);

		double seqStart = System.nanoTime();
		double[] seqResult = SequentialMatricVectorMultiply.sequentialMultiply(matrix, vector, MATRIX_SIZE);
		double seqEnd = System.nanoTime();

		double parStart = System.nanoTime();
		double[] result = ParallelMatrixVectorMultiply.parallelMultiply(matrix, vector, MATRIX_SIZE);
		double parEnd = System.nanoTime();

		System.out.println("\nTime taken for Sequential : " + (seqEnd - seqStart) / 1000000000 + " Seconds");
		System.out.println("\nTime taken for Parallel : " + (parEnd - parStart) / 1000000000 + " Seconds");
	}

	// GenerateRandomMatrix method

	private static double[][] generateRandomMatrix(int numRows, int numCols) {
		double matrix[][] = new double[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				matrix[row][col] = (double) ((int) (Math.random() * 10.0));
			}
		}
		return matrix;
	}

	// GenerateRandomVector method

	private static double[] generateRandomVector(int numRows) {
		double vector[] = new double[numRows];
		for (int row = 0; row < numRows; row++) {
			vector[row] = (double) ((int) (Math.random() * 10.0));
		}
		return vector;
	}

	private static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("|");
		}
	}

	private static void printVector(double[] vector) {
		System.out.print("| ");
		for (int i = 0; i < vector.length; i++) {
			System.out.print(vector[i] + " ");
		}
		System.out.println("|");
	}

}
