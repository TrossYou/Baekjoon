import java.io.*;
import java.util.*;

class Edge {
    int to;
    int cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class Solution {
    static List<Edge>[] adj;

    static int dfs(int now, int parent) {
        int sum = 0;
        for (Edge edge : adj[now]) {
            if (edge.to == parent)
                continue;

            sum += Math.min(edge.cost, dfs(edge.to, now));
        }
        if (sum == 0)
            return Integer.MAX_VALUE;

        return sum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            if (N == 1) {
                System.out.println(0);
                continue;
            }
            adj = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++)
                adj[i] = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());

                adj[u].add(new Edge(v, w));
                adj[v].add(new Edge(u, w));
            }

            int ans = dfs(1, 1);
            System.out.println(ans);
        }
    }
}
