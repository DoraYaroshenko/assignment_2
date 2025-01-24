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
		h.printHeap();
		return h.min.next.key==101&&h.min.next.info.equals("101");
	}
	
	public static void testMeld() {
		FibonacciHeap f = createHeap();
		FibonacciHeap h = createHeap();
		f.printHeap();
		h.printHeap();
		f.meld(h);
		f.printHeap();
	}

	public static void main(String[] args) {
		System.out.println(testInsert());
		testMeld();

	}

}
