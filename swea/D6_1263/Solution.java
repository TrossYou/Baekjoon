package SWEA.D6_1263;

import java.io.*;
import java.util.*;

public class Solution {
    static final int MAX_DIST = 10000;
    static int N, ans, min;
    static boolean[][] idjArr;
    static int[][] dist;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            ans = Integer.MAX_VALUE;

            idjArr = new boolean[N][N];
            dist = new int[N][N];
            for (int i = 0; i < N; i++) {
                Arrays.fill(dist[i], MAX_DIST);
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    idjArr[i][j] = st.nextToken().equals("0") ? false : true;
                    if (i == j)
                        dist[i][j] = 0;
                    if (idjArr[i][j])
                        dist[i][j] = 1;
                }
            }

            play();
            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }

    static void play() {
        // 클로이드 워셜
        for (int k = 0; k < N; k++) {
            for (int s = 0; s < N; s++) {
                for (int e = 0; e < N; e++) {
                    if (dist[s][k] != MAX_DIST && dist[k][e] != MAX_DIST)
                        dist[s][e] = Math.min(dist[s][e], dist[s][k] + dist[k][e]);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = 0; j < N; j++) {
                sum += dist[i][j];
            }
            ans = Math.min(ans, sum);
        }
    }
}
