import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] arr= new int[N][2];
        for(int i = 0 ; i < N ; i++){
            arr[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int[][] dp = new int[N][N];
        for(int i = 0 ; i < N ; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        for(int len = 1; len < N ; len++){
            for(int i = 0 ; i < N ; i++){
                int j = i + len;
                if(j < N){
                    
                }
            }
        }

    }
}