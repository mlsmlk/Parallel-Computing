package ca.mcgill.ecse420.a2;

public class Test {
	private static final int NUMBER_THREADS = 10;
	static int counter = 0;

	public static void main(String[] args) throws InterruptedException {
		// Create defined number of threads for each lock test
		for (int i = 0; i < NUMBER_THREADS; i++) {
			FilterLockTest f = new FilterLockTest();
			f.start();
		}
		for (int i = 0; i < NUMBER_THREADS; i++) {
			BakeryLockTest b = new BakeryLockTest();
			b.start();
		}

	}

	static class BakeryLockTest extends Thread {
		// Initialize Bakery lock
		private static final BakeryLock lock = new BakeryLock(NUMBER_THREADS);

		// Run bakery lock test - increase counter every time a thread enters the
		// critical section, if the counter prints same value twice, then there is no
		// mutual exclusion
		public void run() {
			lock.lock();
			counter += 1;
			System.out.println("Bakery Counter: " + counter);
			lock.unlock();
		}
	}

	static class FilterLockTest extends Thread {
		// Initialize filter lock
		private static final FilterLock lock = new FilterLock(NUMBER_THREADS);

		// Run filter lock test - increase counter every time a thread enters the
		// critical section, if the counter prints same value twice, then there is no
		// mutual exclusion
		public void run() {
			lock.lock();
			counter += 1;
			System.out.println("Filter Counter: " + counter);
			lock.unlock();
		}
	}
}