package SWEA.N_1952;

import java.util.*;
import java.io.*;

public class Solution {
    static int T, ans;
    static int[] price; // 가겨
    static int[] useArr; // 사용 횟수
    static int[] dp; // 누적 요금 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer sb = new StringBuffer();
        T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            ans = 0;

            // 요금 배열 저장
            price = new int[4];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++)
                price[i] = Integer.parseInt(st.nextToken());

            // 이용 횟수 배열 저장 (3달 계산을 위해 1~15월: 총 16개 항)
            useArr = new int[65];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= 12; i++) {
                useArr[i] = Integer.parseInt(st.nextToken());
            }

            // dp 초기화
            dp = new int[16];
            for (int mon = 12; mon >= 1; mon--) { // 3달 계산 편의를 위해 뒤에서부터 계산
                // 1일 & 1달
                int minPrice = Math.min(useArr[mon] * price[0], price[1]);
                dp[mon] = dp[mon + 1] + minPrice;
                // 3달
                int sum = dp[mon] - dp[mon + 3];
                if (price[2] < sum) { // 만약 세달을합치는게 더 낫다면 3달로 갱신
                    dp[mon] = dp[mon + 3] + price[2];
                }
            }
            // 1년
            ans = dp[1] < price[3] ? dp[1] : price[3];

            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
