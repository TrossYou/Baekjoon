import java.io.*;
import java.util.StringTokenizer;

public class Solution {
  static int N, B, ans;
  static int[] heights;
  static boolean[] included;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      B = Integer.parseInt(st.nextToken());
      ans = Integer.MAX_VALUE;

      heights = new int[N];
      included = new boolean[N];
      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++) {
        heights[i] = Integer.parseInt(st.nextToken());
      }

      combine(0, 0);
      sb.append('#').append(tc).append(' ').append(ans).append('\n');
    }
    System.out.print(sb);
  }

  static void combine(int idx, int sum) {
    // 기저조건: 지금까지의 sum이 B이상이 되는 경우
    if (sum >= B) {
      ans = Math.min(ans, sum - B);
      return;
    }

    for (int i = idx; i < N; i++) {
      if (included[i])
        continue;

      included[i] = true;
      combine(i + 1, sum + heights[i]);
      included[i] = false;
    }
  }
}
