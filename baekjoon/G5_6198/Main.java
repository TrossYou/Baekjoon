package Java.G5_6198;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        Deque<Integer> stack = new ArrayDeque<>();
        long sum = 0;

        for (int i = 0; i < N; i++) {
            int h = Integer.parseInt(br.readLine());

            while (!stack.isEmpty() && stack.peekLast() <= h) {
                stack.pollLast();
            }

            sum += stack.size();
            stack.offerLast(h);
        }

        System.out.println(sum);
    }
}