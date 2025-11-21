import java.util.*;

public class BAEK_S2_1927 {
	static int N, last;
	static int[] minHeap;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		minHeap = new int[N + 1];
		for (int time = 0; time < N; time++) {
			int x = sc.nextInt();
			if (x == 0) {
				System.out.println(pop());
			} else {
				insert(x);
			}
		}
	}

	public static void insert(int x) {
		minHeap[++last] = x;
		int target = last;
		while (target / 2 > 0 && minHeap[target / 2] > x) {
			int parent = minHeap[target / 2];
			minHeap[target / 2] = x;
			minHeap[target] = parent;
			target /= 2;
		}
	}

	public static int pop() {
		if (last == 0)
			return 0;

		int result = minHeap[1];
		minHeap[1] = minHeap[last--];

		int target = 1;

		while (target * 2 <= last) {
			int left = target * 2;
			int right = target * 2 + 1;
			int childIdx = left;
			
			if(right <= last && minHeap[right] < minHeap[left]){
				childIdx = right;
			}
			
			if(minHeap[childIdx] < minHeap[target]) {
				int child = minHeap[childIdx];
				minHeap[childIdx] = minHeap[target];
				minHeap[target] = child;
				target = childIdx;
			} else {
				break;
			}
		}
		return result;
	}
}
