import java.io.*;
import java.util.*;

public class Solution2 {
  private static int N, ans, startIdx;
  private static int[][] map;
  private static boolean[][] visited;

  // 순서대로 상, 우, 하, 좌
  private final static int[] dr = new int[] { -1, 0, 1, 0 };
  private final static int[] dc = new int[] { 0, 1, 0, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      map = new int[N][N];
      visited = new boolean[N][N];
      ans = 0;
      startIdx = -1;

      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < N; j++) {
          map[i][j] = Integer.parseInt(st.nextToken());
        }
      }

      play();
      sb.append('#').append(tc).append(' ').append(startIdx).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  private static void play() {
    // 완전 탐색
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int cnt = getCnt(i, j);
        if (cnt > ans) {
          ans = cnt;
          startIdx = map[i][j];
        }
      }
    }
  }

  private static int getCnt(int r, int c) {
    if (visited[r][c])
      return 0;
    visited[r][c] = true;
    for (int k = 0; k < 4; k++) {
      int nr = r + dr[k];
      int nc = c + dc[k];
      if (nr >= 0 && nr < N && nc >= 0 && nc < N && map[nr][nc] == map[r][c] + 1) {
        return getCnt(nr, nc) + 1;
      }
    }

    return 1;
  }
}
