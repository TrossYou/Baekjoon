import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;tc++){
            int N = Integer.parseInt(br.readLine());
            int answer = play(N);
            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }

    static int play(int N){
        int bit = 0;

        int nn = 0;
        while(bit != 1024){ // 10번째
            nn += N;
            String nStr = Integer.toString(nn);
            for(int i = 0 ; i < nStr.length(); i++){
                int num = nStr.charAt(i);
                bit |= (1 << num); 
            }
        }
        return nn;
    }
}
