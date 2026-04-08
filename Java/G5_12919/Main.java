package Java.G5_12919;

import java.io.*;

public class Main {
    static String S, T;
    static int sLen, tLen;
    static boolean isPossible;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = br.readLine();
        T = br.readLine();

        isPossible = false;

        play(S);

        System.out.println(isPossible ? 1 : 0);
    }

    static void play(String nextS) {
        if (nextS.equals(T)) {
            isPossible = true;
            return;
        }
        if (nextS.length() >= T.length())
            return;

        // 뒤에 A추가
        if (isPossible)
            return;
        String next1 = nextS + "A";
        String next1Reverse = new StringBuilder(next1).reverse().toString();
        if (T.contains(next1) || T.contains(next1Reverse))
            play(next1);

        // 뒤에 B추가 후 뒤집기
        if (isPossible)
            return;
        String next2 = nextS + "B";
        String next2Reverse = new StringBuilder(next2).reverse().toString();
        if (T.contains(nextS) || T.contains(next2Reverse))
            play(next2Reverse);
    }
}
