<aside>

### 📘 **학습한 내용**

**0-1 Knapsack(배낭)** 문제 해결.
완전탐색(백트래킹) ➔ Top-down DP ➔ Bottom-up DP (1D 최적화) 5단계 진화 과정 구현.

</aside>

## 📝 문제 요약

- **목적:** 제한된 무게(K) 내에서 가치 합이 최대가 되도록 물건 선택.
- **조건:** 각 물건은 1개뿐이며 쪼갤 수 없음 (0-1).
- **입력:** 물건 개수 N, 배낭 용량 K, 각 물건의 부피(V)와 가치(C).

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘:** 완전탐색(백트래킹) ➔ DP
- **시간 복잡도:** O(NK) (최종 DP 기준)
- **공간 복잡도:** O(K) (1D 배열 최적화 기준)
- **핵심 로직:**
  1. **완전탐색:** '넣는다 vs 안 넣는다'로 분기하여 모든 경우 확인.
  2. **Top-Down:** 재귀 인자(`idx`, `curV`)로 2D 배열 생성. 방문 노드 저장해 중복 제거.
  3. **Bottom-Up:** 재귀 제거. 무게 0~K까지 점화식으로 표 채우기.
  4. **공간 최적화:** 직전 상태만 필요한 점을 활용해 1D 배열로 압축. **역순 탐색**으로 중복 선택 방지.

---

## ✨ 2. 나의 최종 코드

**핵심 구현 코드 (전체 5단계 과정 통합)**

```java
import java.util.*;
import java.io.*;

public class Solution {
  static int[][] products;
  static int[][] dp;
  static int[] dp2;
  static int N, K, ans;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      ans = 0;
      products = new int[N][2];
      dp = new int[N][K + 1];
      dp2 = new int[K + 1]; // 무게 K에서 최대 가치

      // 입력 받기
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(br.readLine());
        products[i][0] = Integer.parseInt(st.nextToken()); // 부피
        products[i][1] = Integer.parseInt(st.nextToken()); // 가치
      }

      // 최적화된 1D Bottom-Up 실행
      bottomUpDp2();
      ans = dp2[K];

      sb.append('#').append(tc).append(' ').append(ans).append('\\n');
    }
    System.out.print(sb);
  }

  // 1. 완전탐색 (void) : 매개변수로 정답 갱신
  static void voidRecur(int idx, int curV, int curC) {
    if (curV > K) return;
    if (idx == N) {
      ans = Math.max(ans, curC);
      return;
    }
    voidRecur(idx + 1, curV + products[idx][0], curC + products[idx][1]); // 넣기
    voidRecur(idx + 1, curV, curC); // 안 넣기
  }

  // 2. 완전탐색 (int) : 하위 문제 가치 반환
  static int intRecur(int idx, int curV) {
    if (curV > K) return -100000000; // 패널티
    if (idx == N) return 0;

    int a = products[idx][1] + intRecur(idx + 1, curV + products[idx][0]);
    int b = intRecur(idx + 1, curV);
    return Math.max(a, b);
  }

  // 3. Top-Down DP : 중복 방지 (Memoization)
  static int UpdownDp(int idx, int curV) {
    if (curV > K) return -100000000;
    if (idx == N) return 0;
    if (dp[idx][curV] != -1) return dp[idx][curV];

    int a = products[idx][1] + UpdownDp(idx + 1, curV + products[idx][0]);
    int b = UpdownDp(idx + 1, curV);
    return dp[idx][curV] = Math.max(a, b);
  }

  // 4. Bottom-Up DP (2D Array)
  static void bottomUpDP() {
    int firstV = products[0][0];
    int firstC = products[0][1];
    for (int k = 0; k <= K; k++) {
      if (k >= firstV) dp[0][k] = firstC;
    }
    for (int i = 1; i < N; i++) {
      for (int j = 0; j <= K; j++) {
        int weight = products[i][0];
        int value = products[i][1];
        if (weight > j) {
          dp[i][j] = dp[i - 1][j]; // 담을 수 없는 경우 이전 최적값 계승
        } else {
          dp[i][j] = Math.max(value + dp[i - 1][j - weight], dp[i - 1][j]);
        }
      }
    }
  }

  // 5. Bottom-Up DP (1D Array 공간 최적화)
  static void bottomUpDp2() {
    for (int i = 0; i < N; i++) {
      int weight = products[i][0];
      int value = products[i][1];

      // 역순 탐색을 통해 물건 중복 선택 방지
      for (int k = K; k >= weight; k--) {
        dp2[k] = Math.max(dp2[k], dp2[k - weight] + value);
      }
    }
  }
}
```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **오류 1:** Bottom-Up 2D 배열 작성 시 반복문을 `k = weight`부터 시작.
  - **해결:** 담을 수 없는 작은 무게(`k < weight`)의 경우 이전 행 값을 물려받지 못함. `0`부터 `K`까지 탐색하고 `if-else` 분기 처리로 해결.
- **오류 2:** Top-Down 재귀에서 `curV > K` 시 `Integer.MIN_VALUE` 반환.
  - **해결:** 물건 가치를 더할 때 언더플로우 발생 위험. 안전한 음수(`-100000000`)로 패널티 부여 방식 수정.

### 🌱 3-2. 새롭게 알게 된 점 (Learning Points)

- **점화식 도출법:** 백트래킹만 제대로 구현하면 DP는 알아서 따라온다.
- **Top-Down 전환:** 재귀 인자가 DP 배열의 차원. `if (dp != -1)`와 `return dp = ...` 딱 두 줄만 추가.
- **1D 배열 최적화:** 2D ➔ 1D 압축 시, 한 물건의 중복 선택을 막으려면 `반드시 역순 탐색(k--)`해야 함.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- **그리디(Greedy):** 물건을 쪼갤 수 있는 **Fractional Knapsack** 문제 풀이 및 차이점 비교.
- **무한 냅색:** 물건 개수가 무한정인 동전 교환(**Coin Change**), **Unbounded Knapsack** 문제 정복.
