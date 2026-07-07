package SWEA.N_4014;

import java.util.*;
import java.io.*;

public class Solution_ai {
  static int N, X, ans;
  static int[][] map;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      X = Integer.parseInt(st.nextToken());
      ans = 0;
      map = new int[N][N];

      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      play();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    int[] line = new int[N];

    // 행 검사
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        line[col] = map[row][col];
      }
      if (canBuild(line))
        ans++;
    }

    // 열 검사
    for (int col = 0; col < N; col++) {
      for (int row = 0; row < N; row++) {
        line[row] = map[row][col];
      }
      if (canBuild(line))
        ans++;
    }
  }

  // 한 줄(행 또는 열)에서 활주로 설치 가능 여부
  static boolean canBuild(int[] line) {
    int flat = 1; // 현재 높이와 같은 연속 칸 수

    for (int i = 1; i < N; i++) {
      int diff = line[i] - line[i - 1];

      if (diff == 0) {
        flat++;
      } else if (diff == 1) { // 오르막
        if (flat < X)
          return false;
        flat = 1;
      } else if (diff == -1) { // 내리막
        int nextH = line[i];
        for (int k = 0; k < X; k++) {
          int idx = i + k;
          if (idx >= N || line[idx] != nextH)
            return false;
        }
        i += X - 1; // 경사로 구간 건너뛰기(겹침 방지)
        flat = 0;
      } else {
        return false; // 높이 차 2 이상
      }
    }

    return true;
  }
}
