package Java.G3_17471;

import java.util.*;
import java.io.*;

public class Main {
    static int[] peopleNum; // 인구 수 배열
    static boolean[][] idjMap; // 인접 배열
    static int bitMask; // A그룹에 포함되었는지.. 비트 F/T
    static int ans, N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        peopleNum = new int[N + 1]; // i구역의 인원 수
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            peopleNum[i] = Integer.parseInt(st.nextToken());
        }

        idjMap = new boolean[N + 1][N + 1];
        bitMask = 0;
        ans = -1;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++) {
                idjMap[i][Integer.parseInt(st.nextToken())] = true; // 인접행렬 저장
            }
        }

        // minCity의 인접행렬로 부분집합 만들기
        for (int bitmask = 1; bitmask < (1 << N + 1); bitmask++) {
            checkAndCal(bitmask);
        }

        System.out.println(ans);
    }

    // static void play(int city) {
    // for (int i = 1; i <= N; i++) {
    // if (idjMap[city][i] && !group[i]) { // 인접한 곳
    // group[i] = true; // 해당 마을을 넣고 계산
    // play(i);
    // group[i] = false;
    // }
    // }
    // checkAndCal();
    // return;
    // }

    // 가능한지 확인하고, 계산 및 갱신
    static void checkAndCal(int bitmask) {
        // boolean[] cumArr = new boolean[N + 1];
        int checkBit = 0;

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 1; i <= N; i++) {
            if ((bitmask & (1 << i)) != 0) { // i가 B그룹
                sb.append(i).append(", ");
                for (int j = 1; j <= N; j++) {
                    if (idjMap[i][j]) {
                        checkBit |= (1 << j);
                    }
                }
            }
        }
        sb.append(']').append('\n');

        System.out.print(sb);

        // 비교
        for (int i = 1; i <= N; i++) {
            if ((bitmask & (1 << i)) == 0 && (checkBit & (1 << i)) != 0)
                return; // 둘 B그룹인데, 포함되지 않은 경우
        }

        checkBit = 0;
        for (int i = 1; i <= N; i++) {
            if ((bitmask & (1 << i)) == 0) {
                for (int j = 1; j <= N; j++) {
                    if (idjMap[i][j]) {
                        checkBit |= (1 << j);
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            if ((bitmask & (1 << i)) == 0 && (checkBit & (1 << i)) == 0)
                return;
        }

        int sumA = 0;
        int sumB = 0;
        for (int i = 1; i <= N; i++) {
            if ((bitmask & (1 << i)) != 0) {
                sumA += peopleNum[i];
            } else {
                sumB += peopleNum[i];
            }
        }

        int diff = Math.abs(sumA - sumB);
        if (ans == -1 || ans > diff)
            ans = diff;

        return;
    }
}
