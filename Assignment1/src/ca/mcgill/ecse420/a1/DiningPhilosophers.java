package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

	public static void main(String[] args) {

		//number of philosophers n=5
		int numberOfPhilosophers = 5;
		//initialize array of philosophers with size 5 and same number of chopsticks for the chopsticks array
		Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
		Object[] chopsticks = new Object[numberOfPhilosophers];

		//every chopstick has an index 
		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = new Object();
		}
		//every philosopher has an index and each has a left chopstick and a right chopstick 
		for (int i = 0; i < philosophers.length; i++) {
			//define which one is left, which one is left using mod
			Object leftChopstick = chopsticks[i];
			Object rightChopstick = chopsticks[(i + 1) % chopsticks.length];

			if (i == philosophers.length-1) {					//3.2, this if condition makes such that the last philosopher reaches for the right chop first
				philosophers[i] = new Philosopher(rightChopstick, leftChopstick);//instead of left. which breaks the circular wait condition and the deadlock
			} else{ //every other philosopher reaches left one first
				philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
			}
			Thread t
					= new Thread(philosophers[i], "Philosopher " + (i + 1));
			t.start();
		}
	}

	//put down chopsticks
	static void put_chop(String put) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + put);
		Thread.sleep(((int) (Math.random() * 100)));
	}
	//take chopsticks
	private static void take_chop(String take) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + take);
		Thread.sleep(((int) (Math.random() * 100)));
	}
	//thinking method
	private static void thinking(String think) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + think);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	public static class Philosopher implements Runnable {
		//chopsticks on either side of the philosophers
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
					//thinking
					thinking(System.nanoTime() + ": Thinking");
					synchronized (leftChopstick) {
						take_chop(
								System.nanoTime()
										+ ": Picked up left chop");
						synchronized (rightChopstick) {
							// eating
							take_chop(
									System.nanoTime()
											+ ": Picked up right chop - eating");

							put_chop(
									System.nanoTime()
											+ ": Put down right chop");
						}

						// Back to thinking
						put_chop(
								System.nanoTime()
										+ ": Put down left chop. Back to thinking");


					}



				}


			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}

		}
	}
