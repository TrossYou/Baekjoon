package SWEA.D4_1251;

import java.util.*;
import java.io.*;

public class Solution_ai_prim {
  static double E;
  static int[] xArr, yArr;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      int N = Integer.parseInt(br.readLine());
      xArr = new int[N];
      yArr = new int[N];

      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        xArr[i] = Integer.parseInt(st.nextToken());

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        yArr[i] = Integer.parseInt(st.nextToken());

      E = Double.parseDouble(br.readLine());

      long totalDist2 = primN2(N);
      long ans = Math.round(E * totalDist2);

      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }

    System.out.print(sb);
  }

  static long primN2(int n) {
    boolean[] visited = new boolean[n];
    long[] minDist = new long[n];
    Arrays.fill(minDist, Long.MAX_VALUE);
    minDist[0] = 0L;

    long totalDist2 = 0L;

    for (int iter = 0; iter < n; iter++) {
      int minV = -1;
      long min = Long.MAX_VALUE;

      for (int i = 0; i < n; i++) {
        if (!visited[i] && minDist[i] < min) {
          min = minDist[i];
          minV = i;
        }
      }

      visited[minV] = true;
      totalDist2 += min;

      for (int v = 0; v < n; v++) {
        if (visited[v])
          continue;

        long dx = (long) xArr[minV] - xArr[v];
        long dy = (long) yArr[minV] - yArr[v];
        long dist2 = dx * dx + dy * dy;

        if (dist2 < minDist[v])
          minDist[v] = dist2;
      }
    }

    return totalDist2;
  }
}
