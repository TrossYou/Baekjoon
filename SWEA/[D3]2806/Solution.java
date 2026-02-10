import java.io.*;

public class Solution {
    private static int totalCnt, N;
    private static boolean[] col, slash, bSlash;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for(int tc=1;tc<=T;tc++){
            N = Integer.parseInt(br.readLine());
            totalCnt = 0;
            col = new boolean[N+1];
            slash = new boolean[2*N+1]; // 합이 일정함 '/'
            bSlash = new boolean[2*N]; // 차가 일정함 '\'

            setQueen(1);
            sb.append('#').append(tc).append(' ').append(totalCnt).append('\n');
        }

        System.out.print(sb);
    }

    static void setQueen(int row){
        if(row > N){
            ++totalCnt;
            return;
        }

        for(int c = 1; c <= N; c++){
            // 유망성 체크
            if(col[c] || slash[row+c] || bSlash[(row-c)+N]) continue; //가지치기

            col[c] = slash[row+c] = bSlash[(row-c)+N] = true;
            setQueen(row+1);
            col[c] = slash[row+c] = bSlash[(row-c)+N] = false; // 복원
        }
    }
}
