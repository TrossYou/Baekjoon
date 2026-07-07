import java.io.*;

public class Solution {
  static String N;
  static int maxTurn;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      N = br.readLine();
      maxTurn = 0;

      if (N.length() > 1) {
        // 모든 조합으로 쪼개는 함수

      }
      sb.append('#').append(tc).append(' ').append(maxTurn).append('\n');
    }
  }

  // 모든 조합으로 쪼개는 함수
  static void divide(String n) {
    // 만약 들어온 값이 2자리 이하면 지금까지의

  }

  // 비트마스크에 따라 곱 연산을 하는 함수
  static long mulMask(int mask, String n) {
    int res = 1;
    int start = 0;
    int end = n.length() - 1;

    for (int i = 0; i < n.length(); i++) {
      if ((mask & (1 << i)) != 0) { // 마스크에 포함되어 있다면
        res *= Integer.parseInt(n.substring(start, i));
        start = i;
      }
    }

    return res;
  }
}
