package Java.G5_1074;

import java.io.*;
import java.util.*;

public class Main {
    static final int BOX_SIZE = 2;
    static int N, r, c, ans;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        ans = 0;

        int startR = 0, startC = 0; // 해당 박스에서 기준 인덱스

        int mul = (int) Math.pow(4, N - 1); // 섹션에 곱해야 하는 수
        int div = (int) Math.pow(2, N - 1); // 섹션을 구하기 위해 나누는 수
        for (int i = N - 1; i >= 0; i--) {
            int newR = r - startR;
            int newC = c - startC;

            int boxR = newR / div;
            int boxC = newC / div;

            int boxIdx = 0;
            if (boxR == 0) {
                if (boxC == 0)
                    boxIdx = 0;
                else
                    boxIdx = 1;
            } else {
                if (boxC == 0)
                    boxIdx = 2;
                else
                    boxIdx = 3;
            }

            ans += boxIdx * mul;

            // 다음 갱신
            startR += div * boxR;
            startC += div * boxC;
            div /= 2;
            mul /= 4;
        }

        System.out.println(ans);
    }
}
