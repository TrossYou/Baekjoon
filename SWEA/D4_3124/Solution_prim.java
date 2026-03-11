package SWEA.D4_3124;

import java.util.*;
import java.io.*;

public class Solution {
  static int V, E, cnt;
  static long ans;
  static boolean[] isVisited;
  static List<List<int[]>> lists;
  static PriorityQueue<int[]> pq;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      V = Integer.parseInt(st.nextToken());
      E = Integer.parseInt(st.nextToken());
      ans = 0;
      cnt = 0;

      isVisited = new boolean[V + 1];
      lists = new ArrayList<>();
      for (int i = 0; i <= V; i++) {
        lists.add(new ArrayList<>());
      }

      // 간선 추가
      for (int i = 0; i < E; i++) {
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        lists.get(a).add(new int[] { b, w }); // a에서 시작하는
        lists.get(b).add(new int[] { a, w }); // b에서 시작하는
      }

      pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
      pq.add(new int[] { 1, 0 }); // 가장 처음 1로 감

      while (!pq.isEmpty()) {
        int[] edge = pq.poll();
        int end = edge[0];
        int w = edge[1];

        if (isVisited[end])
          continue; // 상대좌표가 이미 방문한 노드라면

        // 방문하지 않았다면, 추가
        isVisited[end] = true;
        ans += w;
        cnt++;

        if (cnt == V)
          break;

        for (int[] e : lists.get(end)) {
          if (!isVisited[e[0]])
            pq.add(e); // 만약 계산되지 않았다면
        }
      }
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }
}
