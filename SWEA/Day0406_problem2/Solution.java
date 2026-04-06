package SWEA.Day0406_problem2;

public class Solution {
    public static void main(String[] args) {
        int[] dp = new int[7];
        dp[1] = 2;
        dp[2] = 5;

        for (int i = 3; i <= 6; i++) {
            dp[i] = dp[i - 1] * 2 + dp[i - 2]; // 한칸 전 * 2 와 두칸전 + 빨강인 경우
        }

        System.out.println(dp[6]);
    }
}
