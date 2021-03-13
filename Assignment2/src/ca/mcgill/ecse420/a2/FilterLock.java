package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.atomic.AtomicInteger;

public class FilterLock implements Lock {
	AtomicInteger[] level;
	AtomicInteger[] victim;
	int num;

	public FilterLock(int n) {
		num = n;								//global thread number initialization
		level = new AtomicInteger[n];			//define number of levels
		victim = new AtomicInteger[n];			//define possible max number of victim
		for (int i = 0; i < n; i++) {
			level[i] = new AtomicInteger(0);	//initialize levels
		}
	}

	@Override
	public void lock() {
		int threadId = (int) (Thread.currentThread().getId() % num); // get current thread id
		for (int i = 0; i < num; i++) {							//for each level
			level[threadId] = new AtomicInteger(i);	//define intention of current thread to enter level i
			victim[i] = new AtomicInteger(threadId);	//deprioritize the current thread and give priority to others
			//wait until there is not thread in the higher or same level while the current thread is defined as victim
			for (int k = 0; k < num; k++) {
				while ((k != threadId && level[k].get() >= i && victim[i].get() == threadId)) {
				}
			}
			//when the waiting is done, thread enters the level i
		}

	}

	@Override
	public void unlock() {
		int threadId = (int) (Thread.currentThread().getId() % num); // get current thread id
		level[threadId] = new AtomicInteger (0);		//reset the position of current thread in terms of level
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
