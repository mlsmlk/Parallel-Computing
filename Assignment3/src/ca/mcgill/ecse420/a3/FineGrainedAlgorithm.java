package ca.mcgill.ecse420.a3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author malki
 *
 * @param <T>
 */
/**
 * @author malki
 *
 * @param <T>
 */
public class FineGrainedAlgorithm<T> {
	private Node head = null;
	private Lock lock = new ReentrantLock();

	/**
	 * Node definition, the fields were selected according to the add() example in
	 * book
	 *
	 */
	private class Node {
		T item;
		int key;
		Node next;

		public void lock() {
			lock.lock();
		}

		public void unlock() {
			lock.unlock();
		}
	}

	/**
	 * This method uses hand-over-hand shake like add() method to check if the
	 * current node contains requested item.
	 * 
	 * @param item
	 * @return If nodes key and item matches with the requested item and its key
	 *         returns true otherwise it returns false.
	 */
	public boolean contains(T item) {
		int key = item.hashCode(); // get key of the requested item for matching
		head.lock(); // lock first node, head to use as starting node
		Node pred = head;
		try {
			Node curr = pred.next; // move to next node
			curr.lock(); // lock the node to prevent any possible removing or editing by another thread.
			try {
				while (curr.key < key) { // since the list is built with key ordering, the algorithm can avoid the keys
											// smaller than the requested item's key
					pred.unlock(); // if the first locked node gets in the loop, the algorithm free the node
									// because it is done with the node
					pred = curr; // move to next node
					curr = curr.next;
					curr.lock(); // lock the second node which is after the second locked node
				}
				if (curr.key == key && curr.item == item) { // if the current node key is not smaller than the required
															// key, it might be the nod the algorithm looks for so check
															// if the node is matching with the requested item
					return true;
				}
				return false;

			} finally { // unlock the whole locked nodes since the algorithm has return value and it is
						// done with the list
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	/**
	 * This methods was taken from the book page 202 to create list for testing.
	 * "The method uses hand-over-hand locking to traverse the list.The finally
	 * blocks release lock before returning."
	 * 
	 * @param item
	 * @return true if addition to list is successful false otherwise
	 */
	public boolean add(T item) {
		int key = item.hashCode();
		head.lock();
		Node pred = head;
		try {
			Node curr = pred.next;
			curr.lock();
			try {
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				if (curr.key == key) {
					return false;
				}
				Node newNode = new Node();
				newNode.item = item;
				newNode.next = curr;
				pred.next = newNode;
				return true;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}
}
