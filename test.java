import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {
	
	public static FibonacciHeap createHeap() {
		FibonacciHeap fh = new FibonacciHeap();
		int num = (int)(Math.random()*100);
		Integer[] arr = new Integer[num];
//		System.out.println(num);
		for (int i = 0; i < num; i++) {
			arr[i] = i;
		}
		List<Integer> intList = Arrays.asList(arr);
		Collections.shuffle(intList);
		intList.toArray(arr);
		for(int i:arr) {
			fh.insert(i, String.valueOf(i));
		}
		return fh;
	}

	public static boolean testInsert() {
		FibonacciHeap h = createHeap();
		h.insert(101, String.valueOf(101));
		return h.min.next.key==101&&h.min.next.info.equals("101");
	}
	
	public static boolean testMeld() {
		FibonacciHeap f = createHeap();
		int numOfTreesBeforeMeld = f.numTrees;
		FibonacciHeap h = createHeap();
		f.meld(h);
		f.printHeap();
		return f.numTrees==numOfTreesBeforeMeld+h.numTrees;
	}

	public static void main(String[] args) {
		System.out.println(testInsert());
		System.out.println(testMeld());

	}

}
