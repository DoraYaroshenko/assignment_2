import java.util.ArrayList;
import java.util.Collections;

import FibonacciHeap.HeapNode;

public class test22 {
	
    private static boolean checkMinHeapProperty(FibonacciHeap heap) {
        FibonacciHeap.HeapNode current = heap.min;
        if (current == null) return true;

        // Traverse the root list and check the min-heap property
        do {
            // Check if the current node's key is smaller than its children's keys
            if (current.child != null) {
                FibonacciHeap.HeapNode child = current.child;
                do {
                    if (current.key > child.key) {
                        return false; // Min-heap property violated
                    }
                    child = child.next;
                } while (child != current.child);
            }
            current = current.next;
        } while (current != heap.min);

        return true; // Min-heap property is satisfied
    }
	
    public static void t22() { // test22
        System.out.println("Running test22...");
        FibonacciHeap heap = new FibonacciHeap();

        int treeSize = 10;
        int sizeToDelete = 9;

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = treeSize; i < treeSize * 2; i++) {
            nodes.add(heap.insert(i, String.valueOf(i)));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.deleteMin();
        }
        // Check the heap structure after deletions
        if (!checkMinHeapProperty(heap)) throw new AssertionError("Min-Heap property violated.");

        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        boolean noCascading = true;
        int iterationCuts;

//        Collections.shuffle(nodes);
        ArrayList<FibonacciHeap.HeapNode> nodes2 = new ArrayList<>();
        int [] arr = {17,19,11,10,12,15,14,13,18,16};
        for(int num:arr) {
        	FibonacciHeap.HeapNode addedNode = null;
        	for(FibonacciHeap.HeapNode node:nodes) {
        		if(node.key==num) addedNode=node;
        	}
        	nodes2.add(addedNode);
        }
        nodes = nodes2;

        for (int i = 0; i < treeSize; i++) {
            iterationCuts = heap.totalCuts();

            heap.decreaseKey(nodes.get(i), nodes.get(i).getKey() - (treeSize - i));

            if (heap.totalCuts() - iterationCuts > 1) noCascading = false;
        }
        // Check cuts, links, and cascading cut behavior
//        if (heap.totalCuts() - totalCuts != treeSize - 1) throw new AssertionError("Cuts count mismatch");
        if (heap.totalLinks() - links != 0) throw new AssertionError("No links should have occurred");
        if (!noCascading) throw new AssertionError("Cascading cuts detected");

        // Ensure number of trees is as expected
        if (heap.size() != treeSize) throw new AssertionError("Tree size mismatch");

        System.out.println("✅ Test22 passed");
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
            t22(); // test22
            System.out.println("All tests passed successfully! ✅");
        } catch (AssertionError e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
	}

}
