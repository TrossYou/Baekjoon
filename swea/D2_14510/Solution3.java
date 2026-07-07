import java.io.*;
import java.util.*;

public class Solution3 {
    static int[] trees; // 트리의 높이를 받는 배열
    static int remain1Count, remain2Count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            int N = Integer.parseInt(br.readLine());
            int max = 0;
            int totalDate = 0;
            remain1Count = 0;
            remain2Count = 0;

            trees = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                trees[i] = Integer.parseInt(st.nextToken());
                if(trees[i] > max) max = trees[i]; // 최대 높이 구하기
            }

            // 로직
            // 3으로 나눈 나머지에 따라, 연속으로 채울 수 있는 날짜(최소)를 연산으로 가능
            // 3으로 나눈 나머지가 0 -> (짝/홀) +2*몫 (끝은 시작과 동일)
            // 3으로 나눈 나머지가 1 -> (홀) + 2*몫 + 1 (짝)
            // 3으로 나눈 나머지가 2 -> (짝) + 2*몫 + 1 (홀)
            // 따라서, 차이를 바로바로 계산해서, 짝 홀이 일치하면 바로바로
            // 일치하지 않는 애는 따로 Queue에 저장 -> 추후 계산
            boolean isOdd = true; // 홀수면 true, 짝수면 false
            for(int i=0; i<N; i++){
                int diff = max - trees[i];
                if(diff == 0) continue; // diff가 0이면 바로 패스
                int quot = diff/3;
                int remain = diff%3;

                // 홀수로 시작할 때 -> 나머지 0이거나 1가능
                if(isOdd){
                    if(remain == 2){
                        //최대한 일단 더하기
                        totalDate += 2*quot;
                        remain2Count++; // 2나무 하나 추가
                    }else{
                        if(remain == 1){
                            isOdd = !isOdd; // 나머지가 1이면 짝홀 변경
                            totalDate += 2*quot +1;
                        }else{
                            totalDate += 2 * quot;
                        }
                    }
                }else{ // 짝수로 시작할 때 -> 나머지 0이거나 2 가능
                    if(remain == 1){
                        // 일단 최대한 더하기
                        totalDate += 2*quot;
                        remain1Count++;
                    }else{
                        if(remain == 2){
                            isOdd = !isOdd; // 나머지가 2일때만 짝홀 변경
                            totalDate += 2 * quot + 1;
                        }else{
                            totalDate += 2 * quot;
                        }
                    }
                }
            }
            // 후처리
            if(remain1Count != 0 || remain2Count != 0){
                int minCount = Math.min(remain1Count, remain2Count);
                totalDate += 2 * minCount;

                // 1이 더 남은 경우
                if(remain1Count > remain2Count){
                    remain1Count -= minCount;
                    
                    totalDate += 2 * remain1Count;
                    if(isOdd) totalDate--;
                }else if(remain1Count < remain2Count){ // 2이 더 남은 경우
                    int q = remain2Count/3; // 4번동안 3개
                    totalDate += 4 * q;

                    int r = remain2Count%3;
                    if(r == 1){
                        if(isOdd) totalDate += 2;
                        else totalDate++;
                    }else if(r == 2){
                        totalDate += 3;
                    }
                }
            }  

            sb.append('#').append(tc).append(' ').append(totalDate).append('\n');
        }
        System.out.print(sb);
    }
}