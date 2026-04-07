package SWEA.N_2112;

import java.io.*;
import java.util.*;

public class Solution {
  static int T, D, W, K, ans;
  static boolean[][] originMap; // A: false, B: true
  static boolean[][] copyMap; // A: false, B: true

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      D = Integer.parseInt(st.nextToken());
      W = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      ans = D; // D의 최댓값 13

      // 입력 받기
      originMap = new boolean[W][D]; // 가로 세로 바꾸기
      copyMap = new boolean[W][D];

      for (int i = 0; i < D; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < W; j++) {
          int v = Integer.parseInt(st.nextToken());
          originMap[j][i] = v == 0 ? false : true; // boolean으로 저장
          copyMap[j][i] = originMap[j][i]; // 복제
        }
      }

      play(0, 0);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static boolean isPossible() {
    for (int i = 0; i < W; i++) {
      int cntA = 0;
      int cntB = 0;
      for (int j = 0; j < D; j++) {
        if (!copyMap[i][j]) { // A인 경우
          cntB = 0;
          cntA++;
        } else {
          cntA = 0;
          cntB++;
        }
        if (cntA >= K || cntB >= K)
          break; // 다음 열
      }
      if (cntA < K && cntB < K)
        return false;
    }
    return true;
  }

  static void play(int startD, int cnt) { // row에서 유지, A, B 세가지 해보기. depth 기록
    if (isPossible()) {
      ans = Math.min(ans, cnt);
      return;
    }

    if (startD >= D || cnt >= ans)
      return; // 이미 넘은 경우 반환

    // A로 설정
    for (int i = 0; i < W; i++)
      copyMap[i][startD] = false;
    play(startD + 1, cnt + 1);

    // B로 설정
    for (int i = 0; i < W; i++)
      copyMap[i][startD] = true;
    play(startD + 1, cnt + 1);

    // 원복
    for (int i = 0; i < W; i++)
      copyMap[i][startD] = originMap[i][startD];
    play(startD + 1, cnt);
  }
}
