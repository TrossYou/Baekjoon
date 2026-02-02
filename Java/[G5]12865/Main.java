import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, Integer> map = new HashMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] dp = new int[K+1]; // 남은 용량이 idx일 때, 최대의 가치 저장

        for(int i = 0 ; i < N ; i++){
            st = new StringTokenizer(br.readLine());
            int key = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            map.put(key, value);
            
        }


        for(int i = 1 ; i < K+1 ; i++){
            // dp[i] = map.getOrDefault(i,0);// 현재 무게의 물건이 있다면 저장 -> getOrDefault 메서드 알게 됨
            dp[i] = Math.max(dp[i-1] + dp[1], map.getOrDefault(i, 0)); // dp최대값 계산
        }

        System.out.println(dp[K]);
    }
}
