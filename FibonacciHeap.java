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
	 *
	 */
	public HeapNode findMin()
	{
		return this.min; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	
	/**
	 * 
	 * 
	 * 
	 * delete the min, 
	 * meld the sub trees of the previous min with the heap
	 * consolidate until all roots have different ranks
	 * find new min
	 * 
	 * 
	 * 
	 */
	public void deleteMin()
	{
		if(this.min==null) {
			//heap is empty
		}
		else {
			int countChildren=countChildren(this.min);
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
		while(currRoot.next.next!=tempPointer) {
			while(arrRanks[currRoot.rank]!=null) {
				arrRanks[currRoot.rank]=null;
				link(currRoot, arrRanks[currRoot.rank]);
				if(isNewMin(currRoot)) {
					this.min=currRoot;
				}
				arrRanks[currRoot.rank]=currRoot;
			}
			currRoot=currRoot.next;
		}
		
	}
	
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
	
	private boolean isNewMin(HeapNode node) {
		return (node.key<this.min.key);
	}
	
	private int log2(int x) {
		int result = (int)(Math.log(x) / Math.log(2));
		return result;
	}
	
	/**
	 * 
	 * deletes min from root list
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
	 * time complexity O(1*[num of children node has]), based on potential function [[num of trees]+2*[marked nodes]]
	 * amortized time complexity is O(logn)
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
	/**bla
	 * 
	 * counts the number of children a node has, amortized time is O(log(n))
	 *
	 */
	private int countChildren(HeapNode node) {
		int counter=0;
		if(node.child!=null) {
			HeapNode pointerToGivenNode=node;
			HeapNode currNode=pointerToGivenNode.child, tempPointerToChild=currNode;
			while(currNode.next!=tempPointerToChild) 
			{
				currNode=currNode.next;
				counter++;
			}
		}
		return counter;
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
	 *
	 */
	public void delete(HeapNode x) 
	{    
		return; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return 0; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return 0; // should be replaced by student code
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
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return 42; // should be replaced by student code
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return 0; // should be replaced by student code
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
