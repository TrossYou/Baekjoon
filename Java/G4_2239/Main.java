package Java.G4_2239;

import java.io.*;

public class Main {
    static final int SIZE = 9;
    static final int BOX_SIZE = 3;
    static int[][] map;
    static int[] num; // i번째 숫자가 현재 채워진 개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new int[SIZE][SIZE];
        num = new int[SIZE + 1];
        for (int i = 0; i < SIZE; i++) {
            String str = br.readLine();
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = str.charAt(j) - '0';
                num[map[i][j]]++;
            }
        }

        play(0, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(map[i][j]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }

    // r, c에 v가 가능한 지 확인하는 함수
    static boolean check(int r, int c, int v) {
        for (int i = 0; i < SIZE; i++) {
            // 가로 탐색
            if (map[r][i] == v)
                return false;
            // 세로 탐색
            if (map[i][c] == v)
                return false;
        }

        // 섹션 탐색
        int startR = r / BOX_SIZE * BOX_SIZE;
        int startC = c / BOX_SIZE * BOX_SIZE;

        // box 내에서 가능한지 체크
        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                if (map[startR + i][startC + j] == v)
                    return false;
            }
        }

        return true;
    }

    // map[r][c]를 채우는 재귀함수
    static boolean play(int r, int c) {
        // 좌표 조절
        if (c == SIZE) {
            r += 1;
            c = 0;
        }

        // 기저 조건: (8,8)까지 다 채워졌으면 종료 후 맵 출력
        if (r == SIZE)
            return true; // 성공 시 true

        if (map[r][c] != 0) { // 이미 채워져 있으면 스킵
            return play(r, c + 1);
        }

        // 현재 칸에 1 ~ 9넣어보기
        for (int v = 1; v <= SIZE; v++) {
            if (num[v] >= SIZE)
                continue; // 이미 v가 모두 채워진 상태임
            if (check(r, c, v)) { // 가능하다면.. 다음 인덱스 호출
                map[r][c] = v;
                num[v]++;

                if (play(r, c + 1))
                    return true; // 다음 칸 채우기 & 만약 다 채워졌으면 true 반환

                // 종료되지 않았으면 map 원상복구
                map[r][c] = 0;
                num[v]--;
            }
        }
        return false;
    }
}
