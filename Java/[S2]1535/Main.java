import java.io.*;
import java.util.*;

public class Main {
  static int[] arrL, arrJ;
  static int N, ans;
  // static int[][] topDowndp2D;
  // static int[][] bottomUp2D;
  static int[] bottomUp1D;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    N = Integer.parseInt(br.readLine());
    ans = 0;

    arrL = new int[N]; // 체력 배열
    arrJ = new int[N]; // 기쁨 배열
    // topDowndp2D = new int[N][110]; // 각 인덱스에서 에너지에 따른 기쁨 최대값
    // bottomUp2D = new int[N][110];
    bottomUp1D = new int[110]; // 각 체력일 때 최대 기쁨

    StringTokenizer st = new StringTokenizer(br.readLine());

    for (int i = 0; i < N; i++) {
      arrL[i] = Integer.parseInt(st.nextToken());
    }

    st = new StringTokenizer(br.readLine());

    for (int i = 0; i < N; i++) {
      arrJ[i] = Integer.parseInt(st.nextToken());
    }

    // voidRecur(0, 100, 0);

    // ans = intRecur(0, 100);

    // ans = topDowndp2D(0, 100);

    // bottomUp2D();
    // ans = bottomUp2D[N - 1][100];

    bottomUp1D();
    ans = bottomUp1D[100];
    System.out.println(ans);
  }

  // static void voidRecur(int i, int ene, int del) {
  // if (ene <= 0) // 남은 에너지가 0이하이면, 끝
  // return;
  // if (i == N) { // 끝까지 확인했다면, 최대 행복인지 비교
  // ans = Math.max(ans, del);
  // return;
  // }

  // // 인사를 한다
  // voidRecur(i + 1, ene - arrL[i], del + arrJ[i]);
  // // 인사를 안한다
  // voidRecur(i + 1, ene, del);
  // }

  // static int intRecur(int i, int ene) {
  // if (ene <= 0)
  // return -1000;
  // if (i == N)
  // return 0;

  // int a = arrJ[i] + intRecur(i + 1, ene - arrL[i]);
  // int b = intRecur(i + 1, ene);

  // return Math.max(a, b);
  // }

  // static int topDowndp2D(int i, int ene) {
  // if (ene <= 0)
  // return -1000;
  // if (i == N)
  // return 0;

  // if (topDowndp2D[i][ene] != 0)
  // return topDowndp2D[i][ene];

  // int a = arrJ[i] + topDowndp2D(i + 1, ene - arrL[i]);
  // int b = topDowndp2D(i + 1, ene);

  // return topDowndp2D[i][ene] = Math.max(a, b);
  // }

  // static void bottomUp2D() {
  // int firstE = arrL[0];
  // int firstD = arrJ[0];
  // for (int e = 0; e <= 100; e++) {
  // if (firstE < e) // 사용하는 에너지가 e보다 작을 때만 기쁨 채우기
  // bottomUp2D[0][e] = firstD;
  // }

  // for (int i = 1; i < N; i++) {
  // int ene = arrL[i];
  // int del = arrJ[i];

  // for (int e = 0; e <= 100; e++) {
  // // 넣을 수 있다면
  // if (e > ene)
  // bottomUp2D[i][e] = Math.max(del + bottomUp2D[i - 1][e - ene], bottomUp2D[i -
  // 1][e]);
  // else
  // bottomUp2D[i][e] = bottomUp2D[i - 1][e];
  // }
  // }
  // }

  static void bottomUp1D() {
    for (int i = 0; i < N; i++) {
      int ene = arrL[i];
      int del = arrJ[i];

      for (int e = 100; e > ene; e--) {
        bottomUp1D[e] = Math.max(bottomUp1D[e], del + bottomUp1D[e - ene]);
      }
    }
  }
}
