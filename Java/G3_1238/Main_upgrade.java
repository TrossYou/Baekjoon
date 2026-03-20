package Java.G3_1238;

import java.util.*;
import java.io.*;

public class Main_upgrade {
  static int N, M, X;
  static final int INF = 1_000_000;
  static List<List<int[]>> graph, reverseGraph;
  static int[] toX, fromX;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    X = Integer.parseInt(st.nextToken());

    graph = new ArrayList<>();
    reverseGraph = new ArrayList<>();
    for (int i = 0; i < N + 1; i++) {
      graph.add(new ArrayList<>());
      reverseGraph.add(new ArrayList<>());
    }

    // 길 입력 받기 (graph: 도착지,값 / reverseGraph: 출발지,값)
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int s = Integer.parseInt(st.nextToken());
      int e = Integer.parseInt(st.nextToken());
      int t = Integer.parseInt(st.nextToken());

      graph.get(s).add(new int[] { e, t });
      reverseGraph.get(e).add(new int[] { s, t });
    }

    dijkstraTwice(); // 왕복(toX, fromX)를 동시에 구현

    int ans = 0;
    for (int i = 1; i <= N; i++) {
      if (toX[i] == INF || fromX[i] == INF)
        continue;
      ans = Math.max(ans, toX[i] + fromX[i]);
    }
  }

  static void dijkstraTwice() {
    PriorityQueue<int[]> toPq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
    PriorityQueue<int[]> fromPq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

    toX = new int[N + 1];
    fromX = new int[N + 1];

    for (int i = 1; i <= N; i++) {
      toX[i] = INF;
      fromX[i] = INF;
    }

    toX[X] = 0;
    fromX[X] = 0;

    // 길 기록
    for (int i = 0; i < M; i++) {
      int[] to = reverseGraph.get(X).get(i);
      toX[to[0]] = to[1];
      toPq.add(to);

      int[] from = graph.get(X).get(i);
      fromX[from[0]] = from[1];
      fromPq.add(from);
    }

    // toX먼저 계산
    while (!toPq.isEmpty()) {
      int[] to = toPq.poll();
      int idx = to[0];
      int time = to[1];

      if (time > toX[idx])
        continue;

    }
  }

}
