package Java.S3_1463;

import java.io.*;
import java.util.*;

public class Main {
  static int N;
  static int[] dp;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    N = Integer.parseInt(br.readLine());

    dp = new int[N + 1]; // i번째일 때의 최소횟수
    Arrays.fill(dp, N + 1); // 최대값으로 초기화

    // bottomUp - Push(내다보기) 방식
    // dp[1] = 0;
    // for (int i = 1; i <= N; i++) {
    // if (3 * i <= N)
    // dp[3 * i] = Math.min(dp[3 * i], dp[i] + 1);
    // if (2 * i <= N)
    // dp[2 * i] = Math.min(dp[2 * i], dp[i] + 1);
    // if (i + 1 <= N)
    // dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);
    // }

    // TopDown
    dp[N] = 0;
    for (int i = N; i > 1; i--) {
      if (i % 3 == 0)
        dp[i / 3] = Math.min(dp[i / 3], dp[i] + 1);
      if (i % 2 == 0)
        dp[i / 2] = Math.min(dp[i / 2], dp[i] + 1);
      dp[i - 1] = Math.min(dp[i - 1], dp[i] + 1);
    }

    System.out.println(dp[1]);
  }
}
