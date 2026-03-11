package SWEA.D4_1251;

import java.io.*;
import java.util.*;

public class Solution {
  static class Bridge implements Comparable<Bridge> {
    int a, b;
    double penalty;

    Bridge(int a, int b, double penalty) {
      this.a = a;
      this.b = b;
      this.penalty = penalty;
    }

    @Override
    public int compareTo(Bridge o) {
      return Double.compare(this.penalty, o.penalty);
    }
  }

  static int N, bridgeIdx;
  static double E, ans;
  static int[] islandX, islandY;
  static int[] parents;
  static Bridge[] bridges;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      ans = 0;
      bridgeIdx = 0;
      bridges = new Bridge[N * (N - 1) / 2];
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

      for (int i = 1; i < N; i++)
        calcBridge(i);

      Arrays.sort(bridges, 0, bridgeIdx);

      int selectedBridgeCnt = 0;
      for (int i = 0; i < bridgeIdx; i++) {
        Bridge bridge = bridges[i];
        if (union(bridge.a, bridge.b)) {
          ans += bridge.penalty;
          if (++selectedBridgeCnt == N - 1)
            break;
        }
      }
      sb.append('#').append(tc).append(' ').append(Math.round(ans)).append('\n');
    }

    System.out.print(sb);
  }

  static void calcBridge(int a) {
    for (int i = 0; i < a; i++) {
      bridges[bridgeIdx++] = new Bridge(i, a, getPenalty(i, a));
    }
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
      return false;

    if (parents[aRoot] <= parents[bRoot]) {
      parents[aRoot] += parents[bRoot];
      parents[bRoot] = aRoot;
    } else {
      parents[bRoot] += parents[aRoot];
      parents[aRoot] = bRoot;
    }
    return true;
  }

  static double getPenalty(int a, int b) {
    long dx = islandX[a] - islandX[b];
    long dy = islandY[a] - islandY[b];
    return E * (dx * dx + dy * dy);
  }
}
