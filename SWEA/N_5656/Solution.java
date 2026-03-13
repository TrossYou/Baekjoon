package SWEA.N_5656;

import java.io.*;
import java.util.*;

public class Solution {
  static int N, W, H, ans;
  static int[][][] map;
  static int[] dr = { -1, 0, 1, 0 };
  static int[] dc = { 0, 1, 0, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      W = Integer.parseInt(st.nextToken());
      H = Integer.parseInt(st.nextToken());
      ans = W * H + 1;

      map = new int[N + 1][H][W]; // map[i][][]: 공을 i번 쐈을 때 사용될 맵

      for (int i = 0; i < H; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < W; j++) {
          map[0][i][j] = Integer.parseInt(st.nextToken()); // 초기 맵 세팅
        }
      }

      // 시뮬레이션
      dfs(1);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void dfs(int depth) { // depth: 현재 쏠 공의 번호(1부터 시작)
    if (depth == N + 1) {
      // 전체 전체 돌며 벽돌 수 세기
      int walls = 0;
      for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
          if (map[N][i][j] != 0)
            walls++;
        }
      }
      ans = Math.min(ans, walls); // 공을 N번 쏘면, 남은 벽 수 갱신
      return;
    }

    for (int c = 0; c < W; c++) { // 다음 공을 쏠 곳
      play(depth, c); // depth-1번째 맵에서 c열에 쏴서 map[depth] 생성
      dfs(depth + 1);
    }
  }

  // depth회에 c번 열에 쏘기
  static void play(int depth, int c) {
    for (int i = 0; i < H; i++) {
      for (int j = 0; j < W; j++) {
        // 맵을 복제
        map[depth][i][j] = map[depth - 1][i][j];
      }
    }

    for (int r = 0; r < H; r++) {
      if (map[depth][r][c] == 0)
        continue; // 벽이 없으면 다음 행

      bomb(depth, r, c);
      break;
    }

    // 벽 떨어뜨리기
    for (int col = 0; col < W; col++) {
      int floor = H - 1;
      for (int row = H - 1; row >= 0; row--) {
        if (map[depth][row][col] != 0) { // 0이면,,,위로 올라가서 0이 아닌 것을 가지고 대체
          int v = map[depth][row][col];
          map[depth][row][col] = 0;
          map[depth][floor][col] = v;
          floor--;
        }
      }
    }
  }

  static void bomb(int depth, int r, int c) {
    int size = map[depth][r][c];
    map[depth][r][c] = 0; // 터진 칸은 0으로
    for (int i = 1; i < size; i++) {
      for (int dir = 0; dir < 4; dir++) {
        int nr = r + i * dr[dir];
        int nc = c + i * dc[dir];

        if (nr < 0 || nr >= H || nc < 0 || nc >= W)
          continue;
        if (map[depth][nr][nc] <= 1)
          map[depth][nr][nc] = 0; // 터치기
        else
          bomb(depth, nr, nc);
      }
    }

  }
}
