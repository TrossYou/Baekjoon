package Java.G2_1036;

import java.io.*;
import java.util.*;

public class Main_MLE {
    static final int Z_VALUE = 35;
    static int N, K, ansL, letterNum;
    static short[] len; // i번째 수의 길이
    static int[][] arr;
    static int[] info; // -1: 존재하지 않는 수(가지치기), 0: 변경되지 않는 수, 1: 변경되는 수
    static int[] ansArr;
    static PriorityQueue<String> pq;

    static String ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        len = new short[N];
        info = new int[36];
        Arrays.fill(info, -1); // 모두 존재하지 않음으로 표시

        arr = new int[N][55]; // 최대 50개
        ansArr = new int[55];
        letterNum = 0;
        pq = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            int strL = str.length();
            for (int j = 0; j < strL; j++) {
                char v = str.charAt(j);
                if ('0' <= v && v <= '9')
                    arr[i][strL - j - 1] = v - '0';
                else
                    arr[i][strL - j - 1] = v - 'A' + 10;

                len[i]++;
                if (info[arr[i][strL - j - 1]] == -1) {
                    info[arr[i][strL - j - 1]] = 0; // 존재함을 표시
                    letterNum++; // 수의 개수
                }
            }
        }

        K = Integer.parseInt(br.readLine());
        if (K >= letterNum) { // 모두 Z로 바꾸면 됨.
            Arrays.fill(info, 1);
            calc();
            arrToString();
            ans = pq.poll();
        } else {
            // comb(0, 0); // K개의 조합을 찾아서 계산하는 메서드
            // 하나씩 변경하면서 calc();
            for (int i = 0; i < 36; i++) {
                if (info[i] == 0) {
                    info[i] = 1;
                    calc();
                    arrToString();
                }
            }

        }
        // max값을 String으로 변경

        System.out.println(ans.toUpperCase());
    }

    static void arrToString() {
        String str = "";
        boolean start = false;
        for (int i = ansL + 2; i >= 0; i--) {
            int v = ansArr[i];
            if (!start && v == 0)
                continue;
            start = true;
            str += Long.toString(v, 36);
        }
        pq.add(str);
    }
    // static void comb(int start, int num) {
    // // K개의 조합이 완성된 경우 계산
    // if (num == K) {
    // calc();
    // return;
    // }

    // if (start > 35)
    // return; // 시작 인덱스가 마지막인 경우 종료

    // // 만약 start가 없는 수인 경우, 넘어가기 = 가지치기
    // if (info[start] == -1) {
    // comb(start + 1, num);
    // return;
    // }

    // // start 추가
    // info[start] = 1;
    // comb(start + 1, num + 1);

    // // start 제거
    // info[start] = 0;
    // comb(start + 1, num);
    // }

    static void calc() {
        ansL = 0;
        for (int i = 0; i < N; i++) {
            if (ansL < len[i])
                ansL = len[i]; // 가장 긴 문장 길이 = ans의 길이
            for (int j = 0; j < len[i]; j++) {
                // arr[i][j]값이 1인 경우(포함되는 경우) -> Z로 변경
                ansArr[j] += info[arr[i][j]] == 1 ? Z_VALUE : arr[i][j]; // 합 연산
            }
        }

        for (int i = 0; i < ansL + 3; i++) { // 최대 35*50 = 1
            if (ansArr[i] > 35) { // 35를 넘는 경우, 올림
                ansArr[i + 1] += ansArr[i] / 36;
                ansArr[i] %= 36;
            }
        }

        // for (int i = ansL + 2; i >= 0; i--) {
        // if (maxAnsArr[i] > ansArr[i])
        // return;
        // if (maxAnsArr[i] < ansArr[i]) {
        // maxAnsArr = ansArr; // 더 큰 값으로 변경
        // return;
        // }
        // }

    }
}
