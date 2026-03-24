package Java.G3_17471;

import java.util.*;
import java.io.*;

public class Main {
  static int N, ans;
  static int[] peopleNum;
  static boolean[] groupA;
  static boolean[][] idjMap;
  static List<Integer> reachable; // 1번에서 도달 가능한 도시들

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    N = Integer.parseInt(br.readLine());
    ans = -1;
    peopleNum = new int[N + 1];
    groupA = new boolean[N + 1];
    idjMap = new boolean[N + 1][N + 1];
    reachable = new ArrayList<>();

    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 1; i <= N; i++)
      peopleNum[i] = Integer.parseInt(st.nextToken());

    for (int i = 1; i <= N; i++) {
      st = new StringTokenizer(br.readLine());
      int num = Integer.parseInt(st.nextToken());
      for (int j = 0; j < num; j++) {
        idjMap[i][Integer.parseInt(st.nextToken())] = true;
      }
    }

    // 1번에서 도달 가능한 도시들 찾기
    findReachable();

    // 1번은 항상 groupA에 포함, reachable 도시들(1번 제외)만 부분집합 생성
    groupA[1] = true;
    makeSubset(1); // reachable[1]부터 처리

    System.out.println(ans);
  }

  static void findReachable() {
    // 1번에서 도달 가능한 모든 도시 찾기
    boolean[] visited = new boolean[N + 1];
    Queue<Integer> q = new LinkedList<>();
    q.add(1);
    visited[1] = true;
    reachable.add(1);

    while (!q.isEmpty()) {
      int city = q.poll();
      for (int i = 1; i <= N; i++) {
        if (idjMap[city][i] && !visited[i]) {
          visited[i] = true;
          reachable.add(i);
          q.add(i);
        }
      }
    }
  }

  static void checkNClac() {
    List<Integer> groupAList = new ArrayList<>();
    List<Integer> groupBList = new ArrayList<>();

    for (int i = 1; i <= N; i++) {
      if (groupA[i])
        groupAList.add(i);
      else
        groupBList.add(i);
    }

    // groupA와 groupB 모두 연결되어 있는지 확인
    if (groupBList.isEmpty() || !isConnected(groupAList) || !isConnected(groupBList))
      return;

    // 차이 계산
    int sumA = 0;
    int sumB = 0;

    for (int i = 1; i <= N; i++) {
      if (groupA[i]) {
        sumA += peopleNum[i];
      } else {
        sumB += peopleNum[i];
      }
    }

    int diff = Math.abs(sumA - sumB);
    if (ans == -1 || ans > diff) {
      ans = diff;
    }
  }

  static boolean isConnected(List<Integer> group) {
    if (group.isEmpty())
      return false;

    boolean[] isVisited = new boolean[N + 1];
    Queue<Integer> q = new LinkedList<>();
    int start = group.get(0);
    q.add(start);
    isVisited[start] = true;
    int count = 1;

    while (!q.isEmpty()) {
      int city = q.poll();
      for (int i = 1; i <= N; i++) {
        if (idjMap[city][i] && !isVisited[i] && group.contains(i)) {
          isVisited[i] = true;
          q.add(i);
          count++;
        }
      }
    }

    return count == group.size();
  }

  static void makeSubset(int idx) {
    if (idx == reachable.size()) {
      checkNClac();
      return;
    }

    // reachable.get(idx)를 groupA에 포함
    int city = reachable.get(idx);
    groupA[city] = true;
    makeSubset(idx + 1);

    // reachable.get(idx)를 groupA에 불포함
    groupA[city] = false;
    makeSubset(idx + 1);
  }
}
