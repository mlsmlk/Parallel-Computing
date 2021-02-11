package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

	public static void main(String[] args) {

		int numberOfPhilosophers = 5;

		Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
		Object[] chopsticks = new Object[numberOfPhilosophers];

		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = new Object();
		}

		for (int i = 0; i < philosophers.length; i++) {
			Object leftChopstick = chopsticks[i];
			Object rightChopstick = chopsticks[(i + 1) % chopsticks.length];

			philosophers[i] = new Philosopher(leftChopstick, rightChopstick);

			Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
			t.start();
		}
	}

	static void put_chop(String put) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + put);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	private static void take_chop(String take) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + take);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	private static void thinking(String think) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + think);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	public static class Philosopher implements Runnable {

		private Object leftChopstick;
		private Object rightChopstick;

		public Philosopher(Object leftChopstick, Object rightChopstick) {
			this.leftChopstick = leftChopstick;
			this.rightChopstick = rightChopstick;
		}

		@Override
		public void run() {
			try {
				while (true) {

					thinking(System.nanoTime() + ": Thinking");
					synchronized (leftChopstick) {
						take_chop(
								System.nanoTime()
										+ ": Picked up left fork");
						synchronized (rightChopstick) {
							// eating
							take_chop(
									System.nanoTime()
											+ ": Picked up right fork - eating");

							put_chop(
									System.nanoTime()
											+ ": Put down right fork");
						}

						// Back to thinking
						put_chop(
								System.nanoTime()
										+ ": Put down left fork. Back to thinking");


					}


					//while running
					//do stuff, thinking()?
					//take_fork(i)->left fork
					//take_fork((i+1)%N)-> right fork. n is number of forks
					//EAT()
					//put fork(i)
					//put fork((i+1)%N)--> mod n
				}


			}
		}catch(

		InterruptedException e)

		{
			Thread.currentThread().interrupt();
			return;
		}
	}
}

// 2 states-> thinking and eating

// how to prevent a/the deadlock
//add some priority, have a queue
//locking needs to be atomic
//when one program is asking, the other cannot gain it
//