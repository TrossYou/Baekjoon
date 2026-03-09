package SWEA.D4_1868;

import java.io.*;

public class Solution {
  static int[][] map; // 지뢰: -1, 계산된 칸: 1, 계산안된 칸: 0
  static int N, ans;
  static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
  static int[] dc = { 0, 1, 1, 1, 0, -1, -1, -1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine());
      map = new int[N][N];
      ans = 0;

      String s;
      for (int i = 0; i < N; i++) {
        s = br.readLine();
        for (int j = 0; j < N; j++) {
          map[i][j] = s.charAt(j) == '*' ? -1 : 0; // 폭탄이면 -1, 아니면 0
        }
      }

      play();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    // 주변에 지뢰가 없는 좌표를 모두 저장
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (map[i][j] == 0) { // 방문하지 않은 칸 이면
          boolean bomb = isBomb(i, j);
          if (!bomb) { // 8방이 다 비어 있으면, fill(dfs)돌기
            ans++;
            fill(i, j);
          }
        }
      }
    }
    // 여전히 충족되지 않은 칸 수
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (map[i][j] == 0)
          ans++;
      }
    }
  }

  static void fill(int i, int j) {
    map[i][j] = 1; // 방문 표시

    for (int dir = 0; dir < 8; dir++) {
      int nr = i + dr[dir];
      int nc = j + dc[dir];
      if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
        if (map[nr][nc] == 0) {
          boolean bomb = isBomb(nr, nc);
          if (bomb) {
            map[nr][nc] = 1; // 방문 체크
          } else {
            fill(nr, nc);
          }
        }
      }
    }
  }

  // 8방 체크하는 함수
  static boolean isBomb(int i, int j) {
    for (int dir = 0; dir < 8; dir++) {
      int nr = i + dr[dir];
      int nc = j + dc[dir];

      if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
        if (map[nr][nc] == -1)
          return true; // 폭탄이 있으면 무조건 true
      }
    }
    return false; // 폭탄 없으면 false -> 주변으로 퍼지기
  }
}
