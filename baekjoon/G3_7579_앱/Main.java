package Java.G3_7579;

import java.io.*;
import java.util.*;

public class Main {
  static int N, M, ans;
  static int[] arrM, arrC;
  // static int[][] topdown2D;
  static int[] dp;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    arrM = new int[N];
    arrC = new int[N];
    ans = 10001;

    // dp = new int[M + 1];
    // Arrays.fill(dp, 10001);

    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++)
      arrM[i] = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++)
      arrC[i] = Integer.parseInt(st.nextToken());

    int MAX_COST = 10000; // N: 100 x C: 100
    dp = new int[MAX_COST + 1];

    for (int i = 0; i < N; i++) {
      int mem = arrM[i];
      int cost = arrC[i];

      for (int k = MAX_COST; k >= cost; k--) {
        dp[k] = Math.max(dp[k], mem + dp[k - cost]); // 현재와 넣는 것 충 최대를 저장
      }
    }

    for (int i = 0; i <= MAX_COST; i++) {
      if (dp[i] >= M) {
        System.out.println(i);
        break;
      }
    }

    // topdown2D = new int[N][10000001];
    // for (int i = 0; i < N; i++)
    // Arrays.fill(topdown2D[i], 10001);

    // ans = topDownDP(0, M);
    // recur(0, 0, 0);

    // 브루트포스 - 부분집합
    // 모든 부분집합을 만들어서, 만약 arrM을 넘으면 ans를 갱신
    // -> 집합에서 더하며 가다가.. arrM가 M을 넘으면 ans를 갱신 / arrC가 ans를 넘으면 백트래킹

    // ans = intRecur(0, M);
    // System.out.println(ans);
  }

  // static void recur(int idx, int curM, int curC) {
  // if (curC > ans || idx == N - 1) // 끝에 도달했거나
  // return; // 이미 ans보다 넘는다면, 프루닝
  // if (curM >= M) { // 가장 마지막을 호출한다면 or 필요한 메모리를 넘는다면(끝까지 안가도 ㄱㅊ)
  // ans = Math.min(ans, curC);
  // return;
  // }

  // // 현재 프로그램 넣기
  // recur(idx + 1, curM + arrM[idx], curC + arrC[idx]);
  // // 현재 프로그램 안 넣기
  // recur(idx + 1, curM, curC);
  // }

  // static int intRecur(int idx, int leftM) {
  // if (leftM <= 0) {
  // return 0; // 도달하면 종료
  // }

  // if (idx == N)
  // return 100000000; // 프루닝

  // // 현재 인덱스 넣기
  // int a = arrC[idx] + intRecur(idx + 1, leftM - arrM[idx]);
  // // 안 넣기
  // int b = intRecur(idx + 1, leftM);

  // return Math.min(a, b);
  // }

  // static int topDownDP(int idx, int leftM) {
  // if (leftM <= 0) {
  // return 0;
  // }

  // if (idx == N)
  // return 100000000;

  // if (topdown2D[idx][leftM] != 10001)
  // return topdown2D[idx][leftM];

  // int a = arrC[idx] + topDownDP(idx + 1, leftM - arrM[idx]);
  // int b = topDownDP(idx + 1, leftM);

  // return topdown2D[idx][leftM] = Math.min(a, b);
  // }

}
