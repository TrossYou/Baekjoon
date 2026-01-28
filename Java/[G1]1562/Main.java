import java.io.*;
import java.util.Arrays;

public class Main {
    static final int MOD = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        System.out.println(solve(N));
    }

    static long solve(int N) {
        if (N < 10) return 0;

        // Rolling DP: prev = 길이 L-1, cur = 길이 L
        long[][] prev = new long[10][1 << 10];
        long[][] cur = new long[10][1 << 10];

        // 시작 숫자는 1~9 (0으로 시작 불가)
        for (int d = 1; d <= 9; d++) {
            prev[d][1 << d] = 1;
        }

        for (int len = 2; len <= N; len++) {
            // cur 초기화
            for (int i = 0; i < 10; i++) Arrays.fill(cur[i], 0);

            for (int last = 0; last <= 9; last++) {
                for (int mask = 0; mask < (1 << 10); mask++) {
                    long val = prev[last][mask];
                    if (val == 0) continue;

                    if (last > 0) {
                        int nMask = mask | (1 << (last - 1));
                        cur[last - 1][nMask] = (cur[last - 1][nMask] + val) % MOD;
                    }
                    if (last < 9) {
                        int nMask = mask | (1 << (last + 1));
                        cur[last + 1][nMask] = (cur[last + 1][nMask] + val) % MOD;
                    }
                }
            }

            // swap prev and cur
            long[][] tmp = prev;
            prev = cur;
            cur = tmp;
        }

        long ans = 0;
        int FULL = (1 << 10) - 1;
        for (int last = 0; last <= 9; last++) {
            ans = (ans + prev[last][FULL]) % MOD;
        }
        return ans;
    }
}