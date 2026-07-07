package Java.G3_1520;

import java.util.*;
import java.io.*;

public class Main {
  static int M, N;
  static int[][] map, dp;
  static int[] dr = { -1, 0, 1, 0 };
  static int[] dc = { 0, 1, 0, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken());
    N = Integer.parseInt(st.nextToken());

    map = new int[M][N];
    dp = new int[M][N];

    // dp 초기화 - 아직 지나지 않은 경로와 이동 불가한 경로 구분을 위해 0이 아닌 -1로 초기화
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

    System.out.println(play(0, 0));
  }

  static int play(int r, int c) {
    if (r == M - 1 && c == N - 1) { // 끝까지 도착하면 1 증가
      return 1;
    }

    if (dp[r][c] != -1) // 이미 계산된 칸이라면 스킵
      return dp[r][c];

    int v = map[r][c];
    dp[r][c] = 0; // 초기화
    for (int dir = 0; dir < 4; dir++) {
      int nr = r + dr[dir];
      int nc = c + dc[dir];
      if (nr >= 0 && nr < M && nc >= 0 && nc < N && map[nr][nc] < v) {
        dp[r][c] += play(nr, nc);
      }
    }

    return dp[r][c];
  }
}
