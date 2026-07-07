import java.io.*;
import java.util.*;

public class Solution2 {
    private static int[] heights;
    private static boolean[] diffs;
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
            sectionCount = 0; // 구간 수

            for(int i = 1; i <= N; i++){
                heights[i] = sc.nextInt();
                diffs[i] = (heights[i] - heights[i-1]) > 0;
            }

            play();

            sb.append('#').append(tc).append(' ').append(sectionCount).append('\n');
        }
        sc.close();
        System.out.print(sb);
    }

    private static void play(){
        //초기화
        int L = 1;
        int M = -1;
        int R = -1;
        int idx = 1;
        for(int i = 1; i < N ;i++){
            if(diffs[i] == diffs[i+1]) continue;
    
            switch(idx++){
                case 1:
                    M = i;
                    break;
                case 2:
                    R = i;
                    break;
            }
            if(idx == 3){
                sectionCount += (M - L)*(R - M);
                L = R;
                idx = 1;
            }
        }
        if(idx == 2){
            R = N;
            sectionCount += (M - L)*(R - M);
        }
    }
}
