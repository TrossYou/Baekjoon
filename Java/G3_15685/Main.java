package Java.G3_15685;

import java.util.*;
import java.io.*;

public class Main {
    static final int MAP_SIZE = 101;
    static int x, y, d, g;
    static int[] dirArr; // 누적 방향
    static boolean[][] isExist; // (y,x)점이 있는지 true/false
    static int px, py, ans; // 현재 끝 점, 정답
    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        isExist = new boolean[MAP_SIZE][MAP_SIZE];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken()); // 시작 x
            y = Integer.parseInt(st.nextToken()); // 시작 y
            d = Integer.parseInt(st.nextToken()); // 방향 우상좌하
            g = Integer.parseInt(st.nextToken()); // 드래곤 커브 횟수

            draw();
        }

        ans = 0;
        for (int i = 1; i < MAP_SIZE; i++) {
            for (int j = 1; j < MAP_SIZE; j++) {
                if (isExist[i - 1][j - 1] && isExist[i - 1][j] && isExist[i][j - 1] && isExist[i][j])
                    ans++; // 사방의 좌표들이 모두 있다면 카운트
            }
        }

        System.out.println(ans);
    }

    static void draw() {
        px = x;
        py = y;
        int dirCnt = (int) Math.pow(2, g); // 총 방향의 개수는 2^g개
        dirArr = new int[dirCnt];
        dirArr[0] = d;
        fillDirArr(dirCnt);

        isExist[py][px] = true;
        fillIsExist(dirCnt); // dirArr을 바탕으로 isExist 채우기

    }

    // g개 만큼 방향 배열 채우기
    static void fillDirArr(int dirCnt) {
        int startIdx = 1;

        for (int gen = 1; gen <= g; gen++) {
            int genNum = (int) Math.pow(2, gen - 1);
            for (int i = 0; i < genNum; i++) {
                dirArr[startIdx + i] = (dirArr[startIdx - i - 1] + 1) % 4;
            }
            startIdx += genNum;
        }
    }

    // 배열에 맞게 fillIsExist 채우기
    static void fillIsExist(int dirCnt) {
        for (int i = 0; i < dirCnt; i++) {
            int curD = dirArr[i];

            // 끝 점 수정
            px += dx[curD];
            py += dy[curD];

            isExist[py][px] = true;
        }
    }
}
