import java.io.*;

public class Solution {
    private static int N,totalSum;
    private static int[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;tc++){
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            totalSum = 0;

            for(int i=0;i<N;i++){
                String str = br.readLine();
                for(int j=0;j<N;j++){
                    map[i][j] = str.charAt(j) - '0';
                    totalSum += map[i][j]; // 총합미리 저장
                }
            }
            
            // 아닌 부분 빼기
            for(int num=(N-1)/2; num > 0 ; num--) { // 감소 개수
                for(int i=0;i<num;i++){ 
                    // 좌측 상단 빼기
                    totalSum -= map[(N-1)/2 - num][i]; 
                    // 우측 상단 빼기
                    totalSum -= map[(N-1)/2 - num][N-1-i]; 
                    // 좌측 하단 빼기
                    totalSum -= map[(N-1)/2+num][i];
                    // 우측 하단 빼기
                    totalSum -= map[(N-1)/2+num][N-1-i];
                }
            }
            
            sb.append('#').append(tc).append(' ').append(totalSum).append('\n');
        }

        System.out.print(sb);
    }
}