import java.io.*;
import java.util.*;

public class Solution {
    static int[][] map;
    static int SIZE = 16;

    static class Point{
        int x,y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static int bfs(){
        boolean[][] visited = new boolean[SIZE][SIZE];
        
        Queue<Point> que = new ArrayDeque<>();
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        visited[1][1] = true;
        que.add(new Point(1,1));
        while(!que.isEmpty()){
            Point p = que.poll();
            for(int i = 0 ; i < 4 ; i++){
                int ny = p.y + dy[i];
                int nx = p.x + dx[i];
                if(nx < 0 || nx >= SIZE || ny < 0 || ny >= SIZE || map[ny][nx] == 1 || visited[ny][nx]) continue;
                if(map[ny][nx] == 3) return 1;

                visited[ny][nx] = true;
                que.add(new Point(nx, ny));
                }
            }
        return 0;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        for(int tc = 1 ; tc <= 10; tc++){
            br.readLine(); //tc값
            map = new int[SIZE][SIZE];

            for(int i = 0 ;i < SIZE ; i++){
                String line = br.readLine();
                for(int j = 0 ; j < SIZE; j++){
                    map[i][j] = line.charAt(j) - '0';
                }
            }

            sb.append("#").append(tc).append(" ").append(bfs()).append("\n");
        }
        System.out.print(sb);
    }
}
