package SWEA.D5_7793;

import java.util.*;
import java.io.*;

public class Solution_tmp {
    static int N,M,Sr, Sc, visit; // S: 수연 좌표, X: 악마 위치, visit: 수연이가 방문할 수 있는 남은 칸 수 -> 0인데 안끝났으면 'GAME OVER'
    static char[][] map; 
    static boolean[][] visited; // 수연이가 방문한 곳
    static int ans;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            ans = 0;
            visit = N*M;
            visit -= 2; // 수연이가 현재 있는 칸, 악마 칸 빼기

            map = new char[N][M];

            for(int i=0;i <N; i++){
                String line = br.readLine();
                for(int j=0; j<M;j++){
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == 'S'){
                        Sr = i;
                        Sc = j;
                        visited[i][j] = true;
                    }else if(map[i][j] == 'X'){
                        visit--; // 방문할 수 없는 칸 --
                    }
                }
            }

            boolean result = play();
            sb.append('#').append(tc).append(' ').append(result ? ans : "GAME OVER").append('\n');
        }
        System.out.print(sb);
    }

    static boolean play(){
        // 악마의 손아귀 퍼짐
        spreadDevil();
        // 수연이 이동 -> 상하좌우 - bfs로 하고 싶은데.. 시간 별로,,악마의 손아귀 퍼짐
        if(visit == 0) return false;


        return true;
    }

    static void spreadDevil(){
        boolean[][] spreadNow = new boolean[N][M]; // 이번에 퍼진 칸인지 기록
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j] == '*' && !spreadNow[i][j]){ // 독이 있고, 이번에 퍼진게 아닌 경우
                    for(int dir=0; dir<4; dir++){ // 사방으로 퍼짐
                        int nr = i + dr[dir];
                        int nc = j + dc[dir];
                        if(nr >= 0 && nr < N && nc >= 0 && nc < M){
                            if(map[nr][nc] != '*'){ // 독이 없으면 퍼트리기
                                map[nr][nc] = '*';
                                spreadNow[nr][nc] = true;
                                visit--; // 오염된 칸 추가
                            }
                        }
                    }
                }
            }
        }
    }
}
