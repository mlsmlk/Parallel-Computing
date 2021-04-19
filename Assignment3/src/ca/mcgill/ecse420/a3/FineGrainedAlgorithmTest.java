package ca.mcgill.ecse420.a3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FineGrainedAlgorithmTest {

	static FineGrainedAlgorithm<Integer> list = new FineGrainedAlgorithm<Integer>();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4);

		// add and check contains for different values
		executor.execute(new ContainsTask(10));
		executor.execute(new ContainsTask(1));
		executor.execute(new ContainsTask(120));
		executor.execute(new ContainsTask(2));
		executor.execute(new ContainsTask(60));
		executor.execute(new ContainsTask(19));

		executor.shutdown();
	}

	static class ContainsTask implements Runnable {
		private int n;

		ContainsTask(int n) {
			this.n = n;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 2; i++) { // access same node again to see if lock works
					if (i == 0)
						list.add(n);
					System.out.println("Is " + n + "in the list:" + list.contains(n));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
