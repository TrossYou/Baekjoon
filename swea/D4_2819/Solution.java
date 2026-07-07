package SWEA.D4_2819;

import java.util.*;
import java.io.*;

public class Solution {
  static int[][] map;
  static int[] dr = { -1, 0, 1, 0 };
  static int[] dc = { 0, 1, 0, -1 };
  static Set<String> set;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    StringTokenizer st;
    for (int tc = 1; tc <= T; tc++) {
      map = new int[4][4];
      set = new HashSet<>();
      // 입력 받기
      for (int i = 0; i < 4; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < 4; j++) {
          map[i][j] = Integer.parseInt(st.nextToken());
        }
      }

      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          dfs(i, j, "" + map[i][j]);
        }
      }

      int ans = set.size();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void dfs(int i, int j, String str) {
    if (str.length() == 7) {
      set.add(str);
      return;
    }

    for (int dir = 0; dir < 4; dir++) {
      int nr = i + dr[dir];
      int nc = j + dc[dir];

      if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
        dfs(nr, nc, str + map[nr][nc]);
      }
    }
  }
}
