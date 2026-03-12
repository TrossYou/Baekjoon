package SWEA.D4_1251;

import java.util.*;
import java.io.*;

public class Solution_prim {
  static double E;
  static int[] xArr, yArr;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      int N = Integer.parseInt(br.readLine());
      long ans = 0;
      xArr = new int[N];
      yArr = new int[N];
      boolean[] isVisited = new boolean[N];
      List<List<long[]>> edges = new ArrayList<>();
      for (int i = 0; i < N; i++)
        edges.add(new ArrayList<>());

      // input 넣기
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        xArr[i] = Integer.parseInt(st.nextToken());

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        yArr[i] = Integer.parseInt(st.nextToken());

      E = Double.parseDouble(br.readLine());

      // edge 추가하기
      for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
          long p = getDist(i, j);
          edges.get(i).add(new long[] { j, p });
          edges.get(j).add(new long[] { i, p });
        }
      }

      // PriorityQueue로 p가 적은 것부터
      int cnt = 0;
      PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
      pq.add(new long[] { 0, 0L });
      long totalDist = 0L;

      while (!pq.isEmpty()) {
        long[] edge = pq.poll();

        int end = (int) edge[0];
        long p = edge[1];

        if (isVisited[end]) // 이미 방문한 섬이면, 스킵
          continue;

        isVisited[end] = true; // 방문 처리
        totalDist += p;
        cnt++;

        if (cnt == N) // 모든 섬 방문
          break;

        for (long[] e : edges.get(end)) {
          if (!isVisited[(int) e[0]]) // 목적지가 추가된 섬이 아니라면 pq에 추가
            pq.add(e);
        }
      }
      ans = Math.round(E * totalDist);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static long getDist(int a, int b) {
    long dx = xArr[a] - xArr[b];
    long dy = yArr[a] - yArr[b];

    return dx * dx + dy * dy;
  }
}
