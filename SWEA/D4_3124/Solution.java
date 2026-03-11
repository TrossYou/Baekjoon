package SWEA.D4_3124;

import java.io.*;
import java.util.*;

public class Solution {
  static class Edge implements Comparable<Edge> {
    int A, B, w;

    Edge(int A, int B, int w) {
      this.A = A;
      this.B = B;
      this.w = w;
    }

    @Override
    public int compareTo(Edge e) {
      return Integer.compare(this.w, e.w); // 비교연산
    }
  }

  static int V, E;
  static long ans;
  static Edge[] edges;
  static int[] parents;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      V = Integer.parseInt(st.nextToken());
      E = Integer.parseInt(st.nextToken());
      ans = 0;
      edges = new Edge[E];
      parents = new int[V + 1]; // i번째의 부모
      for (int i = 1; i <= V; i++)
        parents[i] = -1; // 모든 노드가 루트임 -> 크기가 1인 노드

      for (int i = 0; i < E; i++) {
        st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        edges[i] = new Edge(A, B, w);
      }

      Arrays.sort(edges); // w에 추가

      for (Edge edge : edges) {
        int A = edge.A;
        int B = edge.B;
        int w = edge.w;

        if (union(A, B)) { // 합병 됨
          ans += w;
        }
      }
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  // 부모 노드를 찾아주는 함수 - 경로 압축
  public static int find(int v) {
    if (parents[v] < 0)
      return v; // 음수라면, 본인이 루트
    return parents[v] = find(parents[v]);
  }

  // 그룹을 합쳐주는 함수
  public static boolean union(int a, int b) {
    int aRoot = find(a);
    int bRoot = find(b);

    if (aRoot == bRoot) // 둘이 루트가 같으면 끝
      return false;

    if (parents[aRoot] <= parents[bRoot]) { // a그룹 크기가 더 큰 경우 a그룹으로 합병
      parents[aRoot] += parents[bRoot];
      parents[bRoot] = aRoot;
    } else {
      parents[bRoot] += parents[aRoot];
      parents[aRoot] = bRoot;
    }

    return true;
  }
}
