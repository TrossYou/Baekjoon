package SWEA.D4_3289;

import java.util.*;
import java.io.*;

public class Solution {
  static int[] parent;
  static int[] rank;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int n = Integer.parseInt(st.nextToken());
      int m = Integer.parseInt(st.nextToken());

      parent = new int[n + 1];
      rank = new int[n + 1];
      for (int i = 1; i <= n; i++) {
        parent[i] = i;
      }

      sb.append('#').append(tc).append(' ');

      for (int i = 0; i < m; i++) {
        st = new StringTokenizer(br.readLine());
        int com = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        if (com == 0) {
          union(a, b);
        } else {
          sb.append(find(a) == find(b) ? '1' : '0');
        }
      }
      sb.append('\n');
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
  }

  static int find(int a) {
    if (parent[a] != a) {
      parent[a] = find(parent[a]);
    }
    return parent[a];
  }
}
