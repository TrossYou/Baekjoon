package SWEA.D3_3282;

import java.io.*;
import java.util.*;

public class Solution {
    static int T, N, K;
    static int[][] products;
    static int[] dp; // 부피가 i일 때 가능한 최대 가치
    static boolean[] isContained;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            dp = new int[K + 1];
            products = new int[N + 1][2];
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                products[i][0] = Integer.parseInt(st.nextToken()); // 무게
                products[i][1] = Integer.parseInt(st.nextToken()); // 가치
            }

            for (int p = 1; p <= N; p++) {
                int v = products[p][0];
                int c = products[p][1];

                for (int i = K; i >= v; i--) {
                    dp[i] = Math.max(dp[i], dp[i - v] + c);
                }
            }
            sb.append('#').append(tc).append(' ').append(dp[K]).append('\n');
        }
        System.out.print(sb);
    }
}
