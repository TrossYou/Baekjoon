package Java.G3_1520;

import java.io.*;
import java.util.*;

public class Main_ai {
  static int M, N, ans;
  static int[][] map;
  static int[][] dp;
  static boolean[][] isVisited;

  // row, column 상하좌우
  static int[] dr = { -1, 0, 1, 0 };
  static int[] dc = { 0, 1, 0, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken()); // 세로 길이
    N = Integer.parseInt(st.nextToken()); // 가로 길이
    ans = 0;
    map = new int[M][N];
    dp = new int[M][N];
    for (int i = 0; i < M; i++) {
      Arrays.fill(dp[i], -1);
    }

    // 입력 받기
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    // voidRecur(0, 0);
    ans = dfs(0, 0);
    System.out.println(ans);
  }

  // voidRecur - 시간초과 O(4^(500*500))
  static void voidRecur(int r, int c) {
    if (r == M - 1 && c == N - 1) { // 목적지에 도달했다면, 경로 추가 및 종료
      ans++;
      return;
    }

    int v = map[r][c];
    for (int dir = 0; dir < 4; dir++) {
      int nr = r + dr[dir];
      int nc = c + dc[dir];
      if (nr >= 0 && nr < M && nc >= 0 && nc < N && map[nr][nc] < v) {
        voidRecur(nr, nc);
      }
    }
  }

  static int dfs(int r, int c) {
    if (r == M - 1 && c == N - 1)
      return 1; // 끝까지 도달하면 1반환

    if (dp[r][c] != -1) // 이미 계산된 경로라면, 바로 반환
      return dp[r][c];

    dp[r][c] = 0; // 시자
    int v = map[r][c];
    for (int dir = 0; dir < 4; dir++) {
      int nr = r + dr[dir];
      int nc = c + dc[dir];
      if (nr >= 0 && nr < M && nc >= 0 && nc < N && map[nr][nc] < v) {
        dp[r][c] += dfs(nr, nc);
      }
    }

    return dp[r][c];
  }
}
