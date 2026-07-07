import java.util.*;
import java.io.*;

public class Solution {
  static int[][] products;
  static int[][] dp;
  static int[] dp2;
  static int N, K, ans;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      ans = 0;
      products = new int[N][2];
      dp = new int[N][K + 1];
      dp2 = new int[K + 1]; // 무게 K에서 최대 가치

      // 입력 받기
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(br.readLine());
        products[i][0] = Integer.parseInt(st.nextToken()); // v: 부피
        products[i][1] = Integer.parseInt(st.nextToken()); // c: 가치
        // for (int k = 0; k < K + 1; k++) {
        // dp[i][k] = -1;
        // }
      }

      // voidRecur(0, 0, 0);
      // ans = intRecur(0, 0);
      // ans = UpdownDp(0, 0);
      // bottomUpDP();
      bottomUpDp2();
      // ans = dp[N - 1][K];
      ans = dp2[K];
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  // 완전탐색: 앞에서부터 해당 물건을 넣고 안넣고
  // 1. void recur
  static void voidRecur(int idx, int curV, int curC) {
    if (curV > K)
      return;
    if (idx == N) {
      ans = Math.max(ans, curC);
      return;
    }

    // 현재 물건을 넣기
    voidRecur(idx + 1, curV + products[idx][0], curC + products[idx][1]);
    // 현재 물건을 넣지 않기
    voidRecur(idx + 1, curV, curC);
  }

  // 2. int recur - 가치를 반환해야 함
  static int intRecur(int idx, int curV) {
    if (curV > K)
      return Integer.MIN_VALUE; // 패널티
    if (idx == N) { // 다 확인 했으면 가치 0으로 끝내기..
      return 0;
    }

    // 현재 물건 넣기
    int a = products[idx][1] + intRecur(idx + 1, curV + products[idx][0]);
    // 현재 물건 안 넣기
    int b = intRecur(idx + 1, curV);

    return Math.max(a, b); // 넣은 것과 안 넣은 것 중 더 큰 것
  }

  // 3. dp
  static int UpdownDp(int idx, int curV) {
    if (curV > K)
      return -100000000;
    if (idx == N)
      return 0;

    if (dp[idx][curV] != -1) // 중복 방지
      return dp[idx][curV];

    int a = products[idx][1] + UpdownDp(idx + 1, curV + products[idx][0]);
    int b = UpdownDp(idx + 1, curV);

    return dp[idx][curV] = Math.max(a, b);
  }

  static void bottomUpDP() {
    int firstV = products[0][0]; // 부피
    int firstC = products[0][1]; // 가치
    for (int k = 0; k <= K; k++) {
      if (k >= firstV)
        dp[0][k] = firstC;
    }
    for (int i = 1; i < N; i++) {
      for (int j = 0; j <= K; j++) {
        int weight = products[i][0];
        int value = products[i][1];
        if (weight > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          dp[i][j] = Math.max(value + dp[i - 1][j - weight], dp[i - 1][j]);
        }
      }
    }
  }

  static void bottomUpDp2() {
    // dp[i] = 무게가 i일 떄 최대 값
    for (int i = 0; i < N; i++) {
      int weight = products[i][0];
      int value = products[i][1];

      for (int k = K; k >= weight; k--) {
        dp2[k] = Math.max(dp2[k], dp2[k - weight] + value);
      }
    }
  }
}
