package Java.G3_32645;

import java.io.*;
import java.util.*;

public class Main {
  static final int ROOT = 1;
  static final int MAX_VALUE = 100010;
  static List<List<Integer>> graph;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int N = Integer.parseInt(br.readLine());

    graph = new ArrayList<>();
    for (int i = 0; i <= N; i++) {
      graph.add(new ArrayList<>());
    }

    for (int i = 0; i < N - 1; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());

      graph.get(u).add(v);
      graph.get(v).add(u);
    }

    boolean[] isVisited = new boolean[N + 1];
    int[] order = new int[N]; // bfs순서 -> 추후 깊이 탐색에서도 사용
    int[] parent = new int[N + 1];
    int[] depth = new int[N + 1]; // i노드의 깊이
    boolean[] isLeaf = new boolean[N + 1];

    Arrays.fill(isLeaf, true);

    int head = 0, tail = 0;

    order[tail++] = ROOT; // 루트노드
    isVisited[ROOT] = true;
    parent[ROOT] = ROOT;
    depth[ROOT] = 0;

    while (head < tail) {
      int curr = order[head++];
      for (int node : graph.get(curr)) {
        if (!isVisited[node]) { // 방문하지 않은 노드라면, order에 추가+Parent
          depth[node] = depth[curr] + 1;
          isVisited[node] = true;
          order[tail++] = node;
          parent[node] = curr;
          isLeaf[curr] = false;
        }
      }
    }

    boolean[] dp = new boolean[N + 1]; // 동우 이김:true

    for (int i = tail - 1; i >= 0; i--) {
      int curr = order[i];
      if (isLeaf[curr]) {
        continue;
      }

      // 자식을 돌며, dp가 true인게 있으면 true 아니면 false
      for (int next : graph.get(curr)) {
        if (next != parent[curr] && !dp[next]) {
          dp[curr] = true;
          break;
        }
      }
    }

    // 출력
    for (int i = 1; i <= N; i++) {
      sb.append(dp[i] ? "donggggas" : "uppercut").append('\n');
    }

    System.out.print(sb);
  }
}
