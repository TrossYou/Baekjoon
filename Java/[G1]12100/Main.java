import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        answer = Integer.MIN_VALUE;

        int[][] initialMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            initialMap[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        dfs(0, initialMap);
        System.out.println(answer);
    }

    static void dfs(int depth, int[][] map) {
        if (depth == 10) {
            // depth가 5인 경우 최댓값 갱신 후 종료
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    answer = Math.max(answer, map[i][j]);
                }
            }
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            int[][] nextMap = move(dir, map);
            if (nextMap != null) {
                dfs(depth + 1, nextMap);
            }
        }
    }

    static int[][] move(int dir, int[][] map) {
        int[][] result = new int[N][N];
        boolean changed = false;

        if (dir == 0) { // 왼쪽
            for (int i = 0; i < N; i++) {
                int idx = 0;
                boolean merged = false;
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == 0)
                        continue;
                    if (idx > 0 && result[i][idx - 1] == map[i][j] && !merged) {
                        result[i][idx - 1] *= 2;
                        answer = Math.max(answer, result[i][idx - 1]);
                        merged = true;
                        changed = true;
                    } else {
                        if (result[i][idx] != map[i][j] || idx != j)
                            changed = true;
                        result[i][idx++] = map[i][j];
                        merged = false;
                    }
                }
            }
        } else if (dir == 1) { // 위
            for (int j = 0; j < N; j++) {
                int idx = 0;
                boolean merged = false;
                for (int i = 0; i < N; i++) {
                    if (map[i][j] == 0)
                        continue;
                    if (idx > 0 && result[idx - 1][j] == map[i][j] && !merged) {
                        result[idx - 1][j] *= 2;
                        answer = Math.max(answer, result[idx - 1][j]);
                        merged = true;
                        changed = true;
                    } else {
                        if (result[idx][j] != map[i][j] || idx != i)
                            changed = true;
                        result[idx++][j] = map[i][j];
                        merged = false;
                    }
                }
            }
        } else if (dir == 2) { // 오른쪽
            for (int i = 0; i < N; i++) {
                int idx = N - 1;
                boolean merged = false;
                for (int j = N - 1; j >= 0; j--) {
                    if (map[i][j] == 0)
                        continue;
                    if (idx < N - 1 && result[i][idx + 1] == map[i][j] && !merged) {
                        result[i][idx + 1] *= 2;
                        answer = Math.max(answer, result[i][idx + 1]);
                        merged = true;
                        changed = true;
                    } else {
                        if (result[i][idx] != map[i][j] || idx != j)
                            changed = true;
                        result[i][idx--] = map[i][j];
                        merged = false;
                    }
                }
            }
        } else { // 아래
            for (int j = 0; j < N; j++) {
                int idx = N - 1;
                boolean merged = false;
                for (int i = N - 1; i >= 0; i--) {
                    if (map[i][j] == 0)
                        continue;
                    if (idx < N - 1 && result[idx + 1][j] == map[i][j] && !merged) {
                        result[idx + 1][j] *= 2;
                        answer = Math.max(answer, result[idx + 1][j]);
                        merged = true;
                        changed = true;
                    } else {
                        if (result[idx][j] != map[i][j] || idx != i)
                            changed = true;
                        result[idx--][j] = map[i][j];
                        merged = false;
                    }
                }
            }
        }

        return changed ? result : null;
    }

}
