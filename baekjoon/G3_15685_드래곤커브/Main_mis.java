package Java.G3_15685;

import java.util.*;
import java.io.*;

public class Main_mis {
    static final int MAP_SIZE = 100;
    static int x, y, d, g;
    static int[] dirArr; // 누적 방향
    static int[][] bitMap; // [x][y]의 벽 정보 상(0)우(1)하(2)좌(3)
    static int px, py, ans; // 현재 끝 점, 정답
    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        bitMap = new int[MAP_SIZE][MAP_SIZE];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken()); // 시작 x
            y = Integer.parseInt(st.nextToken()); // 시작 y
            d = Integer.parseInt(st.nextToken()); // 방향 우상좌하
            g = Integer.parseInt(st.nextToken()); // 드래곤 커브 횟수

            draw();
        }

        ans = 0;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (bitMap[i][j] == 15) { // 모든 칸이 있으면 값이 15(1111)
                    ans++;
                }
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

        fillBitMap(dirCnt); // dirArr을 바탕으로 bitMap 채우기

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

    // 배열에 맞게 bitMap 채우기
    static void fillBitMap(int dirCnt) {
        for (int i = 0; i < dirCnt; i++) {
            int curD = dirArr[i];
            if (curD == 0) { // 우
                bitMap[py][px + 1] |= (1 << 2); // 상단칸의 하단벽
                bitMap[py + 1][px + 1] |= (1 << 0); // 하단칸의 상단벽
            } else if (curD == 1) { // 상
                bitMap[py][px] |= (1 << 1); // 좌측칸의우측벽
                bitMap[py][px + 1] |= (1 << 3); // 우측칸의 좌측벽
            } else if (curD == 2) { // 좌
                bitMap[py][px] |= (1 << 2); // 상단칸의 하단벽
                bitMap[py + 1][px] |= (1 << 0); // 하단칸의 상단벽
            } else { // 하
                bitMap[py + 1][px] |= (1 << 1); // 좌측칸의우측벽
                bitMap[py + 1][px + 1] |= (1 << 3); // 우측칸의 좌측벽
            }

            // 끝 점 수정
            px += dx[curD];
            py += dy[curD];
        }
    }
}
