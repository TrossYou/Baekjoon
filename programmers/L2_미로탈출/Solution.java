import java.util.*;

class Solution {
    int rows, cols;
    int[] dr = { -1, 0, 1, 0 };
    int[] dc = { 0, 1, 0, -1 };

    public int solution(String[] maps) {
        rows = maps.length;
        cols = maps[0].length();

        int sr = -1, sc = -1;
        int lr = -1, lc = -1;
        int er = -1, ec = -1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (maps[i].charAt(j)) {
                    case 'S' -> {
                        sr = i;
                        sc = j;
                    }
                    case 'L' -> {
                        lr = i;
                        lc = j;
                    }
                    case 'E' -> {
                        er = i;
                        ec = j;
                    }
                    default -> {
                    }
                }
            }
        }

        // 1) 시작 → 레버
        int tolever = bfs(sr, sc, lr, lc, maps);
        if (tolever == -1)
            return -1;

        // 2) 레버 → 출구
        int toexit = bfs(lr, lc, er, ec, maps);
        if (toexit == -1)
            return -1;

        return tolever + toexit;
    }

    public int bfs(int startR, int startC, int targetR, int targetC, String[] maps) {
        int[][] cntMap = new int[rows][cols];
        for (int[] row : cntMap)
            Arrays.fill(row, -1);

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { startR, startC });
        cntMap[startR][startC] = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            if (r == targetR && c == targetC) {
                return cntMap[r][c];
            }

            for (int dir = 0; dir < 4; dir++) {
                int nr = r + dr[dir];
                int nc = c + dc[dir];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    if (maps[nr].charAt(nc) == 'X' || cntMap[nr][nc] != -1)
                        continue;
                    cntMap[nr][nc] = cntMap[r][c] + 1;
                    queue.offer(new int[] { nr, nc });
                }
            }
        }
        return -1; // 도달 불가
    }
}
