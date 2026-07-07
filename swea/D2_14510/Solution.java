import java.io.*;
import java.util.*;

public class Solution {
    static int[] trees;
    static int remain1Count, remain2Count; // 홀수(1) 개수, 짝수(2) 개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            int N = Integer.parseInt(br.readLine());
            int max = 0;
            
            remain1Count = 0;
            remain2Count = 0;

            trees = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                trees[i] = Integer.parseInt(st.nextToken());
                if(trees[i] > max) max = trees[i];
            }

            for(int i=0; i<N; i++){
                int diff = max - trees[i];
                if(diff == 0) continue;
                
                remain1Count += diff % 2; // 홀수라면 1은 무조건 하나 필요
                remain2Count += diff / 2; // 나머지는 전부 2로 채움
            }
            
            // 2의 개수가 (1의 개수 + 1)보다 많다면 균형이 안 맞는 상태
            while(remain2Count > remain1Count + 1){
                remain2Count--; // 2 하나 제거
                remain1Count += 2; // 1 두 개 추가 (더미 데이터처럼 활용)
            }
            
            // 최종 날짜 계산
            int totalDate = 0;
            if(remain1Count > remain2Count) {
                totalDate += remain1Count * 2 - 1;
            } else if(remain1Count == remain2Count) {
                totalDate += remain1Count * 2;
            } else { // remain2Count가 1개 더 많은 경우
                totalDate += remain1Count * 2 + 2;
            }

            sb.append('#').append(tc).append(' ').append(totalDate).append('\n');
        }
        System.out.print(sb);
    }
}