import java.io.*;
import java.util.*;

public class Solution{
    static int N, cnt, ans;
    static int[][] map;
    static int[] dx = {1, -1, -1, 1};
    static int[] dy = {1, 1, -1, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=T; tc++){
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            cnt = 0;
            ans = 0;

            for(int i=0; i<N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }
    }

    // \ 방향 직사각형 (가로a, 세로b)
    static boolean leftDiag(int x, int y, int a, int b){
        int nx = x;
        int ny = y;
        for(int idx=0; idx<4; idx++){
            for(int i = 0; i < a; i++){
                nx += dx[idx];
                ny += dy[idx];
            }  
        }
        return true;
    }
}