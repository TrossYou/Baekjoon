package SWEA.Day0406_problem1;

public class Solution {
    public static void main(String[] args) {
        int b = 0; // 마지막 층의 blue의 개수 -> 위에 y만가능 = 다음의 y1개수가 됨
        int y = 0; // 마지막 층이 yellow인 것 중 1층짜리 개수 -> 위에 y,b가능
        // n = 1일때
        b = 1;
        y = 1;
        for (int i = 2; i <= 8; i++) {
            int oldB = b;
            int oldY = y;

            b = oldY;
            y = oldY + oldB;
        }

        System.out.println(b + y);
    }
}
