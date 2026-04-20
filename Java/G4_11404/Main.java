package Java.G4_11404;

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_VALUE = 10000010;
    static int n,m;
    static int[][] minMap;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());
        minMap = new int[n+1][n+1];
        for(int i = 1; i<=n; i++){
            Arrays.fill(minMap[i], MAX_VALUE);
        }

        m = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i = 0; i< m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            minMap[a][b] = Math.min(minMap[a][b], c);
        }

        // 플로이드 워셜
        for(int k = 1; k <=n; k++){
            for(int i = 1; i<=n; i++){
                for(int j = 1; j <= n; j++){
                    if(i == j){
                        minMap[i][j] = 0;
                        continue;
                    }
                    if(minMap[i][k] != MAX_VALUE && minMap[k][j] != MAX_VALUE)
                    minMap[i][j] = Math.min(minMap[i][j], minMap[i][k] + minMap[k][j]);
                }
            }
        }

        for(int i = 1; i<=n; i++){
            for(int j=1; j<=n; j++){
                sb.append(minMap[i][j] == MAX_VALUE ? 0 : minMap[i][j]).append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
