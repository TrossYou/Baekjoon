package Java.G3_32645;

import java.util.*;
import java.io.*;

public class Main_runtimeE {
  static class Node {
    int parent;
    int depth;
    boolean isLeaf;

    public Node(int parent, boolean isLeaf) {
      this.parent = parent;
      this.isLeaf = isLeaf;
      depth = 0;
    }
  }

  static final int ROOT = 1;
  static boolean[] isVisited;
  static Node[] nodes;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int N = Integer.parseInt(br.readLine());
    isVisited = new boolean[N + 1];
    nodes = new Node[N + 1];

    isVisited[ROOT] = true;
    nodes[ROOT] = new Node(ROOT, true);

    // 입력 받기
    for (int i = 0; i < N - 1; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());

      int parent = isVisited[u] ? u : v;
      int child = isVisited[u] ? v : u;

      Node parentNode = nodes[parent];
      parentNode.isLeaf = false; // 리프노드가 아니라는 것 표시

      Node childNode = new Node(parent, true);
      nodes[child] = childNode;
      isVisited[child] = true;
    }

    // depth 계산
    for (int i = 1; i <= N; i++) {
      if (nodes[i].isLeaf) {
        dfs(i);
      }
    }

    // 결과 출력
    for (int i = 1; i <= N; i++) {
      sb.append(nodes[i].depth % 2 == 0 ? "uppercut" : "dongggas").append('\n');
    }

    System.out.print(sb);
  }

  static void dfs(int curr) {
    int parent = nodes[curr].parent;
    Node parentNode = nodes[parent];
    parentNode.depth = parentNode.depth == 0 ? nodes[curr].depth + 1
        : Math.min(parentNode.depth, nodes[curr].depth + 1);

    if (parent != ROOT)
      dfs(parent);
  }
}
