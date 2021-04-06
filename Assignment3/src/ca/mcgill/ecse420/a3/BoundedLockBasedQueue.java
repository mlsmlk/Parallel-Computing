package ca.mcgill.ecse420.a3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The code base was taken from the book page 225 and 226 and the code was
 * modified according to the stated requirements in assignment
 */
public class BoundedLockBasedQueue<T> {
	T[] items;// Array
	ReentrantLock enqLock, deqLock; // Lock definition for both enqueue and dequeue
	Condition notEmptyCondition, notFullCondition; // conditions depending on the size to activate locks
	volatile int head;// items[0]
	volatile int tail;// items[items.length-1]
	int capacity;// bounded size

	/**
	 * Queue constructor
	 * 
	 * @param size
	 */
	@SuppressWarnings("unchecked")
	public BoundedLockBasedQueue(int size) {
		head = 0; // first element in the array
		tail = head; // since the list is empty, it is same with the head
		enqLock = new ReentrantLock(); // lock initialization for enqueue
		notFullCondition = enqLock.newCondition(); // define condition for enqueue lock
		deqLock = new ReentrantLock();// lock initialization for dequeue
		notEmptyCondition = deqLock.newCondition(); // define condition for dequeue lock
		capacity = size; // define the size of the array list
		items = (T[]) new Object[capacity]; // define array with the give capacity
	}

	/**
	 * Add the item as the last element of the queue
	 * 
	 * @param x
	 * @throws InterruptedException
	 */
	public void enq(T x) throws InterruptedException {
		boolean mustWakeDequeuers = false; // trigger initialization for transition to dequeue
		enqLock.lock();// lock enqueue process for other threads while this thread runs the algorithm
		try {
			while (tail - head == capacity)// if the queue is full
				notFullCondition.await();// wait until the queue is not full
			items[tail % items.length] = x;// add new element to the array
			tail++;// increase the index of tail
			mustWakeDequeuers = tail - head == 1; // if queue is empty, wake dequeuer
		} finally {
			enqLock.unlock();// release enqueue for other threads
		}
		if (mustWakeDequeuers) { // wake dequeuer
			deqLock.lock(); // lock dequeue
			try {
				notEmptyCondition.signalAll();// signal dequeuers that queue is no longer empty
			} finally {
				deqLock.unlock();// unlock dequeue
			}
		}
	}

	/**
	 * Remove first element in queue
	 * 
	 * @return removed item (head of the array)
	 * @throws InterruptedException
	 */
	public T deq() throws InterruptedException {
		T result;
		boolean mustWakeEnqueuers = false;// trigger initialization for waking enqueuers
		deqLock.lock();// lock dequeue for other threads
		try {
			while (tail - head == 0)// if queue is empty
				notEmptyCondition.await();// wait until queue is not empty
			result = items[head % items.length]; // remove the head of the queue
			head++;// move the starting point
			mustWakeEnqueuers = tail - head == items.length - 1; // if the queue reach the capacity, wake enqueuer
		} finally {
			deqLock.unlock();// release dequque function for other threads
		}
		if (mustWakeEnqueuers) {// wake enqueuer
			enqLock.lock();// lock enqueue temporarily
			try {
				notFullCondition.signalAll(); // signal enqueuers that queue is no longer full
			} finally {
				enqLock.unlock();// release enqueue
			}
		}
		return result;
	}

}
