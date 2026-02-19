import java.io.*;
import java.util.*;

public class Solution {
    static int N, ans;
    static int[][] map;
    static int[] visited;
    static int visitedId;

    static int[] dx = { 1, -1, -1, 1 };
    static int[] dy = { 1, 1, -1, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        visited = new int[101];
        visitedId = 0;

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            ans = -1;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            play();
            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }

    static void play() {
        int num = 0;
        for (int lenSum = N + 1; lenSum >= 4; lenSum--) {
            num++;
            for (int i = lenSum - 2; i >= (lenSum + 1) / 2; i--) {
                int j = lenSum - i;
                for (int r = 0; r < num; r++) {
                    for (int c = j - 1; c <= N - i; c++) {
                        if (i != j) { // 직사각형
                            if (diag(c, r, i, j) || diag(N - c - 1, r, j, i)) {
                                ans = (lenSum - 2) * 2; // 최고 사각형 찾으면 바로 종료
                                return;
                            }
                        } else { // 정사각형
                            if (diag(c, r, i, i)) {
                                ans = (lenSum - 2) * 2;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    // \ 방향 직사각형 : ( a > b ), / 방향 직사각형 : (a < b), 정사각형 : (a == b)
    static boolean diag(int x, int y, int a, int b) {
        visitedId++;
        int nx = x;
        int ny = y;
        for (int idx = 0; idx < 4; idx++) {
            int limit = (idx % 2 == 0) ? a - 1 : b - 1;
            for (int i = 0; i < limit; i++) {
                nx += dx[idx];
                ny += dy[idx];

                int dessert = map[ny][nx];

                // 이번 탐색에서 이미 먹은 디저트라면
                if (visited[dessert] == visitedId)
                    return false;

                // 먹은 디저트 체크
                visited[dessert] = visitedId;
            }
        }
        return true;
    }
}
