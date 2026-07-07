<aside>

## 📘 학습한 내용

- **브루트포스(재귀)** 를 이용한 모든 경우의 수 탐색(`void`, `int` 반환형 차이)
- 재귀에 **메모이제이션(Memoization)**을 결합한 Top-Down DP 구현
- **2차원 배열**을 이용한 Bottom-Up DP (배낭 문제의 정석)
- **1차원 배열**을 이용한 공간 복잡도 최적화 (뒤에서부터 채우는 방식)
</aside>

## 📝 문제 요약

> - **문제**: N명의 사람에게 인사를 해서 얻을 수 있는 최대 기쁨 구하기.
> - **제약**: 각 사람에게 인사할 때 잃는 체력 L과 얻는 기쁨 J가 정해져 있음.
>   초기 체력 L은 100 → 체력이 0이 되거나 그보다 낮아지면 죽으므로 **체력은 반드시 1이상 이어야 함**

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** 다이나믹 프로그래밍(DP), 0-1 배낭문제(Knapsack Problem)
- **시간/공간 복잡도:**
  - 시간: O(Nx100) (N은 사람 수, 100은 최대 체력)
  - 공간: O(100) (1차원 DP)
- **핵심 로직:**
  1. 각 사람을 선택하느냐 마느냐의 문제 → **0-1 Knapsack** 구조
  2. 체력이 0이 되면 안됨 → 실제 가용 체력은 99까지로 계산하거나 `e > ene` 조건 확인
  3. 1차원 DP 사용 시, 이전 물건의 결과가 중복 반영되지 않도록 **체력을 뒤(100)에서 앞(ene+1) 방향으로 스캔** 하는 것이 핵심.

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- **Top-Down DP (Memoization)**

  ```jsx
  static int topDowndp2D(int i, int ene) {
      if (ene <= 0) return -1000; // 체력 소진 시 유효하지 않은 값 반환
      if (i == N) return 0;

      if (topDowndp2D[i][ene] != 0) return topDowndp2D[i][ene];

      // 선택하는 경우 vs 선택하지 않는 경우 중 최댓값 저장
      int a = arrJ[i] + topDowndp2D(i + 1, ene - arrL[i]);
      int b = topDowndp2D(i + 1, ene);

      return topDowndp2D[i][ene] = Math.max(a, b);
  }
  ```

- **2차원 Bottom-Up DP**

  ```jsx
  static void bottomUp2D() {
      for (int i = 1; i < N; i++) {
          int ene = arrL[i];
          int del = arrJ[i];

          for (int e = 0; e <= 100; e++) {
              if (e > ene) // 현재 체력(e)으로 인사가 가능한 경우
                  bottomUp2D[i][e] = Math.max(del + bottomUp2D[i - 1][e - ene], bottomUp2D[i - 1][e]);
              else // 인사가 불가능한 경우 이전 상태 유지
                  bottomUp2D[i][e] = bottomUp2D[i - 1][e];
          }
      }
  }
  ```

- **1차원 Bottom-Up DP**

  ```jsx
  // 공간 복잡도를 O(W)로 최적화한 방식
  static void bottomUp1D() {
      for (int i = 0; i < N; i++) {
          int ene = arrL[i]; // 소모 체력
          int del = arrJ[i]; // 획득 기쁨

          // 뒤에서부터 채워야 '현재 단계'의 값이 '현재 단계'에 다시 영향을 주지 않음
          for (int e = 100; e > ene; e--) {
              bottomUp1D[e] = Math.max(bottomUp1D[e], del + bottomUp1D[e - ene]);
          }
      }
  }
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **체력 경계값 설정**: 처음에 `ene <= 0` 조건을 처리할 때, 기쁨이 최대가 되더라도 체력이 정확히 0이 되면 안 된다는 점을 간과함. 100에서 시작해 0 이하가 되는 순간을 유효하지 않은 값(`1000`)으로 리턴하여 해결함.
- **1차원 DP 순서**: 1차원 배열로 최적화할 때 앞에서부터 채우면 같은 사람과 여러 번 인사하는 효과(Unbounded Knapsack)가 발생함. 이를 방지하기 위해 `e`를 역순으로 순회하도록 수정함.

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- 단순 재귀(`voidRecur`)에서 결과값을 리턴하는 재귀(`intRecur`)로, 그리고 다시 `Memoization`을 입히는 과정에서 DP의 원리를 체계적으로 이해함.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)
