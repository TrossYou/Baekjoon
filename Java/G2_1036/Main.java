package Java.G2_1036;

import java.io.*;
import java.util.*;

public class Main {
    static final int Z_VALUE = 35;
    static int N, K, letterNum, maxL;
    static int[] len; // i번쨰 문장 길이
    static int[][] arr;
    static boolean[] isContian = new boolean[36]; // 수 i가 포함되어 있는지 = 가지치기 -> 36
    static boolean[] maxGain = new boolean[36]; // 이익이 최대가 되는 조합
    static int[][] gain = new int[36][60]; // i를 Z로 바꿨을 떄 j자리에서 얻는 이득 합
    static PriorityQueue<Long[]> pq; // [0]: 바꾼 수 [1]: 이득값

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        maxL = 0;
        len = new int[N];
        arr = new int[N][55];
        pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            int strL = str.length();
            if (strL > maxL)
                maxL = strL;
            for (int j = 0; j < strL; j++) {
                char v = str.charAt(j);
                if ('0' <= v && v <= '9')
                    arr[i][strL - j - 1] = v - '0';
                else
                    arr[i][strL - j - 1] = v - 'A' + 10;

                len[i]++;
                if (!isContian[arr[i][strL - j - 1]]) {
                    isContian[arr[i][strL - j - 1]] = true;
                    letterNum++;
                }
            }
        }

        K = Integer.parseInt(br.readLine());
        if (K >= letterNum) { // K가 종류 수 보다 크거나 같으면 모두 Z로 변경 후 SUM
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < len[i]; j++) {
                    arr[i][j] = Z_VALUE;
                }
            }
            System.out.println(getAns());
        } else {
            // 각 문자별 이득 계산
            for (int i = 0; i < 36; i++) {
                if (isContian[i]) {
                    countGain(i);
                }
            }

            // 이득 큰 순서대로 문자 정렬
            List<Integer> candidates = new ArrayList<>();
            for (int i = 0; i < 36; i++) {
                if (isContian[i]) {
                    candidates.add(i);
                }
            }

            candidates.sort((a, b) -> compareGain(b, a));

            // 상위 K개 선택
            for (int i = 0; i < K; i++) {
                maxGain[candidates.get(i)] = true;
            }

            // 선택된 문자들을 Z로 변경
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < len[i]; j++) {
                    if (maxGain[arr[i][j]]) {
                        arr[i][j] = Z_VALUE;
                    }
                }
            }

            System.out.println(getAns());
        }
    }

    static int compareGain(int a, int b) {
        for (int j = 59; j >= 0; j--) {
            if (gain[a][j] != gain[b][j]) {
                return gain[a][j] - gain[b][j];
            }
        }
        return 0;
    }

    static void countGain(int letter) {
        int diff = Z_VALUE - letter;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < len[i]; j++) {
                if (arr[i][j] == letter) {
                    gain[letter][j] += diff;
                }
            }
        }

        // 36진수 자리 올림 처리
        for (int j = 0; j < 59; j++) {
            gain[letter][j + 1] += gain[letter][j] / 36;
            gain[letter][j] %= 36;
        }
    }

    static String getAns() {
        int[] ansArr = new int[55]; // 최대 50 + 3
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < len[i]; j++) {
                ansArr[j] += arr[i][j];
            }
        }

        for (int i = 0; i < maxL + 3; i++) { // 최대 35 * 50 = 1
            if (ansArr[i] > 35) { // 35를 넘는 경우, 올림
                ansArr[i + 1] += ansArr[i] / 36;
                ansArr[i] %= 36;
            }
        }

        String ans = "";
        boolean start = false;
        for (int i = ansArr.length - 1; i >= 0; i--) {
            int v = ansArr[i];
            if (!start && v == 0)
                continue;
            start = true;
            ans += Long.toString(v, 36);
        }
        return ans.toUpperCase();
    }
}
