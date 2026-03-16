package SWEA.N_4014;

import java.util.*;
import java.io.*;

public class Solution_tmp {
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
    // 행 검사
    boolean isPossible = true;
    for (int row = 0; row < N; row++) {
      int frontS = 0; // 앞 높이의 시작점
      int frontE = 0; // 앞 높이의 끝나는 점
      int frontH = map[row][0]; // 앞 높이
      int backS = -1; // 뒤 높이의 시작점
      int backE = -1; // 뒤 높이의 끝나는 점
      int backH = -1; // 뒤 높이
      isPossible = true;
      for (int col = 0; col < N; col++) {
        // 만약 frontH와 같다면 -> 아직 앞에서 세는 중
        if (map[row][col] == frontH) {
          if (map[row][col] == frontH) {
            frontE = col;
            continue;
          } else { // 높이 다르면 새롭게
            backS = col;
            backE = col;
            backH = map[row][col];
            continue;
          }
        } else { // 만약 백이 1이 아니라면
          // backH와 현재가 같으면 추가
          if (backH == -1) { // 최초
            backS = col;
            backE = col;
            backH = map[row][col];
            continue;
          }
          if (map[row][col] == backH) {
            backE = col;
            continue;
          } else { // 백을 세고 있는데 끝남 -> 연산 및 초기화
            int diff = frontH - backH;
            if ((diff == 1 && backE - backS + 1 >= X) || (diff == -1 && frontE - frontS + 1 >= X)) {
              // 경사로 설치 가능 -> 초기화
              frontS = backS;
              frontE = backE;
              frontH = backH;
              backS = col;
              backE = col;
              backH = map[row][backS];
              continue;
            }
            isPossible = false; // 높이가 바뀌었지만, 계산 불가로 종료
            break;
          }
        }
      }
      if (isPossible)
        ans++; // 활주로 선택 가능하다면..
    }

  }
}
