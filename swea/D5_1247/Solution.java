package SWEA.D5_1247;

import java.util.*;
import java.io.*;

public class Solution {
  static int N, ans;
  static int[][] coords;
  static boolean[] isVisited;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      ans = 2200; // 최대 거리 200, 이동 횟수: N(10)+1 회 = 최대: 2200
      coords = new int[N + 2][2]; // [i][0]: i번째 x좌표, [i][1]: i번째 y좌표 - 0,1은 각각 회사와 집
      isVisited = new boolean[N + 2];

      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N + 2; i++) {
        for (int j = 0; j < 2; j++) {
          coords[i][j] = Integer.parseInt(st.nextToken());
        }
      }

      dfs(0, 0, 0);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  // cnt: 지금까지 방문한 집 수, a: 출발하는 집 번호, sum: 누적합
  static void dfs(int cnt, int a, int sum) {
    if (cnt == N) { // 모든 집 방문 완료
      sum += getDistance(a, 1); // 마지막 집과 김대리집(회사는 처음에 계산 됨)
      ans = Math.min(ans, sum);
      return;
    }

    // 이미 합이 넘은 경우 가지치기
    if (sum > ans)
      return;

    for (int i = 2; i < 2 + N; i++) {
      if (isVisited[i])
        continue; // 이미 방문한 집이라면 continue

      isVisited[i] = true; // 방문 처리
      dfs(cnt + 1, i, sum + getDistance(a, i));
      isVisited[i] = false; // 방문 회수
    }
  }

  // a와 b의 거리 계산
  static int getDistance(int a, int b) {
    return Math.abs(coords[a][0] - coords[b][0]) + Math.abs(coords[a][1] - coords[b][1]);
  }
}
