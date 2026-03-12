package SWEA.D4_1251;

import java.util.*;
import java.io.*;

public class Solution {
  static long ans;
  static int N;
  static int[] xArr, yArr;
  static long[] minDist;
  // minDist[]: MST에서부터 i섬까지 연결된 거리 중 최소
  static boolean[] visited;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      ans = 0L;
      N = Integer.parseInt(br.readLine());
      xArr = new int[N];
      yArr = new int[N];
      minDist = new long[N];
      visited = new boolean[N];

      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        xArr[i] = Integer.parseInt(st.nextToken());

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        yArr[i] = Integer.parseInt(st.nextToken());

      double E = Double.parseDouble(br.readLine());

      long totalDist = play();
      ans = Math.round(E * totalDist);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static long play() {
    long totalDist = 0L;
    minDist[0] = 0L; // 0번 섬 추가
    visited[0] = true;
    int cnt = 1;

    for (int i = 1; i < N; i++) {
      minDist[i] = getDist(0, i); // 일단 0으로 리셋
    }

    while (cnt != N) {
      int minIdx = -1;
      long minD = Long.MAX_VALUE; // 최소 거리

      // 현재 MST에서 거리가 가장 가까운 섬 찾기
      for (int i = 0; i < N; i++) {
        if (!visited[i] && minD > minDist[i]) {
          minD = minDist[i];
          minIdx = i;
        }
      }

      visited[minIdx] = true; // 방문
      totalDist += minD;
      cnt++;

      for (int i = 0; i < N; i++) { // minIdx를 minDist에 갱신
        if (!visited[i]) {
          long dist = getDist(minIdx, i);
          if (dist < minDist[i]) {
            minDist[i] = dist;
          }
        }
      }
    }
    return totalDist;
  }

  static long getDist(int a, int b) {
    long dx = (long) xArr[a] - xArr[b];
    long dy = (long) yArr[a] - yArr[b];

    return dx * dx + dy * dy;
  }
}
