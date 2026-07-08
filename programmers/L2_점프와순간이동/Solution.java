public class Solution {
  public int solution(int n) {
    int ans = 0;
    int[] dp = new int[n + 1];

    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
      if (i % 2 == 0) {
        dp[i] = dp[i / 2];
        continue;
      }

      dp[i] = dp[i / 2] + 1;
      int j = 1;
      while (dp[i - j] + j < dp[i]) {
        dp[i] = dp[i - j] + j; // j전 + j한게 더 클 때.
        j++;
      }
    }

    // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
    System.out.println("ans: " + dp[n]);
    ans = dp[n];
    return ans;
  }
}
