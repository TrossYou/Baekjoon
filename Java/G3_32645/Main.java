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
    boolean[] isLeaf = new boolean[N + 1];
    Arrays.fill(isLeaf, true);

    int head = 0, tail = 0;

    order[tail++] = ROOT; // 루트노드
    isVisited[ROOT] = true;
    parent[ROOT] = ROOT;

    while (head < tail) {
      int curr = order[head++];
      for (int node : graph.get(curr)) {
        if (!isVisited[node]) { // 방문하지 않은 노드라면, order에 추가+Parent
          isVisited[node] = true;
          order[tail++] = node;
          parent[node] = curr;
          isLeaf[curr] = false;
        }
      }
    }

    int[] minDepth = new int[N + 1];
    Arrays.fill(minDepth, MAX_VALUE);

    for (int i = tail - 1; i >= 0; i--) {
      int curr = order[i];
      if (isLeaf[curr]) {
        minDepth[curr] = 0;
      }

      int p = parent[curr];
      if (p != curr) { // 루트 노드라면,,?? 루트는..본인을 업데이트 하면 안됨
        minDepth[p] = Math.min(minDepth[p], minDepth[curr] + 1);
      }
    }

    // 출력
    for (int i = 1; i <= N; i++) {
      sb.append(minDepth[i] % 2 == 0 ? "uppercut" : "donggggas").append('\n');
    }

    System.out.print(sb);
  }
}
