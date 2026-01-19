import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
  private static int[][] rotate90()

  public static void main(String args[]) throws Exception {
    System.setIn(new FileInputStream("res/input.txt"));

    Scanner sc = new Scanner(System.in);
    int T;
    T = sc.nextInt();
    for (int test_case = 1; test_case <= T; test_case++) {
      int N = sc.nextInt();
      int map[][] = new int[N][N];
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          map[i][j] = sc.nextInt();
        }
      }

    }
  }
}
