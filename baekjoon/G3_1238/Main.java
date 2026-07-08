package Java.G3_1238;

import java.io.*;
import java.util.*;

public class Main {

  static final int INF = 1_000_000;
  static int N, M, X;
  static int[][] idjMap; // 인접 행렬

  static int[] dijkstra(boolean to) {
    int[] d = new int[N + 1];
    boolean[] visited = new boolean[N + 1];
    PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

    if (to) { // X로 가는 길
      for (int i = 1; i <= N; i++) {
        d[i] = idjMap[i][X];
        if (d[i] != INF)
          pq.add(new int[] { i, d[i] });
      }

      d[X] = 0;
      visited[X] = true;

      while (!pq.isEmpty()) {
        int[] road = pq.poll();
        int target = road[0];
        int dist = road[1];
        if (visited[target])
          continue;
        visited[target] = true; // visited으로 처리

        for (int i = 1; i <= N; i++) { // d갱신
          // if (target == i || i == X) // 없는게 더 빠를 수도..
          // continue;
          int tmpD = idjMap[i][target] + dist;
          if (tmpD < d[i]) {
            d[i] = tmpD;
            pq.add(new int[] { i, tmpD });
          }
        }
      }
    } else { // X에서 돌아가는 길
      for (int i = 1; i <= N; i++) {
        d[i] = idjMap[X][i];
        if (d[i] != INF)
          pq.add(new int[] { i, d[i] });
      }
      d[X] = 0;
      visited[X] = true;

      while (!pq.isEmpty()) {
        int[] road = pq.poll();
        int target = road[0];
        int dist = road[1];
        if (visited[target])
          continue;
        visited[target] = true; // visited으로 처리

        for (int i = 1; i <= N; i++) { // d갱신
          // if (target == i || i == X) // 없는게 더 빠를 수도..
          // continue;
          int tmpD = idjMap[target][i] + dist;
          if (tmpD < d[i]) {
            d[i] = tmpD;
            pq.add(new int[] { i, tmpD });
          }
        }
      }
    }

    return d;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    X = Integer.parseInt(st.nextToken());

    idjMap = new int[N + 1][N + 1];
    for (int i = 1; i <= N; i++) {
      Arrays.fill(idjMap[i], INF);
    }

    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int s = Integer.parseInt(st.nextToken());
      int e = Integer.parseInt(st.nextToken());
      int t = Integer.parseInt(st.nextToken());

      idjMap[s][e] = t;
    }

    int[] toX = dijkstra(true);
    int[] fromX = dijkstra(false);

    int ans = 0;
    for (int i = 1; i <= N; i++) {
      if (ans < toX[i] + fromX[i])
        ans = toX[i] + fromX[i];
    }

    System.out.println(ans);
  }
}
