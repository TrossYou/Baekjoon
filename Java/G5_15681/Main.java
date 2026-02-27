package Java.G5_15681;

import java.io.*;
import java.util.*;

public class Main {
  static int N, R, Q;
  static List<Integer>[] adjs; // O(N*??)
  static List<Integer> orders; // O(N)
  static int[] sizeArr, parent; // O(N)

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    R = Integer.parseInt(st.nextToken());
    Q = Integer.parseInt(st.nextToken());

    adjs = new List[N + 1];
    for (int i = 1; i <= N; i++)
      adjs[i] = new ArrayList<>();

    orders = new ArrayList<>();
    sizeArr = new int[N + 1];
    parent = new int[N + 1];

    // 인접 노드 저장 - O(N)
    for (int i = 0; i < N - 1; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      adjs[a].add(b);
      adjs[b].add(a);
    }

    play();

    Arrays.fill(sizeArr, 1);
    for (int i = orders.size() - 1; i >= 0; i--) {
      int cur = orders.get(i);
      int p = parent[cur];
      if (p != 0)
        sizeArr[p] += sizeArr[cur];
    }

    for (int i = 0; i < Q; i++) {
      int a = Integer.parseInt(br.readLine());
      sb.append(sizeArr[a]).append('\n');
    }

    System.out.print(sb);
  }

  static void play() {
    Queue<Integer> q = new LinkedList<>();
    q.add(R);
    parent[R] = 0; // 최상단
    boolean[] isVisited = new boolean[N + 1];

    while (!q.isEmpty()) {
      int node = q.poll();
      isVisited[node] = true;
      orders.add(node); // 순서 기록 - root부터

      for (int child : adjs[node]) {
        if (isVisited[child])
          continue;
        parent[child] = node; // 부모 설정
        q.add(child);
      }
    }
  }
}
