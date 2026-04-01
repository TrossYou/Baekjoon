package Java.G3_16438;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        int size = 1;
        for (int i = 0; i < 7; i++) {
            if (size >= N) { // 이미 N을 넘으면 ABBBBBB... 로 출력
                sb.append('A');
                for (int j = 0; j < N - 1; j++)
                    sb.append('B');
            } else {
                int idx = 0;
                boolean isA = true;
                int curL = 0;
                while (idx < N) {
                    for (int j = 0; j < size; j++) {
                        sb.append(isA ? 'A' : 'B');
                        if (++curL >= N)
                            break;
                    }
                    idx += size;
                    isA = !isA;
                }
                size *= 2;
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
