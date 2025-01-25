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
	public HeapNode insert(int key, String info) {
		HeapNode newNode = new HeapNode(key, info);
		newNode.rank = 0;
		addTree(newNode);
		numOfNodes++;
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
				totalCuts++;
				meld(toMeld);
			}
			else {
				removeMin();
			}
			this.numOfNodes--;
			this.numTrees=this.numTrees+countChildren-1;
			if(numTrees!=0) {//only consolidate if we didn't delete the last node in the heap
				consolidate();
			}
			
		}
	}
	/**
	 * deleteMin function when called from the method Delete(). implemented without successive linking
	 * @param actualMin: the actual minimum of the heap, as to delete node x, it was made the temporary minimum
	 */
	
	public void deleteMin(HeapNode actualMin) {
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
			this.min=actualMin;
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
		HeapNode currRoot=this.min; 
		//tempPointer=currRoot;
		HeapNode dummyNode=new HeapNode(-1, null);
		dummyNode.prev=currRoot.prev;
		dummyNode.next=currRoot;
		currRoot.prev=dummyNode;
		dummyNode.prev.next=dummyNode;
		while(currRoot!=dummyNode) {
			if(arrRanks[currRoot.rank]!=currRoot) {
				while(arrRanks[currRoot.rank]!=null) {
					HeapNode newParent=	link(currRoot, arrRanks[currRoot.rank]);		
					arrRanks[newParent.rank-1]=null;
					if(isNewMin(currRoot)) {
						this.min=currRoot;
					}
					currRoot=newParent;
				}
				arrRanks[currRoot.rank]=currRoot;
				if(isNewMin(currRoot)) {
					this.min=currRoot;
				}
				currRoot=currRoot.next;
			}
			else {
				break;
			}
		}
		removeNode(dummyNode);
	}
		
	
	
	/**
	 * links two binomial trees of the same rank. returns the node that became the parent of the other.
	 * 
	 * 
	 * @param node1: binomial tree of some rank k
	 * @param node2: binomial tree of some rank k
	 */
	
	private HeapNode link(HeapNode node1, HeapNode node2) {
		if(node1.key>node2.key) {
			HeapNode temp=node1;
			node1=node2;
			node2=temp;
		}
		if(isNewMin(node1)) {
			this.min=node1;
		}
		if(node1.key==node2.key && node2==this.min) {
			this.min=node1;
		}
		replaceChild(node1, node2);
		node1.rank++;
		this.numTrees--;
		this.totalLinks++;
		return node1;
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
			node2.next=node2;
			node2.prev=node2;
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
			removeNode(min);
			this.min=min.next;
		}
	}
	
	/**
	 * remove node from roots list
	 * @param node
	 */
	private void removeNode(HeapNode node) {
		node.next.prev=node.prev;
		node.prev.next=node.next;
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
	public void decreaseKey(HeapNode x, int diff) {
		if(x!=null) {
			x.key = x.key - diff;
			if (x.parent != null && x.key < x.parent.key) {
				cascadingCuts(x);
			}
			if (x.key < this.min.key) {
				this.min = x;
			}
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
	 * time complexity is the sum of the time complexity of decreaseKey and deleteMin. therefore:
	 * worst case: O(n)
	 * amortized: O(logn)
	 *
	 */
	public void delete(HeapNode x) 
	{ 
		if(x!=null) {
			if(x.key!=this.min.key) {
				HeapNode tempMinPointer=this.min;
				decreaseKey(x, Integer.MIN_VALUE);
				deleteMin(tempMinPointer);
			}
			else {
				deleteMin();
			}
		}
		
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
	public void meld(FibonacciHeap heap2) {
		if(heap2!=null) {
			if(this.min==null) {
				this.min=heap2.min;
			}
			else {
				this.min.next.prev = heap2.min.prev;
				heap2.min.prev.next = this.min.next;
				this.min.next = heap2.min;
				heap2.min.prev = this.min;
				if(heap2.min.key<this.min.key) {
					this.min = heap2.min;
				}
			}
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
	
	public boolean empty() {
		return (this.min==null);
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
		
		public HeapNode(int key, String info) {
			this.key = key;
			this.info = info;
		}
		public int getKey() {
			return this.key;
		}
	}
}
