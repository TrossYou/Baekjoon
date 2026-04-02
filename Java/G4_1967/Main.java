package Java.G4_1967;

import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        int parent;
        boolean isLeaf;
        List<int[]> edges; // 자식의 간선 저장

        public Node(int parent) {
            this.parent = parent;
            isLeaf = true;
            edges = new ArrayList<>();
        }
    }

    static Node[] nodes;
    static boolean[] isReady;
    static int[] maxV;
    static int n, ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        maxV = new int[n + 1];
        nodes = new Node[n + 1];

        // 루트노드 추가
        Node root = new Node(1);
        nodes[1] = root;
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 부모 노드 체크
            if (nodes[p] == null) { // 부모가 없으면, 새로운 트리 생성
                Node newRoot = new Node(p);
                nodes[p] = newRoot;
            }

            // 노드 생성
            if (nodes[c] == null) { // 이미 생성된 노드임
                Node node = new Node(p);
                nodes[c] = node;
            } else {
                nodes[c].parent = p;
            }

            Node parent = nodes[p];
            parent.edges.add(new int[] { c, w }); // p->c:w간선을 저장
            parent.isLeaf = false;
        }

        ans = 0;
        // 탐색하기
        play(1);
        System.out.print(ans);
    }

    static void play(int idx) {
        // 자식 모두 준비 되었는지 확인
        int firstValue = 0, secondValue = 0;
        for (int[] node : nodes[idx].edges) {
            int c = node[0];
            int w = node[1];

            if (maxV[c] == 0 && !nodes[c].isLeaf) { // 리프노드가 아닌데, maxV가 0인 경우 - 아직 계산이 완료되지 않은 상황
                play(c);
            }
            int curValue = maxV[c] + w;
            if (firstValue < curValue) {
                firstValue = curValue;
                secondValue = firstValue;
            } else if (secondValue < curValue) {
                secondValue = curValue;
            }
        }

        maxV[idx] = firstValue;
        ans = Math.max(ans, firstValue + secondValue);
    }
}
