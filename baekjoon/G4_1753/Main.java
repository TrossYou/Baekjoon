package Java.G4_1753;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    final int INF = 1_000_000_000;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    StringTokenizer st = new StringTokenizer(br.readLine());
    int V = Integer.parseInt(st.nextToken());
    int E = Integer.parseInt(st.nextToken());

    List<List<int[]>> edges = new ArrayList<>(V + 1);
    for (int i = 0; i <= V; i++) {
      edges.add(new ArrayList<>());
    }

    int start = Integer.parseInt(br.readLine());

    int[] d = new int[V + 1];
    Arrays.fill(d, INF);
    d[start] = 0;

    for (int i = 0; i < E; i++) {
      st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());
      int w = Integer.parseInt(st.nextToken());

      edges.get(u).add(new int[] { v, w });
    }

    // dijkstra
    PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
    pq.add(new int[] { start, 0 });

    while (!pq.isEmpty()) {
      int[] current = pq.poll();
      int to = current[0];
      int dist = current[1];
      if (dist > d[to])
        continue;
      for (int[] e : edges.get(to)) {
        if (d[e[0]] > d[to] + e[1]) {
          d[e[0]] = d[to] + e[1];
          pq.add(new int[] { e[0], d[e[0]] });
        }
      }
    }

    for (int i = 1; i <= V; i++) {
      if (d[i] == INF)
        sb.append("INF");
      else
        sb.append(d[i]);
      sb.append('\n');
    }

    System.out.print(sb);
  }
}
