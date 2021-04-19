package ca.mcgill.ecse420.a3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelMatrixVectorMultiply {
	public static ExecutorService executor = Executors.newCachedThreadPool(); // Create fixed thread pool with given

	/**
	 * Returns the result of a concurrent matrix multiplication The two matrices are
	 * randomly generated
	 * 
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 */
	public static double[] parallelMultiply(double[][] a, double[] b, int matrixSize) {
		// thread number
		double[] result = new double[matrixSize]; // Initialize result matrix
		try {
			Future<?> multiplication = executor.submit(new ParallelMultiply(0, matrixSize, a, b, result));
			multiplication.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown(); // Shutdown the pool so make sure the output is ready
		return result;

	}

	/**
	 * Define thread task
	 * 
	 * @param a      is the matrix
	 * @param b      is the vector
	 * @param result is the result
	 * @param i      is the row index for the matrix
	 * @param n      is the matrix size (number of rows)
	 */
	static class ParallelMultiply implements Runnable {
		private double[][] a;
		private double[] b;
		private double[] result;
		private int i;
		private int n;

		ParallelMultiply(final int i, final int n, final double[][] a, final double[] b, final double[] result) {
			this.i = i;
			this.n = n;
			this.a = a;
			this.b = b;
			this.result = result;
		}

		// Override run for this task
		public void run() {

			try {
				if (n == 1) { // if the multiplication is divided and reached to one row, all columns are
								// multiplied to start to obtain result

					for (int j = 0; j < 2000; j++) {
						result[i] += a[i][j] * b[j];
					}

				} else {
					Future<?> firstHalf = executor.submit(new ParallelMultiply(i, n / 2, a, b, result));

					Future<?> secondHalf = executor.submit(new ParallelMultiply(i + (n / 2), n / 2, a, b, result));
					firstHalf.get();
					secondHalf.get();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}