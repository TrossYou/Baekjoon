package G5_7576;

import java.util.*;
import java.io.*;

public class Main {
  static int M, N, leftTom;
  static int[][] map;
  static int[] dr = new int[] { -1, 0, 1, 0 };
  static int[] dc = new int[] { 0, 1, 0, -1 };
  static Queue<int[]> q;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    M = Integer.parseInt(st.nextToken());
    N = Integer.parseInt(st.nextToken());
    leftTom = 0; // 남은 토마토 수
    q = new LinkedList<>(); // 토마토 좌표 저장

    map = new int[N][M];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < M; j++) {
        int t = Integer.parseInt(st.nextToken());
        if (t == 1) {
          q.add(new int[] { i, j });
        } else if (t == 0)
          leftTom++;
        map[i][j] = t;
      }
    }

    int time = 0;
    while (!q.isEmpty() && leftTom > 0) {
      time++;
      int num = q.size();
      for (int i = 0; i < num; i++) {
        int[] tom = q.poll();

        int r = tom[0];
        int c = tom[1];

        for (int dir = 0; dir < 4; dir++) {
          int nr = r + dr[dir];
          int nc = c + dc[dir];

          if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == 0) {
            map[nr][nc] = 1;
            q.add(new int[] { nr, nc });
            leftTom--;
          }
        }
      }
    }

    if (leftTom == 0)
      System.out.println(time);
    else
      System.out.println(-1);
  }
}
