package SWEA.D5_pro_14596;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class UserSolution {
    static int[][] map;
    static int SIZE;
    static int[] dr = { -1, 0, 1, 0 };
    static int[] dc = { 0, 1, 0, -1 };

    public void init(int N, int mMap[][]) {
        SIZE = N;
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            map[i] = Arrays.copyOf(mMap[i], N);
        }
    }

    public int numberOfCandidate(int M, int mStructure[]) {
        if (M == 1) { // 1일 때는 다 가능
            return SIZE * SIZE;
        }

        int ans = 0;

        // 가로 방향 탐색
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <= SIZE - M; j++) {
                boolean leftToRight = checkFit(i, j, 1, M, mStructure);
                boolean rightToLeft = checkFit(i, j + M - 1, 3, M, mStructure);

                if (leftToRight || rightToLeft)
                    ans++; // 가로에서 가능하면
            }
        }

        // 세로 방향 탐색
        for (int i = 0; i <= SIZE - M; i++) {
            for (int j = 0; j < SIZE; j++) {
                boolean topToBottom = checkFit(i, j, 2, M, mStructure);
                boolean bottomToTop = checkFit(i + M - 1, j, 0, M, mStructure);

                if (topToBottom || bottomToTop)
                    ans++;
            }
        }
        return ans;
    }

    public int maxArea(int M, int mStructure[], int mSeaLevel) {
        int ans = -1;

        // 가로방향 탐색
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <= SIZE - M; j++) {
                // 좌 -> 우 탐색
                boolean leftToRight = checkFit(i, j, 1, M, mStructure);
                if (leftToRight) {
                    ans = Math.max(ans, play(i, j, 1, M, mStructure, mSeaLevel));
                }
                // 우 -> 좌 탐색
                boolean rightToLeft = checkFit(i, j + M - 1, 3, M, mStructure);
                if (rightToLeft) {
                    ans = Math.max(ans, play(i, j + M - 1, 3, M, mStructure, mSeaLevel));
                }
            }
        }

        // 세로방향 탐색
        for (int i = 0; i <= SIZE - M; i++) {
            for (int j = 0; j < SIZE; j++) {
                // 상 -> 하 탐색
                boolean topToBottom = checkFit(i, j, 2, M, mStructure);
                if (topToBottom) {
                    ans = Math.max(ans, play(i, j, 2, M, mStructure, mSeaLevel));
                }

                // 하 -> 상 탐색
                boolean bottomToTop = checkFit(i + M - 1, j, 0, M, mStructure);
                if (bottomToTop) {
                    ans = Math.max(ans, play(i + M - 1, j, 0, M, mStructure, mSeaLevel));
                }
            }
        }
        return ans;
    }

    // 구조물 매칭 검사 함수
    private boolean checkFit(int r, int c, int dir, int M, int[] mStructure) {
        int baseH = map[r][c] + mStructure[0];
        int nr = dr[dir]; // 방향
        int nc = dc[dir];

        for (int k = 1; k < M; k++) {
            if (baseH != map[r + nr * k][c + nc * k] + mStructure[k])
                return false;
        }
        return true;
    }

    // [r][c]에 mStructure을 세우고, mSeaLevel로 계산 -> 가능 여부는 이미 검증됨
    private int play(int r, int c, int dir, int M, int[] mStructure, int mSeaLevel) {
        int nr = dr[dir];
        int nc = dc[dir];

        int[][] newMap = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            newMap[i] = Arrays.copyOf(map[i], SIZE);
        }

        // 벽 세우기
        for (int k = 0; k < M; k++) {
            newMap[r + nr * k][c + nc * k] += mStructure[k];
        }

        // bfs로 잠식되는 좌표 탐색
        boolean[][] flooded = new boolean[SIZE][SIZE];
        Queue<int[]> q = new LinkedList<>();

        // 가장 끝을 순회하며 q에 추가
        for (int i = 0; i < SIZE; i++) {
            if (newMap[0][i] < mSeaLevel) {
                q.add(new int[] { 0, i });
            }
            if (i != 0 && i != SIZE - 1 && newMap[i][0] < mSeaLevel) {
                q.add(new int[] { i, 0 });
            }
            if (i != 0 && i != SIZE - 1 && newMap[i][SIZE - 1] < mSeaLevel) {
                q.add(new int[] { i, SIZE - 1 });
            }
            if (newMap[SIZE - 1][i] < mSeaLevel) {
                q.add(new int[] { SIZE - 1, i });
            }
        }

        while (!q.isEmpty()) {
            int[] area = q.poll();
            int curR = area[0];
            int curC = area[1];
            if (flooded[curR][curC])
                continue;
            flooded[curR][curC] = true;

            for (int d = 0; d < 4; d++) {
                int newR = curR + dr[d];
                int newC = curC + dc[d];
                if (0 <= newR && newR < SIZE && 0 <= newC && newC < SIZE) {
                    if (flooded[newR][newC])
                        continue;
                    if (newMap[newR][newC] < mSeaLevel) {
                        q.add(new int[] { newR, newC });
                    }
                }
            }
        }

        // 침수되지 않은 땅 수 세기
        int leftLand = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!flooded[i][j])
                    leftLand++;
            }
        }

        return leftLand;
    }
}