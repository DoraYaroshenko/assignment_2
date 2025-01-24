import java.util.ArrayList;
import java.math.*;
/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	public HeapNode min;
	public int totalCuts;
	public int totalLinks;
	public int numTrees;
	public int numOfNodes;
	
	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap()
	{
		this.min=null;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) 
	{    
		return null; // should be replaced by student code
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 * O(1) time complexity as the function simply returns a property of the Heap.
	 *
	 */
	public HeapNode findMin()
	{
		return this.min; 
	}

	/**
	 * 
	 * Delete the minimal item
	 * 
	 * overall time complexity is the sum of the time complexity of the actual deletion and the consolidation.
	 * the actual deletion does constant work on the children of min (O(logn) children) and then calls meld.
	 * therefore in both worst case and amortized case it has O(log(n)) time complexity
	 * it was shown during the lecture that the consolidation has O(n) worst case time complexity and O(log(n))
	 * amortized time complexity.
	 * 
	 * time complexity of deleteMin() is:
	 * O(n) worst case
	 * O(log(n)) amortized
	 *
	 */
	
	
	public void deleteMin()
	{
		if(this.min==null) {
			//heap is empty
		}
		else {
			int countChildren=this.min.rank;
			if(this.min.child!=null) {
				HeapNode minOfChildren=heapOfChildrenHelper(this.min);
				FibonacciHeap toMeld=new FibonacciHeap();
				toMeld.min=minOfChildren;
				removeMin();
				meld(toMeld);
			}
			else {
				removeMin();
			}
			this.numOfNodes--;
			this.numTrees=this.numTrees+countChildren-1;
			consolidate();
			
		}
		
	}
	/**
	 * 
	 * consolidating/successive linking
	 * worst time O(n)
	 * amortized time complexity O(log(n))
	 *
	 */
	private void consolidate() {
		int maxRank=log2(this.size())+1;
		HeapNode[] arrRanks=new HeapNode[maxRank];
		HeapNode currRoot=this.min, tempPointer=currRoot;
		while(1==1) 
		{
			while(arrRanks[currRoot.rank]!=null) {
				arrRanks[currRoot.rank]=null;
				link(currRoot, arrRanks[currRoot.rank]);
				if(isNewMin(currRoot)) {
					this.min=currRoot;
				}
				arrRanks[currRoot.rank]=currRoot;
			}
			if(currRoot.next!=tempPointer) {
				currRoot=currRoot.next;
			}
			else {
				break;
			}
			
		}
		
	}
	
	/**
	 * links two binomial trees of the same rank.
	 * 
	 * 
	 * @param node1: binomial tree of some rank k
	 * @param node2: binomial tree of some rank k
	 */
	
	private void link(HeapNode node1, HeapNode node2) {
		if(node1.key>node2.key) {
			HeapNode temp=node1;
			node1=node2;
			node2=temp;
		}
		if(isNewMin(node1)) {
			this.min=node1;
		}
		replaceChild(node1, node2);
		node1.rank++;
		this.numTrees--;
		this.totalLinks++;
	}
	
	/**
	 * adds node2 to node1's children so that node1.child=node2
	 * 
	 * @param node1 a node to which we want to add node2 as a child
	 * @param node2 node with key smaller than node1.key to become another child of node1
	 * 
	 * time complexity in worst case and amortized is O(1)
	 */
	
	private void replaceChild(HeapNode node1, HeapNode node2) {
		node2.next.prev=node2.prev;
		node2.prev.next=node2.next;
		if(node1.child==null) {
			node1.child=node2;
			node2.parent=node1;
		}
		else {
			HeapNode tempChild=node1.child;
			node2.next=tempChild;
			node2.prev=tempChild.prev;
			tempChild.prev.next=node2;
			tempChild.prev=node2;
			node1.child=node2;
			node2.parent=node1;
		}
		
	}
	
	/**
	 * checks if node should become the new min
	 * 
	 * @param node: the node that is being checked
	 * @return: true if node's key is smaller than min's key, false otherwise
	 * 
	 * time complexity O(1) in both worst case and amortized time.
	 */
	private boolean isNewMin(HeapNode node) {
		return (node.key<this.min.key);
	}
	
	/**
	 * returns the logarithm of integer x in base 2
	 * 
	 * @param x
	 * @return: logarithm of integer x in base 2 rounded down
	 * 
	 * time complexity O(1) in both worst case and amortized time.
	 */
	private int log2(int x) {
		int result = (int)(Math.log(x) / Math.log(2));
		return result;
	}
	
	/**
	 * 
	 * deletes min from roots list
	 * time complexity O(1)
	 *
	 */
	
	private void removeMin() {
		if (this.min.next==this.min) {//the min is the only node in the heap
			this.min=null;
		}
		else {
			this.min.next.prev=this.min.prev;
			this.min.prev.next=this.min.next;
			this.min=min.next;
		}
	}
	/**
	 * 
	 * finds the minimum out of the children of node, makes all the children roots.
	 * 
	 * time complexity O(1*[num of children node has]), node has O(log(n)) children, therefore
	 * time complexity is O(log(n)) in both worst case and amortized time.
	 *
	 */
	
	private HeapNode heapOfChildrenHelper(HeapNode node) {
		HeapNode minOfChildren=null;
		if(node.child!=null) {
			HeapNode pointerToGivenNode=node;
			HeapNode currNode=pointerToGivenNode.child, tempPointerToChild=currNode;
			minOfChildren=currNode;
			currNode.parent=null;
			while(currNode.next!=tempPointerToChild) 
			{
				currNode=currNode.next;
				if (currNode.key<minOfChildren.key) {
					minOfChildren=currNode;
				}
				currNode.parent=null;
			}
		}
		return minOfChildren;
	}
	

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the x from the heap.
	 * time complexity is the sum of the time complexity of decreaseKey and deleteMin. therefore:
	 * worst case: O(n)
	 * amortized: O(logn)
	 *
	 */
	public void delete(HeapNode x) 
	{    
		decreaseKey(x, Integer.MIN_VALUE);
		deleteMin();
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 * time complexity O(1) in both worst case and amortized time.
	 * 
	 */
	public int totalLinks()
	{
		return totalLinks; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 * time complexity O(1) in both worst case and amortized time.
	 * 
	 */
	public int totalCuts()
	{
		return totalCuts; // should be replaced by student code
	}


	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2)
	{
		if (this.min==null)//if current heap is empty
		{
			this.min=heap2.min;
			updatePropertiesPostMeld(heap2);
		}
		else if(heap2.min==null)//if heap2 is empty
		{
			//nothing changes
		}
		else if (this.min.key>heap2.min.key) {
			this.min.prev.next=heap2.min;
			heap2.min.prev.next=this.min;
			updatePropertiesPostMeld(heap2);
		}
	}
	
	private void updatePropertiesPostMeld(FibonacciHeap heap2) {
		this.totalCuts+=heap2.totalCuts;
		this.totalLinks+=heap2.totalLinks;
		this.numTrees+=heap2.numTrees;
		this.numOfNodes+=heap2.numOfNodes;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 * 
	 * time complexity O(1) in both worst case and amortized time.
	 *   
	 */
	public int size()
	{
		return numOfNodes;
		
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return numTrees; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode{
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;
	}
}
