import java.io.*;

public class Solution_matrix {
    private static int N, count;
    private static boolean[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;tc++){
            // 변수 초기화
            N = Integer.parseInt(br.readLine());
            count = 0;
            map = new boolean[N][N];
            play(0);
            sb.append('#').append(tc).append(' ').append(count).append('\n');
        }
        System.out.print(sb);
    }

    static void play(int r){
        if(r == N){
            count++;
            return;
        }

        for(int i = 0 ; i < N ; i++){
            if(isPossible(r, i)){
                // 퀸을 놓을 수 있다면, 놓기
                map[r][i] = true;
                play(r+1);
                // 복원
                map[r][i] = false;
            }
        }
    }

    // (r,c)에 퀸을 둘 수 있는 지
    static boolean isPossible(int r, int c){
        for(int i = 0 ; i < r ; i++){
            // 같은 열에 퀸이 있는지 체크
            if(map[i][c]) return false;
            
            // 좌상단 대각선 체크
            if(c - (r - i) >= 0 && map[i][c - (r - i)]) return false;
            
            // 우상단 대각선 체크
            if(c + (r - i) < N && map[i][c + (r - i)]) return false;
        }

        return true;
    }
}