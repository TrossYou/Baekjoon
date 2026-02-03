import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // HashMap<Integer, Integer> map = new HashMap<>(); // 배열이 훨씬 효율적
        int[] values;

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        values = new int[K+1]; //무게가 idx일때 가치 저장
        int[] dp = new int[K+1]; // 남은 용량이 idx일 때, 최대의 가치 저장

        for(int i = 0 ; i < N ; i++){
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            for(int w = K ; w >= weight ; w--){
                dp[w] = Math.max(dp[w], dp[w - weight] + value);
            }
        }

        // for(int i = 1 ; i < K+1 ; i++){
        //     // dp[i] = map.getOrDefault(i, 0);
        //     // for(int j = 1 ; j <= i/2 ; j++){
        //     //     dp[i] = Math.max(dp[i-j] + dp[j], dp[i]); // dp최대값 계산
        //     // }
        //     for(int  w = i ; w > 0 ; w--){ // weigt를 줄이며 value 탐색 -> 중복 제거
        //         if(values[w] != 0) {
        //             int v = values[w];
        //             dp[i] = Math.max(dp[i], dp[i-w] + v);
        //         }
        //     }
        // }
        System.out.println(dp[K]);
    }
}
