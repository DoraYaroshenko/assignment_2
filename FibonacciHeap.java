/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap {
	public HeapNode min;
	public int totalCuts;
	public int totalLinks;
	public int numTrees;

	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap() {
		this.min = null;
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
		addTree(newNode);
		return newNode;
	}

	private void handleInsertionToEmptyTree(HeapNode newNode) {
		this.min = newNode;
		newNode.next = newNode;
		newNode.prev = newNode;
	}

	private void handleInsertionWhenTreeNotEmpty(HeapNode newNode) {
		newNode.prev = this.min;
		newNode.next = this.min.next;
		this.min.next.prev = newNode;
		this.min.next = newNode;
	}

	private void addTree(HeapNode x) {
		if (this.min == null) {
			handleInsertionToEmptyTree(x);
		} else {
			handleInsertionWhenTreeNotEmpty(x);
		}
		if (this.min.key > x.key)
			this.min = x;
		this.numTrees += 1;
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
		x.key = x.key - diff;
		if (x.parent != null && x.key < x.parent.key) {
			cascadingCuts(x);
		}
		if (x.key < this.min.key) {
			this.min = x;
		}
	}

	private void cascadingCuts(HeapNode x) {
		HeapNode parent = x.parent;
		cut(x);
		if (parent.parent != null) {
			if (!parent.mark) {
				parent.mark = true;
			} else {
				cascadingCuts(parent);
			}
		}
	}

	private void cut(HeapNode x) {
		HeapNode parent = x.parent;
		x.parent = null;
		x.mark = false;
		parent.rank = parent.rank - 1;
		if (x == x.next) {
			parent.child = null;
		} else {
			parent.child = x.next;
			x.prev.next = x.next;
			x.next.prev = x.prev;
		}
		totalCuts+=1;
		addTree(x);
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
		return this.totalLinks;
	}

	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts() {
		return this.totalCuts;
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		this.min.next.prev = heap2.min.prev;
		heap2.min.prev.next = this.min.next;
		this.min.next = heap2.min;
		heap2.min.prev = this.min;
		
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
		return this.numTrees;
	}

	public void printHeap() {
		HeapNode node = this.min;
		while (node.next != this.min) {
			System.out.println("Prev: " + node.prev.key + " Node: " + node.key + " Next: " + node.next.key);
			printTree(node);
			node = node.next;
		}
		System.out.println("Prev: " + node.prev.key + " Node: " + node.key + " Next: " + node.next.key);
		System.out.println("Number of trees: " + this.numTrees);
	}

	public void printTree(HeapNode x) {
		HeapNode child = x.child;
		while (child != null) {
			HeapNode node = child;
			while (node.next != child) {
				System.out.print(node.key + " ");
				node = node.next;
			}
			System.out.print(node.key + " ");
			child = node.next.child;
		}
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
