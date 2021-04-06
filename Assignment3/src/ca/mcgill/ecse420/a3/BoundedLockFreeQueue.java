package ca.mcgill.ecse420.a3;

/**
 * The code written BoundedLockBasedQueue class is used as starting point.
 */
public class BoundedLockFreeQueue<T> {
	T[] items;// Array
	volatile int head;// items[0]
	volatile int tail;// items[items.length-1]
	int capacity;// bounded size

	/**
	 * Queue constructor
	 * 
	 * @param size
	 */
	@SuppressWarnings("unchecked")
	public BoundedLockFreeQueue(int size) {
		head = 0; // first element in the array
		tail = head; // since the list is empty, it is same with the head
		capacity = size; // define the size of the array list
		items = (T[]) new Object[capacity]; // define array with the give capacity
	}

	/**
	 * Add the item as the last element of the queue
	 * 
	 * @param x
	 */
	public void enq(T x) {
		while (tail - head == capacity)
			continue;// if the queue is full wait until its size decreases
		items[tail % items.length] = x;// add new element to the array
		tail++;// increase the index of tail
	}

	/**
	 * Remove first element in queue
	 * 
	 * @return removed item (head of the array)
	 * 
	 */
	public T deq() {
		while (tail - head == 0)
			continue;// if queue is empty wait until an element is enqueued
		T result = items[head % items.length]; // remove the head of the queue
		head++;// move the starting point
		return result;
	}

}
