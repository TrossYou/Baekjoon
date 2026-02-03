
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int N,M;
        int[] weights;

        int T = Integer.parseInt(br.readLine());
        for(int tc = 1 ; tc <= T ; tc++){
            int answer = Integer.MIN_VALUE;
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            weights = new int[N];

            st = new StringTokenizer(br.readLine());
            for(int n = 0 ; n < N ; n++){
                weights[n] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
