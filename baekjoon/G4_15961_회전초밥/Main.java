import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static int[] circleArr;
  private static int N, d, k, c;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken()); // N: 접시의 수
    d = Integer.parseInt(st.nextToken()); // d: 초밥의 가지 수
    k = Integer.parseInt(st.nextToken()); // k: 연속해서 먹는 접시의 수
    c = Integer.parseInt(st.nextToken()); // c: 쿠폰번호

    // 초밥 배열 받기 - 시간복잡도 O(N)
    circleArr = new int[2 * N]; // 원형배열을 만들기 위해 2N크기
    for (int i = 0; i < N; i++) {
      circleArr[i] = Integer.parseInt(br.readLine());
      if (i < k - 1)
        circleArr[N + i] = circleArr[i]; // 원형 배열
    }

    // for문을 돌며...슬라이딩 도어 구현
    int[] cntArr = new int[d + 1]; // i번 초밥이 몇 개 들어 있는가?
    // 쿠폰 초밥 세팅
    cntArr[c]++;
    int typeNum = 1;
    // 1) 초기 슬라이드 계산 - 시간복잡도 O(K)
    for (int i = 0; i < k; i++) {
      int sushiType = circleArr[i];
      if (++cntArr[sushiType] == 1)
        typeNum++; // 없던 종류라면 타입 수++
    }
    int maxNum = typeNum;

    // 2) 슬라이딩도어 적용 - 시간복잡도 O(K)
    for (int i = 1; i < N; i++) { // i: 시작 인덱스
      int minusType = circleArr[i - 1];
      int plusType = circleArr[i + k - 1];

      if (--cntArr[minusType] == 0)
        typeNum--;
      if (++cntArr[plusType] == 1)
        typeNum++;
      if (maxNum < typeNum)
        maxNum = typeNum;
      if (maxNum == k + 1)
        break; // 최대값이 나오면 조기 종료
    }

    System.out.println(maxNum);
  }
}
