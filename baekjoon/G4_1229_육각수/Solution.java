
public class Solution {
  static int MAX = 50;

  public static void main(String[] args) {
    int[] hexArr = new int[MAX];

    // hex계산
    hexArr[0] = 1;
    int dup = 1;
    for (int idx = 1; idx < MAX; idx++) {
      hexArr[idx] = hexArr[idx - 1] + idx * 6 - dup;
      dup += 2;
      System.out.println(hexArr[idx]);
    }
  }
}
