package SWEA.D4_7465;

import java.util.*;
import java.io.*;

public class Solution {
  static int[] parent;
  static int[] rank;
  static int cnt;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int n = Integer.parseInt(st.nextToken());
      int m = Integer.parseInt(st.nextToken());

      cnt = n;
      parent = new int[n + 1];
      rank = new int[n + 1];
      for (int i = 1; i <= n; i++) {
        parent[i] = i;
      }

      for (int i = 0; i < m; i++) {
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        union(a, b);
      }
      sb.append('#').append(tc).append(' ').append(cnt).append('\n');
    }
    System.out.print(sb);
  }

  static void union(int a, int b) {
    int rootA = find(a);
    int rootB = find(b);
    if (rootA == rootB)
      return;

    if (rank[rootA] < rank[rootB]) {
      parent[rootA] = rootB;
    } else if (rank[rootA] > rank[rootB]) {
      parent[rootB] = rootA;
    } else {
      parent[rootB] = rootA;
      rank[rootA]++;
    }
    cnt--;
  }

  static int find(int a) {
    if (parent[a] != a) {
      parent[a] = find(parent[a]);
    }
    return parent[a];
  }
}
