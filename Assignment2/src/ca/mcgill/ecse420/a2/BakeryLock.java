package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BakeryLock implements Lock {

	AtomicBoolean[] flag;
	AtomicInteger[] label;
	int num;

	public BakeryLock(int n) {
		num = n; // initialize the global number of thread
		flag = new AtomicBoolean[n]; // define the number of possible flag and labels according to thread number
		label = new AtomicInteger[n];
		for (int i = 0; i < n; i++) {
			flag[i] = new AtomicBoolean(false); // initialize flag and label
			label[i] = new AtomicInteger(0);
		}
	}

	@Override
	public void lock() {
		int threadId = (int) (Thread.currentThread().getId() % num); // get current thread id
		flag[threadId] = new AtomicBoolean(true); // indicate that current thread wants to enter critical
		// section
		int max = Integer.MIN_VALUE; // initialize temporary max value for finding the highest label number
		for (AtomicInteger i : label) {
			max = Math.max(i.get(), max); // find the label with maximum number
		}
		label[threadId] = new AtomicInteger(max + 1); // set label for new thread the possible min value (max
		// +1)
		// compare interested threads according to lexicographic order and let the one
		// in lowest enter the critical section
		for (int k = 0; k < num; k++)
			while ((k != threadId) && flag[k].get()
					&& (label[k].get() < label[threadId].get() || (label[k] == label[threadId] && k < threadId)))
				;
	}

	@Override
	public void unlock() {
		int threadId = (int) (Thread.currentThread().getId() % num); // get current thread id
		flag[threadId] = new AtomicBoolean(false); // the current thread no longer interested to the critical
													// section
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