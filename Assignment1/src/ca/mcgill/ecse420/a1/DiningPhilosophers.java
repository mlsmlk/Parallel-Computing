package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
	
	public static void main(String[] args) {


			int numberOfPhilosophers = 5;

                Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
                Object[] chopsticks = new Object[numberOfPhilosophers];
	}

	void put_fork(int num) {

	}

	void take_fork(int num) {

	}

	void thinking(String action) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	public static class Philosopher implements Runnable {

		private Object leftChopstick;
		private Object rightChopstick;

		public Philosopher(Object leftChopstick, Object rightChopstick ) {
			this.leftChopstick = leftChopstick;
			this.rightChopstick = rightChopstick;
		}

		

		@Override
		public void run() {

			while(true) {   //while running
				//do stuff, thinking()?
				//take_fork(i)->left fork
				//take_fork((i+1)%N)-> right fork. n is number of forks
				//EAT()
				//put fork(i)
				//put fork((i+1)%N)--> mod n
			}
			
		}


	}

}

// 2 states-> thinking and eating



//add some priority, have a queue
//locking needs to be atomic
//when one program is asking, the other cannot gain it
//