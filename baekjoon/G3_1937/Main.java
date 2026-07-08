package Java.G3_1937;

import java.io.*;
import java.util.*;

public class Main {
  static int N, ans;
  static int[][] map, dp;
  static int[] dr = { -1, 0, 1, 0 };
  static int[] dc = { 0, 1, 0, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    N = Integer.parseInt(br.readLine());

    map = new int[N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    dp = new int[N][N];

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        ans = Math.max(ans, topdownDP(i, j));
      }
    }

    System.out.println(ans);
  }

  static int topdownDP(int r, int c) {
    // 사방향이 모두 없다면 반환
    // 방향이 있다면, 이전 값 + 1
    if (dp[r][c] != 0)
      return dp[r][c]; // 이미 계산 되어 있다면 그대로 반환

    int v = map[r][c];
    dp[r][c] = 1; // 시작
    for (int dir = 0; dir < 4; dir++) {
      int nr = r + dr[dir];
      int nc = c + dc[dir];

      if (nr >= 0 && nr < N && nc >= 0 && nc < N && map[nr][nc] > v) {
        dp[r][c] = Math.max(dp[r][c], topdownDP(nr, nc) + 1);
      }
    }

    return dp[r][c];
  }
}
