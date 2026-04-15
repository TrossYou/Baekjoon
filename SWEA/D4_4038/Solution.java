package SWEA.D4_4038;

import java.io.*;
import java.util.*;

class Solution {
    static String str, target;
    static int s, e, strL, targetL, cnt; // s: 시작 포인터, e: 끝 포인터, L: 길이들 cnt: 등장 횟수
    static char startChar; // target의 시작 문자
    static Queue<Integer> q; // target의 시작 문자와 같은 인덱스 모음

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            str = br.readLine();
            strL = str.length();
            target = br.readLine();
            targetL = target.length();
            startChar = target.charAt(0);
            q = new LinkedList<>();

            for (int i = 0; i < strL; i++) {
                if (str.charAt(i) == startChar) {
                    q.add(i);
                }
            }
            s = q.poll();
            cnt = 0;

            while (s + targetL - 1 < strL) {
                boolean isBreak = false; // 규칙이 깨졌는지 확인
                for (int i = 0; i < targetL; i++) {
                    if (str.charAt(s + i) != target.charAt(i)) { // 규칙 깨짐
                        isBreak = true;
                    }
                }
                if (!isBreak) { // 완성이 되면, 카운트 증가 및 다음 s
                    cnt++;
                }
                s = q.poll(); // 다음 시작
            }

            sb.append('#').append(tc).append(' ').append(cnt).append('\n');
        }
        System.out.print(sb);
    }
}