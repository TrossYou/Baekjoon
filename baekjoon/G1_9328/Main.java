import java.util.*;
import java.io.*;

public class Main {
  static char[][] map;
  static int[] dx = { -1, 0, 1, 0 };
  static int[] dy = { 0, 1, 0, -1 };
  static int h, w;

  class DoorInfo {
    int keyMask; // 비트 마스크로 획득 가능한 key들 저장
    int docCount; // 문서 수
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());
    while (T-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int h = Integer.parseInt(st.nextToken());
      int w = Integer.parseInt(st.nextToken());
      map = new char[h + 2][w + 2]; // 상하좌우 여백 추가
      for (int i = 1; i < h + 1; i++) {
        String str = br.readLine();
        for (int j = 1; j < w + 1; j++) {
          map[i][j] = str.charAt(j - 1);
        }
      }

      DoorInfo[] doors = new DoorInfo[26]; // 각 index는 알파벳 수( x - 'A');
      bfs(); // door 정보 순회
    }
  }

  static void bfs() {
    boolean[][] visited = new boolean[h + 2][w + 2];
    for (int i = 0; i < h + 2; i++) {
      for (int j = 0; j < w + 2; j++) {
        if (map[i][j] == '*' || visited[i][j])
          continue;
        else {
          if (map[i][j] == '.')
            ;// 다음 경로 bfs호출
          else if (map[i][j] == '$')
            ;// 문서 정보 저장
        }
      }
    }
  }

}
