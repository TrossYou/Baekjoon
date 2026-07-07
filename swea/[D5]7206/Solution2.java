import java.io.*;

public class Solution2 {
  private static String N;
  private static int ans;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      N = br.readLine();
      ans = 0;
      play();
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void play() {
    for (int i = 1; i < N.length(); i++) {
      ans = Math.max(ans, getAns(N, i, 0));
    }
  }

  public static int getAns(String n, int cutIdx, int turn) {
    int front = Integer.parseInt(n.substring(0, cutIdx)); // 잘린 앞 부분
    int back = Integer.parseInt(n.substring(cutIdx)); // 잘린 뒷 부분
    int mul = front * back;
    int localTurn = turn + 1;

    if (mul >= 10) {
      String nextN = String.valueOf(mul);
      for (int i = 1; i < nextN.length(); i++) {
        localTurn = Math.max(localTurn, getAns(nextN, i, turn + 1));
      }
    }

    return localTurn;
  }
}
