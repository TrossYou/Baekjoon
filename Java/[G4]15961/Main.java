import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken()); // N: 접시의 수
    int d = Integer.parseInt(st.nextToken()); // d: 초밥의 가지 수
    int k = Integer.parseInt(st.nextToken()); // k: 연속해서 먹는 접시의 수
    int c = Integer.parseInt(st.nextToken()); // c: 쿠폰번호

    int[] circleArr = new int[N];
    for (int i = 0; i < N; i++)
      circleArr[i] = Integer.parseInt(br.readLine());

    // 0부터 k-1부터 시작해서, k개의 set을 돌며 최대 개수

  }
}
