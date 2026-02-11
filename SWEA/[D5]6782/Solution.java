import java.io.*;
import java.util.*;

public class Solution {
    static int count; // 최소값

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            count = 0;
            long N = Long.parseLong(br.readLine());
            while (N > 2) {
                long base = (long) Math.sqrt(N); // 루트N의 근사치 -> long형변환 - 소수점 버림

                // 거듭제곱이면
                if (base * base == N) {
                    N = base;
                }
                // 거듭제곱이 아니면
                else {
                    count += (base + 1) * (base + 1) - N; // (base+1)^2으로 변환후,
                    N = base + 1;
                }
                count++; // 루트 계산
            }

            sb.append('#').append(tc).append(' ').append(count).append('\n');
        }
        System.out.print(sb);
    }
}
