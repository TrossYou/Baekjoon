import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    private static int[] tickets, monPlans, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=T;tc++){
            // 변수 초기화
            tickets = new int[4]; // 1일, 1달, 3달, 1년 이용권 가격
            monPlans = new int[15]; // 1~12월 사용 계획 0인덱스, 13,14는 3달 이용권 위해 추가
            dp = new int[13]; // i월 까지 최소 값

            // 이용권 가격 입력
            st = new StringTokenizer(br.readLine());
            for(int i = 0 ; i < 4 ; i++){
                tickets[i] = Integer.parseInt(st.nextToken());
            }

            // 이용 계획 입력
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= 12; i++){
                monPlans[i] = Integer.parseInt(st.nextToken());
            }

            start();
            int answer = Math.min(dp[12], tickets[3]); // dp[12]과 1년 티켓 중 더 작은 값

            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }
        System.out.print(sb);
    }

    static void start(){
        // 각 달 별로 dp 채우기
        for(int mon = 1; mon <= 12 ; mon++){
            dp[mon] = getMinAmount(mon);
        }
    }

    // 각 달에서 최소값 계산하는 함수
    static int getMinAmount(int mon){
        int minAmount = Integer.MAX_VALUE;
        // 1일 치로 계산
        minAmount = Math.min(minAmount, dp[mon-1] + monPlans[mon]*tickets[0]);

        // 1달 치로 계산
        minAmount = Math.min(minAmount, dp[mon-1] + tickets[1]);

        // 3달 치로 계산 (끝나는 날 기준 )
        if(mon >= 3) minAmount = Math.min(minAmount, dp[mon-3]+tickets[2]);
        // 12월에서는, 3달 계산이 조금 다름
        if(mon == 12) {
            minAmount = Math.min(minAmount, dp[10]+tickets[2]);
            minAmount = Math.min(minAmount, dp[11] + tickets[2]);
        }
        return minAmount;
    }
}

