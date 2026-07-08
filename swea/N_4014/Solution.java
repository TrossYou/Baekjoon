package SWEA.N_4014;

import java.util.*;
import java.io.*;

public class Solution {
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

      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < N; j++) {
          map[i][j] = Integer.parseInt(st.nextToken());
        }
      }

      play();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    boolean isHorizontal = true;
    boolean[] visited = new boolean[N];
    // 행 검사
    for (int row = 0; row < N; row++) {
      int h = map[row][0];
      boolean isPossible = true;
      visited = new boolean[N];
      // boolean horizontal = true; // 수평
      for (int col = 1; col < N; col++) {
        if (map[row][col] != h) {
          isPossible = check(row, col, isHorizontal, visited);
          if (!isPossible) // 불가능하면 바로 종료
            break;
          h = map[row][col]; // 갱신
        }
      }
      if (isPossible) // 만약 가능하다며
        ans++;
    }
    // 열 검사
    isHorizontal = false;
    for (int col = 0; col < N; col++) {
      int h = map[0][col];
      boolean isPossible = true;
      visited = new boolean[N];
      for (int row = 1; row < N; row++) {
        if (map[row][col] != h) {
          isPossible = check(row, col, isHorizontal, visited);
          if (!isPossible) // 불가능하면 바로 종료
            break;
          h = map[row][col]; // 갱신
        }
      }
      if (isPossible) // 만약 가능하다며
        ans++;
    }
  }

  // idx에서 가능한 지 연산
  static boolean check(int row, int col, boolean isHorizontal, boolean[] visited) {
    int frontH = isHorizontal ? map[row][col - 1] : map[row - 1][col];
    int backH = map[row][col];
    int diff = frontH - backH;

    // 열 연산
    if (isHorizontal) {
      if (diff > 1 || diff < -1)
        return false;
      else if (diff == 1) { // 앞에가 더 크면
        for (int i = 0; i < X; i++) {
          if (col + i >= N || visited[col + i] || map[row][col + i] != backH)
            return false; // X개가 유지 안되기 떄문에 바로 반환
          visited[col + i] = true;
        }
      } else if (diff == -1) { // 뒤에가 더 크면
        for (int i = 1; i <= X; i++) {
          if (col - i < 0 || visited[col - i] || map[row][col - i] != frontH)
            return false;
          visited[col - i] = true;
        }
      }
    } else { // 행 연산
      if (diff > 1 || diff < -1)
        return false;
      else if (diff == 1) { // 앞에가 더 크면
        for (int i = 0; i < X; i++) {
          if (row + i >= N || visited[row + i] || map[row + i][col] != backH)
            return false; // X개가 유지 안되기 떄문에 바로 반환
          visited[row + i] = true;
        }
      } else if (diff == -1) { // 뒤에가 더 크면
        for (int i = 1; i <= X; i++) {
          if (row - i < 0 || visited[row - i] || map[row - i][col] != frontH)
            return false;
          visited[row - i] = true;
        }
      }
    }
    return true;
  }
}
