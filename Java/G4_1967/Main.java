package Java.G4_1967;

import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        int parent;
        List<int[]> edges; // 자식의 간선 저장

        public Node(int parent) {
            this.parent = parent;
            edges = new ArrayList<>();
        }
    }

    static Node[] nodes;
    static int[] maxV, sum;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        maxV = new int[n+1];
        sum = new int[n+1];
        nodes = new Node[n+1];


        StringTokenizer st;
        for(int i=0; i<n-1; i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            Node node = new Node()
        }
    }
}
