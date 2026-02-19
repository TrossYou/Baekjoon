import java.io.*;
import java.util.*;

public class Solution {
    static int N, gap;
    static int[][] map;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st; 

        int T = Integer.parseInt(br.readLine());

        for(int tc=1;tc<=T;tc++){
            N = Integer.parseInt(br.readLine());
            gap = Integer.MAX_VALUE;
            map = new int[N][N];
            visited = new boolean[N];

            for(int i = 0; i < N ; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < N ; j++){
                    if(i > j){
                        map[j][i] += Integer.parseInt(st.nextToken());
                    }
                    else map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            johap(0, 0);

            sb.append('#').append(tc).append(' ').append(gap).append('\n');
        }
        System.out.print(sb);
    }    

    // foodQ: 현재까지 추가된 인덱스큐, num: 현재까지 추가된 크기, idx: 추가 시작 인덱스
    static void johap(int num, int idx){
        if(num == N/2){
            calcDiff();
            return;
        }

        for(int i=idx; i<N; i++){
            // 현재 공 삽입
            visited[i] = true;
            johap(num+1, i+1);
            // 복귀
            visited[i] = false;
        }
    }

    static void calcDiff(){
        int sumA = 0;
        int sumB = 0;

        for(int i = 0 ; i < N ; i++){
            for(int j = i+1 ; j < N ; j++){
                if(visited[i] && visited[j]) sumA += (map[i][j]);
                else if(!visited[i] && !visited[j]) sumB += map[i][j];
            }
        }

        gap = Math.min(gap, Math.abs(sumA - sumB));
    }
}