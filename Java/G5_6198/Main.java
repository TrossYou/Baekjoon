package Java.G5_6198;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N];
        int[] dp = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // dp채우기
        int sum = 0;
        dp[N - 1] = 0;
        for (int i = N - 2; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                if (arr[i] <= arr[j])
                    break;
                dp[i] += dp[j] > 0 ? dp[j] : 1;
            }
            sum += dp[i];
        }
        System.out.print(sum);
    }
}