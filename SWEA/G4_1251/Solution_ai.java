package SWEA.G4_1251;

import java.io.*;
import java.util.*;

public class Solution_ai {
  // long 하나에 비트 패킹: [dist2 (41bit) | a (10bit) | b (10bit)]
  static int N, edgeIdx;
  static double E, ans;
  static int[] islandX, islandY;
  static int[] parents;
  static long[] edges;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      ans = 0;
      edgeIdx = 0;
      edges = new long[N * (N - 1) / 2];
      islandX = new int[N];
      islandY = new int[N];
      parents = new int[N];
      for (int i = 0; i < N; i++)
        parents[i] = -1;

      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        islandX[i] = Integer.parseInt(st.nextToken());

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        islandY[i] = Integer.parseInt(st.nextToken());

      E = Double.parseDouble(br.readLine());

      // 간선 생성: dist2 기준 비트 패킹
      for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
          long dx = islandX[i] - islandX[j];
          long dy = islandY[i] - islandY[j];
          long dist2 = dx * dx + dy * dy;
          edges[edgeIdx++] = (dist2 << 20) | ((long) i << 10) | j;
        }
      }

      // primitive long[] 정렬 → dist2 기준 자동 정렬 (E는 상수이므로 순서 동일)
      Arrays.sort(edges, 0, edgeIdx);

      for (int i = 0; i < edgeIdx; i++) {
        int a = (int) ((edges[i] >> 10) & 0x3FF);
        int b = (int) (edges[i] & 0x3FF);
        long dist2 = edges[i] >> 20;
        if (union(a, b)) {
          ans += E * dist2; // E 곱셈은 선택된 N-1개에만
        }
      }

      sb.append('#').append(tc).append(' ').append(Math.round(ans)).append('\n');
    }

    System.out.print(sb);
  }

  static int find(int a) {
    if (parents[a] < 0)
      return a;
    return parents[a] = find(parents[a]);
  }

  static boolean union(int a, int b) {
    int aRoot = find(a);
    int bRoot = find(b);

    if (aRoot == bRoot)
      return false; // 병합 안됨

    if (parents[aRoot] <= parents[bRoot]) { // a그룹의 크기가 더 큼
      parents[aRoot] += parents[bRoot];
      parents[bRoot] = aRoot;
    } else {
      parents[bRoot] += parents[aRoot];
      parents[aRoot] = bRoot;
    }
    return true;
  }
}
