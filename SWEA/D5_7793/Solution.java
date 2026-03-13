package SWEA.D5_7793;

import java.util.*;
import java.io.*;

public class Solution {
    static int N,M,Sr,Sc, ans; // S: 수연이의 위치
    static char[][] map;
    static int[][] devilTime; // devil이 퍼지는 시간
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            ans = 0;

            // 입력받기
            map = new char[N][M];
            devilTime = new int[N][M];
            for(int i=0; i<N; i++){
                String line = br.readLine();
                for(int j=0; j<M; j++){
                    map[i][j] = line.charAt(j);
                    devilTime[i][j] = map[i][j] == '*' ?  0 : -1;
                }
            }
        
            // devil 퍼트리기
            spreadDevil();


            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }    

    static void spreadDevil(){
        
    }

}
