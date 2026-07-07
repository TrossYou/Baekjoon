<aside>

## 📘 학습한 내용

- **상태 정의의 전환 (State inversion)** : 배낭 문제(Knapsack) 유형에서 메모리 제한으로 인해 상태(indeex)를 그대로 사용할 수 없을 때, **제한 조건(비용)을 인덱스로 삼고, 구하고자 하는 값(메모리)를 DP 배열의 값으로 두어 최대화**하는 방식으로 문제 우회
- `voidRecur` → `intRecur` → `Top-down DP` → `Bottom-up DP` 로 알고리즘 최적화 과정 직접 구현으로 연습
</aside>

## 📝 문제 요약

> - **문제:** 백준 7579번 - 앱
> - **목표**: 활성화된 앱들을 종료하여 총 **M 바이트 이상의 메모리**를 확보하되, 앱을 종료할 때 발생하는 **비용의 합을 최소화** 하는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - 배낭 문제 (0-1 Knapsack), DP
- **시간/공간 복잡도:**
  - 시간: O(N x MAX_COST) (약 100 x 10,000 = 1,000,000)
  - 공간: O(MAX_COST)
- **핵심 로직:**
  1. **초기 접근(`voidRecur` / `inRecur`)**: 모든 앱에 대하여 ‘종료한다/종료하지 안는다’의 부분집합을 구하는 브루트포스 재귀 및 백트래킹 구현
  2. **`Top-down DP` 시도**: dp[idx][메모리]로 시도했으나, M이 최대 10,000,000이라 **메모리 초과** 발생
  3. **`Bottom-up 1D DP`**
     - `dp[c]` = 비용 `c`를 소모해서 얻을 수 있는 **최대 메모리**
     - 1차원 배열을 사용하여 뒤에서부터 순회하며 앱 중복 방지

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- [최종] 1D Bottom-up DP

  ```jsx
  // dp[c]: 비용 c로 확보할 수 있는 최대 메모리
  int MAX_COST = 10000; // 최대 앱 개수(100) * 최대 비용(100)
  dp = new int[MAX_COST + 1];

  for (int i = 0; i < N; i++) {
      int mem = arrM[i];
      int cost = arrC[i];

      // 같은 앱을 중복해서 끄지 않도록 역순 탐색 (0-1 Knapsack)
      for (int k = MAX_COST; k >= cost; k--) {
          dp[k] = Math.max(dp[k], mem + dp[k - cost]);
      }
  }

  // 확보한 메모리가 M 이상이 되는 최초의 비용(최솟값) 찾기
  for (int i = 0; i <= MAX_COST; i++) {
      if (dp[i] >= M) {
          System.out.println(i);
          break;
      }
  }
  ```

- voidRecur & intRecur

  ```jsx
  // 1. 전역 변수를 활용한 백트래킹 (Pruning)
  static void recur(int idx, int curM, int curC) {
      if (curM >= M) {
          ans = Math.min(ans, curC);
          return;
      }
      if (curC > ans || idx == N) return;

      recur(idx + 1, curM + arrM[idx], curC + arrC[idx]); // 넣기
      recur(idx + 1, curM, curC); // 안 넣기
  }

  // 2. 상태(State)에서 누적 비용(curC)을 없앤 순수 재귀
  static int intRecur(int idx, int leftM) {
      if (leftM <= 0) return 0;
      if (idx == N) return 100000000; // 불가능한 큰 값 반환

      int a = arrC[idx] + intRecur(idx + 1, leftM - arrM[idx]);
      int b = intRecur(idx + 1, leftM);

      return Math.min(a, b);
  }
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 처음에 직관적으로 `dp[index][남은 메모리]` 형태의 Top-down DP를 구상함.
  → 하지만 **N ≤ 100**이고 **M ≤ 10,000,000**이라, **100 x 10^7** 크기의 배열을 선언하면 4GB 가량 필요함
  ⇒ 인덱스를 메모리 → 비용 으로 수정하고, 1차원으로 계산

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **조건의 역발상:** 구하고자 하는 정답(최소 비용)을 무조건 DP의 값으로 넣을 필요는 없다. 제약 조건의 범위가 너무 크다면, 오히려 제약 조건을 DP의 값으로 두고 기준점(비용)을 인덱스로 활용하여 최대/최소를 판별할 수 있다.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 아직 `voidRecur` → `intRecur` → `DP` 로 넘어가는 흐름이 완전히 손에 익지 않음.
  비슷한 배낭 문제나 상태 전이 문제를 통해 `Top-down`과 `Bottom-up`을 자유롭게 오가는 연습 필요
  - [**BOJ 1520 - 내리막 길**](https://www.acmicpc.net/problem/1520)
    - **이유:** Top-down DP(메모이제이션)의 꽃이라 불리는 문제입니다. DFS(`intRecur`)에 DP를 섞지 않으면 무조건 시간 초과가 나기 때문에 재귀형 DP를 연습하기 좋습니다.
  - [\*\*BOJ 2293 - 동전 1](https://www.acmicpc.net/problem/2293) & [BOJ 2294 - 동전 2](https://www.acmicpc.net/problem/2294)\*\*
    - **이유:** 1차원 배열 최적화(`bottomup1d`)를 연습하기 가장 좋은 문제입니다. 앱 문제와 달리 같은 물건을 여러 번 쓸 수 있을 때의 반복문 방향 차이를 깨달을 수 있습니다.
