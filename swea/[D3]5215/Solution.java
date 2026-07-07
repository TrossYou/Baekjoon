import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    private static int N,L;
    private static int[][] items;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int tc=1;tc<=T;tc++){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());
            items = new int[N][2]; // [i][0]: 점수, [i][1]: 칼로리

            for(int i=0;i<N;i++){
                st = new StringTokenizer(br.readLine());
                items[i][0] = Integer.parseInt(st.nextToken());
                items[i][1] = Integer.parseInt(st.nextToken());
            }

            int maxScore = getMaxScore();
            sb.append("#").append(tc).append(" ").append(maxScore).append("\n");
        }
        System.out.print(sb);
    }

    static int getMaxScore(){
        int maxScore = 0;

        for(int mask = 1 ; mask < (1<<N) ; mask++){ // 모든 부분집합을 비트 연산으로 완전탐색
            int calSum = 0;
            int scoreSum = 0;
            for(int i=0;i<N;i++){
                if((mask&(1<<i)) != 0){ // i번째 아이템이 포함된 경우
                    calSum += items[i][1];    
                    if(calSum > L) break;
                    scoreSum += items[i][0];         
                }
            }
            if(calSum <= L && scoreSum > maxScore) maxScore = scoreSum;
        }
        return maxScore;
    }
}