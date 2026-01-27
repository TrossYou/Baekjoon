import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr= new int[N+1];
        for(int i = 0 ; i < N ; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
            if(i == N-1) arr[N] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][N];

        for(int len = 1; len < N ; len++){ 
            for(int i = 0 ; i + len < N ; i++){ 
                int j = i + len; 
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = i ; k < j ; k++){ 
                    int cost = dp[i][k] + dp[k+1][j] + arr[i] * arr[k+1] * arr[j+1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        System.out.println(dp[0][N-1]);
    }
}