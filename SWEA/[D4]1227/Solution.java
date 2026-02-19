import java.io.*;

public class Solution {
  static final int MAP_SIZE = 100;
  static int[][] map;
  static boolean[][] visited;
  static int ans;
  static int[] dx = { 0, 1, 0, -1 };
  static int[] dy = { -1, 0, 1, 0 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    for (int tc = 1; tc <= 10; tc++) {
      br.readLine(); // 인풋 날리기
      ans = 0;
      map = new int[MAP_SIZE][MAP_SIZE];
      visited = new boolean[MAP_SIZE][MAP_SIZE];
      for (int i = 0; i < MAP_SIZE; i++) {
        String str = br.readLine();
        for (int j = 0; j < MAP_SIZE; j++) {
          map[i][j] = Integer.parseInt(str.substring(j, j + 1));
        }
      }
      play();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    dfs(1, 1);
  }

  static void dfs(int r, int c) {
    for (int idx = 0; idx < 4; idx++) {
      int nx = c + dx[idx];
      int ny = r + dy[idx];
      // 테두리는 어차피 벽이므로 검사x
      if (nx > 0 && ny > 0 && nx < MAP_SIZE - 1 && ny < MAP_SIZE - 1 && map[ny][nx] == 0 && !visited[ny][nx]) {
        visited[ny][nx] = true;
        dfs(ny, nx);
      }
      if (map[ny][nx] == 3) {
        ans = 1;
        return;
      }
    }
    return;
  }
}
