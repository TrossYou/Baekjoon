package Programmers.L3_기차선로;

import java.util.ArrayList;
import java.util.List;

class Solution {
  int[] dr = { -1, 0, 1, 0 };
  int[] dc = { 0, 1, 0, -1 };
  int[] opp = { 2, 3, 0, 1 };

  // 선로별 이동 방향
  int[][] outInfo = {
      {},
      { -1, 3, -1, 1 }, // 1
      { 2, -1, 0, -1 }, // 2
      { 2, 3, 0, 1 }, // 3
      { 3, -1, -1, 0 }, // 4
      { 1, 0, -1, -1 }, // 5
      { -1, 2, 1, -1 }, // 6
      { -1, -1, 3, 2 } // 7
  };

  int[][] grid;
  boolean[][] visited;
  List<int[]> baseRail = new ArrayList<>();
  int answer = 0;
  int n, m;

  public int solution(int[][] grid) {
    this.grid = grid;
    n = grid.length;
    m = grid[0].length;
    visited = new boolean[n][m];

    // 이미 있는 선로 목록
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] != -1 && grid[i][j] != 0)
          baseRail.add(new int[] { i, j });
      }
    }

    visited[0][0] = true;
    play(0, 0, 1, 3);
    return answer;
  }

  public void play(int r, int c, int rail, int inDir) {
    int outDir = outInfo[rail][inDir]; // 나가는 방향
    if (outDir == -1)
      return;

    int nr = r + dr[outDir];
    int nc = c + dc[outDir];
    int newInDir = opp[outDir];

    if (nr < 0 || nr >= n || nc < 0 || nc >= m)
      return;
    if (grid[nr][nc] == -1)
      return; // 장애물
    if (visited[nr][nc])
      return; // 이미 방문

    // 목적지
    if (nr == n - 1 && nc == m - 1) {
      visited[nr][nc] = true;
      if (isPossible())
        answer++;
      visited[nr][nc] = false; // 취소
      return;
    }

    visited[nr][nc] = true;

    if (grid[nr][nc] == 0) {
      for (int nextRail = 1; nextRail <= 7; nextRail++) {
        // 3번 제외
        if (nextRail == 3)
          continue;
        if (outInfo[nextRail][newInDir] != -1) {
          grid[nr][nc] = nextRail;
          play(nr, nc, nextRail, newInDir);
          grid[nr][nc] = 0; // 다시 0
        }
      }
    } else {
      play(nr, nc, grid[nr][nc], newInDir);
    }

    visited[nr][nc] = false;
  }

  boolean isPossible() {
    for (int[] p : baseRail) {
      if (!visited[p[0]][p[1]])
        return false;
    }
    return true;
  }
}
