import java.io.*;
import java.util.*;

public class Solution {
    // static 변수 등 기존 구조 유지
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
            
            // totalDate 변수는 제거 (루프 안에서 계산 불가)
            remain1Count = 0;
            remain2Count = 0;

            trees = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                trees[i] = Integer.parseInt(st.nextToken());
                if(trees[i] > max) max = trees[i];
            }

            // [수정 1] 그리디 로직 변경
            // 기존: 3으로 나눠서 1, 2 골고루 분배 -> 실패 원인
            // 변경: 일단 2(짝수)로 최대한 채우고, 1(홀수)은 어쩔 수 없을 때만 챙김
            for(int i=0; i<N; i++){
                int diff = max - trees[i];
                if(diff == 0) continue;
                
                remain1Count += diff % 2; // 홀수라면 1은 무조건 하나 필요
                remain2Count += diff / 2; // 나머지는 전부 2로 채움
            }

            // [수정 2] 후처리 (말씀하신 '더미' 처리 단계)
            // 2가 너무 많으면 2를 깨서 1 두 개로 만듦 (균형 맞추기)
            // 2는 짝수 날만 가능하지만, 1로 쪼개면 홀수 날도 활용 가능하므로 유동성 확보
            
            // 2의 개수가 (1의 개수 + 1)보다 많다면 균형이 안 맞는 상태
            while(remain2Count > remain1Count + 1){
                remain2Count--; // 2 하나 제거
                remain1Count += 2; // 1 두 개 추가 (더미 데이터처럼 활용)
            }
            
            // 최종 날짜 계산
            int totalDate = 0;
            if(remain1Count > remain2Count) {
                totalDate = remain1Count * 2 - 1;
            } else if(remain1Count == remain2Count) {
                totalDate = remain1Count * 2;
            } else { // remain2Count가 1개 더 많은 경우
                totalDate = remain1Count * 2 + 2;
            }

            sb.append('#').append(tc).append(' ').append(totalDate).append('\n');
        }
        System.out.print(sb);
    }
}