package Java.G5_15681;

import java.util.*;
import java.io.*;

public class Main1 {
  // static class Node {
  // int parent, childCnt;
  // // List<Integer> children; // 자식 노드 번호 저장

  // Node(int parent) {
  // this.parent = parent;
  // childCnt = 0;
  // // children = new ArrayList<>();
  // }
  // }

  // static boolean[][] connects; // a와 b 간선 저장 -> 얘가 문제인듯!!!!! 1 * 10^10 ->
  // 인듯!!!!1GB
  // static Map<Integer, Node> nodeMap; // node 인스턴스 저장
  // static int[][] nodeArr; // nodeArr[i][0]: i노드의 부모, nodeArr[i][1]: 자식의 수 ->
  // 부모조차 저장할 필요 없다! topdownDP
  static int[] sizeArr; // size[i]: 자식 수 - 사이즈가 -1이면 방문 안한 것 O(N)
  static List<Integer>[] connects; // i의 자식들 번호 저장 O(N*N) // 실제는 그보다 작음
  static int N, R, Q;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken()); // 정점 수
    R = Integer.parseInt(st.nextToken()); // 루트 번호
    Q = Integer.parseInt(st.nextToken()); // 쿼리 수

    // connects = new boolean[N + 1][N + 1]; // [i][j]: i번 노드와 연결된 노드 수
    connects = new List[N + 1];
    for (int i = 1; i <= N; i++) {
      connects[i] = new ArrayList<>();
    }

    // 간선 입력 받기: 접점 - 1 개
    for (int i = 0; i < N - 1; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      // 간선 추가
      // connects[a][b] = true;
      // connects[b][a] = true;
      connects[a].add(b);
      connects[b].add(a);
    }

    // nodeMap = new HashMap<>();
    // nodeArr = new int[N + 1][2];
    // for (int i = 1; i <= N; i++) {
    // nodeArr[i][0] = i; // parent를 본인 자신으로 생성
    // nodeArr[i][1] = 0; // 자식 수를 0으로 초기화
    // // nodeMap.put(i, new Node(i)); // parent를 본인 자신으로 생성
    // }
    sizeArr = new int[N + 1];
    Arrays.fill(sizeArr, -1); // 확인 했는지도 확인

    // makeTree(R, -1);
    getSize(R); // size 계산
    for (int i = 0; i < Q; i++) {
      int q = Integer.parseInt(br.readLine());
      sb.append(sizeArr[q]).append('\n');
    }

    System.out.print(sb);
  }

  // 트리를 만드는 함수
  // static void makeTree(int curNode, int parent) {
  // // Node currentNode = nodeMap.get(nodeNum);
  // if (nodeArr[curNode][0] != curNode)
  // return; // 본인이 아니면 반환(이미 계산됨)

  // nodeArr[curNode][0] = parent; // 부모 설정

  // for (int i = 1; i <= N; i++) {
  // if (i != curNode && connects[curNode].contains(i) && i != parent) { // 간선이
  // 있고, 부모가 아니라면
  // // Node child = nodeMap.get(i);
  // // currentNode.children.add(i); // 자식에 추가
  // makeTree(i, curNode);
  // // currentNode.childCnt += child.childCnt + 1;
  // nodeArr[curNode][1] += nodeArr[i][1] + 1;
  // }
  // }
  // }

  // 서브트리 사이즈 계산 함수 - topdownDP
  static int getSize(int curNode) {
    if (sizeArr[curNode] != -1)
      return 0; // 이미 탐색한 노드라면 부모임 -> 그냥 넘어가기

    sizeArr[curNode] = 1;

    for (int node : connects[curNode]) {
      sizeArr[curNode] += getSize(node);
    }

    return sizeArr[curNode];
  }
}
