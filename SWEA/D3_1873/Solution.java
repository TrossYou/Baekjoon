package SWEA.D3_1873;

import java.util.*;
import java.io.*;

public class Solution {
  static int T, H, W, N, tankR, tankC;
  static char[][] map;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      H = Integer.parseInt(st.nextToken());
      W = Integer.parseInt(st.nextToken());
      tankR = -1;
      tankC = -1;

      map = new char[H][W];

      for (int i = 0; i < H; i++) {
        String line = br.readLine();
        for (int j = 0; j < W; j++) {
          map[i][j] = line.charAt(j);
          if (map[i][j] == '^' || map[i][j] == 'v' || map[i][j] == '<' || map[i][j] == '>') {
            tankR = i;
            tankC = j;
          }
        }
      }

      N = Integer.parseInt(br.readLine());
      String commands = br.readLine();
      for (int j = 0; j < N; j++) {
        char com = commands.charAt(j);
        play(com);
      }

      sb.append('#').append(tc).append(' ');
      for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
          sb.append(map[i][j]);
        }
        sb.append('\n');
      }
    }

    System.out.print(sb);
  }

  static void play(char com) {
    if (com == 'U') {
      map[tankR][tankC] = '^';
      if (tankR - 1 >= 0 && map[tankR - 1][tankC] == '.') { // 이동 가능하다면
        map[tankR][tankC] = '.'; // 평지로 변경
        tankR--; // 위로 한 칸
        map[tankR][tankC] = '^'; // 위로 한 칸
      }
    } else if (com == 'D') {
      map[tankR][tankC] = 'v';
      if (tankR + 1 < H && map[tankR + 1][tankC] == '.') { // 이동 가능하다면
        map[tankR][tankC] = '.'; // 평지로 변경
        tankR++; // 아래로 한 칸
        map[tankR][tankC] = 'v';
      }
    } else if (com == 'L') {
      map[tankR][tankC] = '<';
      if (tankC - 1 >= 0 && map[tankR][tankC - 1] == '.') { // 이동 가능하다면
        map[tankR][tankC] = '.'; // 평지로 변경
        tankC--;
        map[tankR][tankC] = '<';
      }
    } else if (com == 'R') {
      map[tankR][tankC] = '>';
      if (tankC + 1 < W && map[tankR][tankC + 1] == '.') { // 이동 가능하다면
        map[tankR][tankC] = '.'; // 평지로 변경
        tankC++;
        map[tankR][tankC] = '>';
      }
    } else if (com == 'S') {
      char dir = map[tankR][tankC]; // 탱크가 현재 바라보고 있는 방향
      int dr = 0;
      int dc = 0;
      if (dir == '^') {
        dr = -1;
      } else if (dir == 'v') {
        dr = +1;
      } else if (dir == '<') {
        dc = -1;
      } else if (dir == '>') {
        dc = +1;
      }

      // 맵 안쪽
      int shotR = tankR;
      int shotC = tankC;
      while (shotR + dr >= 0 && shotR + dr < H && shotC + dc >= 0 && shotC + dc < W) {
        if (map[shotR + dr][shotC + dc] == '.' || map[shotR + dr][shotC + dc] == '-') { // 평지,연못이라면
          shotR += dr; // 포탄 이동
          shotC += dc;
        } else if (map[shotR + dr][shotC + dc] == '*') { // 벽이라면
          map[shotR + dr][shotC + dc] = '.'; // 벽 제거
          break;
        } else if (map[shotR + dr][shotC + dc] == '#') {
          break; // 그냥 종료
        }
      }
    }
  }
}
