/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap {
	public HeapNode min;
	public HeapNode first;

	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap() {
		this.min=null;
		this.first=null;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) {
		HeapNode newNode = new HeapNode(key, info);
		newNode.rank = 0;
		int caseNum = determineInsertionCase();
		switch (caseNum) {
		case 0:
			handleInsertionToEmptyTree(newNode);
			break;
		case 1:
			handleInsertionWhenMinHasNextAndPrev(newNode);
			break;
		case 2:
			handleInsertionWhenMinHasNoPrevAndNoNext(newNode);
			break;
		case 3:
			handleInsertionWhenMinHasPrevAndNoNext(newNode);
			break;
		}
		if (this.min.key > newNode.key)
			this.min = newNode;
		return newNode;
	}

	private void handleInsertionToEmptyTree(HeapNode newNode) {
		this.min = newNode;
		this.first = newNode;
		newNode.next = newNode;
		newNode.prev = newNode;
	}

	private void handleInsertionWhenMinHasNextAndPrev(HeapNode newNode) {
		newNode.prev = this.min;
		newNode.next = this.min.next;
		this.min.next.prev = newNode;
		this.min.next = newNode;
	}

	private void handleInsertionWhenMinHasNoPrevAndNoNext(HeapNode newNode) {
		this.min.next = newNode;
		this.min.prev = newNode;
		newNode.prev = this.min;
		newNode.next = this.min;
	}
	
	private void handleInsertionWhenMinHasPrevAndNoNext(HeapNode newNode) {
		this.min.next=newNode;
		newNode.prev=this.min;
		newNode.next = this.first;
		this.first.prev = newNode;
	}

	private int determineInsertionCase() {
		if (this.min == null)
			return 0;
		boolean hasPrev = this.min != this.first;
		boolean hasNext = this.min.next != this.first;
		if (hasNext)
			return 1;
		if (!hasNext && !hasPrev)
			return 2;
		if (!hasNext && hasPrev)
			return 3;
		return -1;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin() {
		return null; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() {
		return; // should be replaced by student code

	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap.
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks() {
		return 0; // should be replaced by student code
	}

	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts() {
		return 0; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return 42; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {
		return 0; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 * 
	 */
	public static class HeapNode {
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;

		public HeapNode(int key, String info) {
			this.key = key;
			this.info = info;
		}
	}
}
