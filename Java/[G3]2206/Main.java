import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int x, y, dist;
        boolean isSmashed;

        public Node(int x, int y, int dist, boolean isSmashed) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.isSmashed = isSmashed;
        }
    }

    static boolean[][] map;
    static int N, M;
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j) == '0';
            }
        }
        System.out.println(bfs());
    }

    static int bfs() {
        boolean[][][] visited = new boolean[N][M][2];
        Queue<Node> q = new ArrayDeque<>();
        visited[0][0][0] = true;
        q.add(new Node(0, 0, 1, false));

        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.x == N - 1 && node.y == M - 1) {
                return node.dist;
            }
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= M || visited[nx][ny][node.isSmashed ? 1 : 0])
                    continue;

                if (map[nx][ny]) { // 이동 가능
                    visited[nx][ny][node.isSmashed ? 1 : 0] = true;
                    q.add(new Node(nx, ny, node.dist + 1, node.isSmashed));
                } else {
                    if (!node.isSmashed) {
                        visited[nx][ny][1] = true;
                        q.add(new Node(nx, ny, node.dist + 1, true));
                    }
                }
            }
        }
        return -1;
    }
}
