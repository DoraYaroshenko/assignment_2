import java.util.ArrayList;
import java.util.Collections;

public class experiment {

	public static void experiment1(double n) {
		int sizeOfHeap = 0;
		int linksNum = 0;
		int cutsNum = 0;
		int treesNum = 0;
		long timeSum = 0;
		FibonacciHeap fh = new FibonacciHeap();
		ArrayList<Integer> indexes = new ArrayList<>();
		for (int j = 1; j < n + 1; j++) {
			indexes.add(j);
		}
		for (int i = 0; i < 20; i++) {
			Collections.shuffle(indexes);
			long startTime = System.currentTimeMillis();
			for (int j = 0; j < n; j++) {
				fh.insert(indexes.get(j), String.valueOf(indexes.get(j)));
			}
			fh.deleteMin();
			sizeOfHeap += fh.size();
			linksNum += fh.totalLinks();
			cutsNum += fh.totalCuts();
			treesNum += fh.numTrees();
			long endTime = System.currentTimeMillis();
			timeSum += endTime - startTime;
		}
		System.out.println("First experiment with n=" + n);
		System.out.println("Average size: " + sizeOfHeap / 20);
		System.out.println("Average links: " + linksNum / 20);
		System.out.println("Average cuts: " + cutsNum / 20);
		System.out.println("Average trees: " + treesNum / 20);
		System.out.println("Average time: " + timeSum / 20);
	}

	public static void experiment2(double n) {
		int sizeOfHeap = 0;
		int linksNum = 0;
		int cutsNum = 0;
		int treesNum = 0;
		long timeSum = 0;
		FibonacciHeap fh = new FibonacciHeap();
		ArrayList<Integer> indexes = new ArrayList<>();
		for (int j = 1; j < n + 1; j++) {
			indexes.add(j);
		}
		for (int i = 0; i < 20; i++) {
			Collections.shuffle(indexes);
			long startTime = System.currentTimeMillis();
			for (int j = 0; j < n; j++) {
				fh.insert(indexes.get(j), String.valueOf(indexes.get(j)));
			}
			for (int j = 0; j < n / 2; j++) {
				fh.deleteMin();
			}
			sizeOfHeap += fh.size();
			linksNum += fh.totalLinks();
			cutsNum += fh.totalCuts();
			treesNum += fh.numTrees();
			long endTime = System.currentTimeMillis();
			timeSum += endTime - startTime;
		}
		System.out.println("Second experiment with n=" + n);
		System.out.println("Average size: " + sizeOfHeap / 20);
		System.out.println("Average links: " + linksNum / 20);
		System.out.println("Average cuts: " + cutsNum / 20);
		System.out.println("Average trees: " + treesNum / 20);
		System.out.println("Average time: " + timeSum / 20);
	}

	public static void experiment3(double n) {
		int sizeOfHeap = 0;
		int linksNum = 0;
		int cutsNum = 0;
		int treesNum = 0;
		long timeSum = 0;
		FibonacciHeap fh = new FibonacciHeap();
		ArrayList<Integer> indexes = new ArrayList<>();
		for (int j = 1; j < n + 1; j++) {
			indexes.add(j);
		}
		for (int i = 0; i < 20; i++) {
			Collections.shuffle(indexes);
			long startTime = System.currentTimeMillis();
			FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[(int) n + 1];
			for (int j = 0; j < n; j++) {
				int key = indexes.get(j);
				nodes[key] = fh.insert(key, String.valueOf(key));
			}
			fh.deleteMin();
			int m = 1;
			while (fh.size() > (Math.pow(2, 5) - 1)&&(nodes.length - m)>2) {
				fh.delete(nodes[nodes.length - m]);
				m++;
			}
			sizeOfHeap += fh.size();
			linksNum += fh.totalLinks();
			cutsNum += fh.totalCuts();
			treesNum += fh.numTrees();
			long endTime = System.currentTimeMillis();
			timeSum += endTime - startTime;
		}
		System.out.println("Third experiment with n=" + n);
		System.out.println("Average size: " + sizeOfHeap / 20);
		System.out.println("Average links: " + linksNum / 20);
		System.out.println("Average cuts: " + cutsNum / 20);
		System.out.println("Average trees: " + treesNum / 20);
		System.out.println("Average time: " + timeSum / 20);
	}

	public static void main(String[] args) {
		for (int i = 1; i < 6; i++) {
			experiment1(Math.pow(3, (i + 7)) - 1);
			experiment2(Math.pow(3, (i + 7)) - 1);
			experiment3(Math.pow(3, (i + 7)) - 1);
		}
	}

}
