import java.io.*;
import java.util.*;

public class Solution {
    static int N, gap, totalSum;
    static int[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st; 

        int T = Integer.parseInt(br.readLine());

        for(int tc=1;tc<=T;tc++){
            totalSum = 0;
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];

            for(int i = 0; i < N ; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < N ; j++){
                    // 입력 받으면서 S_ij와 S_ji 합산
                    if(i > j) {
                        map[j][i] += Integer.parseInt(st.nextToken());
                        totalSum += map[j][i];
                    }
                    else map[i][j] = Integer.parseInt(st.nextToken()); // 기본 입력
                }
            }
            play();

            sb.append('#').append(tc).append(' ').append(gap).append('\n');
        }
        System.out.print(sb);
    }    

    static void play(){
        int res = Integer.MAX_VALUE;
        for(int i = 1 ; i <= N/2; i++){ // N/2를 뽑아야 하므로 최대 시작 인덱스가 N/2
            res = Math.max(res, johap(i, 0, 0));
        }
        gap = res;
    }

    // idx: 시작 인덱스, num: 뽑은 수
    static int johap(int idx, int num, int nowSum){
        int sum = nowSum;
        if(num == N/2){ // 다 뽑은 경우
            return Math.abs(totalSum - 2 * sum ); // |A - B|는 |전체 - 2A|와 동일
        }

        for(int i = idx; i < N ; i++){
            sum += map[idx][i]; 
            johap(i+1, num+1, sum);
        }

        return sum;
    }
}