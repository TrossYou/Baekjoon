import java.io.*;
import java.util.*;

public class Solution {
    private static int[] heights;
    private static boolean[] diffs;
    private static Queue<Integer> mountQ, vallQ; // 산, 골짜기 저장
    private static int N, sectionCount;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int T = sc.nextInt();
        
        for(int tc=1;tc<=T;tc++){
            //전역변수 초기화
            N = sc.nextInt();
            heights = new int[N+1]; // i번째 산의 높이 저장
            diffs = new boolean[N+1];   // i-1번째 산과 i번째 산의 차이를 true(+), false(-)로 저장
            mountQ = new LinkedList<>();// 산 인덱스 저장(앞에서부터 저장)
            vallQ = new LinkedList<>(); // 골짜기 인덱스 저장
            sectionCount = 0; // 구간 수

            for(int i = 1; i <= N; i++){
                heights[i] = sc.nextInt();
                diffs[i] = (heights[i] - heights[i-1]) > 0;
            }

            for(int i = 2; i < N ;i++){ // 양 끝은 산이 될 수 없으니 제외
                if(diffs[i] && !diffs[i+1]) mountQ.add(i); // 상승(true)에서 하강(false)인 경우 산
                else if(!diffs[i] && diffs[i+1]) vallQ.add(i); // 하강(false)에서 상승(true)인 경우 골짜기
            }

            if(!mountQ.isEmpty()){ // 산이 애초에 없는 경우는 바로 종료
                // 산이 포함된 구간 구하기
                if(diffs[2]){ // 만약 +로 시작된 경우, 산 먼저 -> 1부터 구간 가능
                    countSection(1);
                }else{ // 만약 -로 시작된 경우, 골짜기 먼저 -> 첫 골짜기 구간부터
                    countSection(vallQ.poll());
                }
            }

            sb.append('#').append(tc).append(' ').append(sectionCount).append('\n');
        }
        sc.close();
        System.out.print(sb);
    }

    static void countSection(int startIdx){
        // 골짜기가 끝난 경우.. 마지막 산과 N까지 계산 -> vallQ에 N넣기
        if(vallQ.isEmpty()) vallQ.add(N);
        
        int endIdx = vallQ.peek(); // 다음 골짜기까지 가능 다음 시작 인덱스이므로 제거x
        int mount = mountQ.poll(); // 산 좌표 
        sectionCount += (mount - startIdx)*(endIdx-mount);

        // 산이 끝난 경우 종료
        if(endIdx == N || mountQ.isEmpty()) return;
        countSection(vallQ.poll());
    }
}
