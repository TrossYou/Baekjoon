import java.io.*;
import java.util.*;

public class Solution {
  static int N, ans, start;
  static int[][] arr;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      ans = 1;
      start = 0;
      arr = new int[N * N + 1][2]; // [i][0], [i][1] : i번의 (r, c)좌표

      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < N; j++) {
          int v = Integer.parseInt(st.nextToken());
          arr[v][0] = i;
          arr[v][1] = j;
        }
      }

      play();
      sb.append('#').append(tc).append(' ').append(start).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    int cnt = 1;
    for (int i = N * N; i > 1; i--) { // 시작점 계산 편의를 위해, 역순으로 검사
      int r = arr[i][0];
      int c = arr[i][1];

      int nextR = arr[i - 1][0];
      int nextC = arr[i - 1][1];
      // x값 하나 차이나거나 y값 하나 차이나면 이동 가능
      if ((r == nextR && Math.abs(c - nextC) == 1) || (c == nextC && Math.abs(r - nextR) == 1)) {
        cnt++;
        continue;
      }

      // 1차이나지 않으면, 끝남
      if (cnt >= ans) {
        ans = cnt;
        start = i; // 현재가 시작
      }
      cnt = 1; // 다시 초기화
    }
    // 마지막 1까지 포함되는 경우
    if (cnt >= ans) {
      ans = cnt;
      start = 1;
    }
  }
}
