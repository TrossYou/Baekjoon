package SWEA.N_5658;

import java.util.*;
import java.io.*;

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int K = Integer.parseInt(st.nextToken());

      int length = N / 4; // 한 칸의 길이

      String str = br.readLine();
      str += str; // 2배로 만들어서 나누기 연산 제거

      Set<String> set = new HashSet<>(); // 만들어진 수 넣기

      for (int i = 0; i < N; i++) {
        set.add(str.substring(i, i + length));
      }

      Integer[] arr = new Integer[set.size()];
      int i = 0;
      for (String s : set) {
        arr[i++] = Integer.parseInt(s, 16);
      }
      Arrays.sort(arr, Comparator.reverseOrder());

      sb.append('#').append(tc).append(' ').append(arr[K - 1]).append('\n');
    }
    System.out.print(sb);
  }
}
