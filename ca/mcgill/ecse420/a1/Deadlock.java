package ca.mcgill.ecse420.a1;

public class Deadlock {

	//declare resources
	public static Object resourceA = new Object();
	public static Object resourceB = new Object();
	

	public static void main(String[] args) {
		Deadlock ds = new Deadlock();

		ds.readData();
		ds.writeData();
	}

	public void writeData() {

		synchronized (resourceA) {
			// obtained lock on A
			System.out.println("Thread 1 Obtained Lock on A");

			synchronized (resourceB) {
				// obtained lock on B
				// Do something
				System.out.println("Thread 1 Obtained Lock on B");
			}
		}
	}

	public void readData() {
		synchronized (resourceB) {
			// obtained lock on B
			System.out.println("Thread 2 Obtained Lock on B");

			synchronized (resourceA) {
				// obtained lock on A
				// DO something else
				System.out.println("Thread 2 Obtained Lock on A");
			}
		}
	}
}

/*
 * 
 * 2.1. Explain under what conditions deadlock could occur.
 * 
 * Deadlocks occur when 2 threads have a lock and are waiting for the other lock
 * to be freed, which is in the hands of another thread For instance say TH1 is
 * waiting for TH2 to release the lock, and TH2 is waiting TH1 to do so.
 * 
 * 
 * 2.2. Discuss possible design solutions to avoid deadlock.
 * 
 * Lock ordering and identityHashCode
 * 
 * 
 */
