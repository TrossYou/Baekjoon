package Java.G5_6198;

import java.io.*;

public class Main_TLE {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        long sum = 0;
        for (int i = 1; i < N; i++) {
            for (int j = i + 1; j <= N; j++) {
                if (arr[i] <= arr[j])
                    break;
                sum++;
            }
        }

        System.out.println(sum);
    }
}