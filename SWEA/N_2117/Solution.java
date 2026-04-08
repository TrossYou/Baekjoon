package SWEA.N_2117;

import java.io.*;
import java.util.*;

public class Solution {
    static int T, N, M, ans;
    static List<int[]> houses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            ans = 1; // K가 1일때를 기본값
            houses = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    if (st.nextToken().equals("1"))
                        houses.add(new int[] { i, j }); // 집 정보 저장
                }
            }

            int maxK = 2 * N + 1;
            for (int k = 2; k <= maxK; k++) {
                int charge = k * k + (k - 1) * (k - 1);

                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < N; c++) {
                        int houseNum = 0;
                        for (int[] h : houses) {
                            if (Math.abs(r - h[0]) + Math.abs(c - h[1]) < k)
                                houseNum++;
                        }
                        if (0 <= houseNum * M - charge) {
                            ans = Math.max(ans, houseNum);
                        }
                    }
                }

            }

            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
