## 📝 문제 요약

> NxM 보드에서 임의의 8x8 부분 보드를 잘라냈을 때, 체스판 규칙(`BW` 교차)대로 칠하려면 최소 몇 칸을 다시 칠해야 하는지 구하는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** Brute-force(완전 탐색), 2차원 배열 구현
- **시간/공간 복잡도:**
  - **시간 복잡도**: O((N-7) x (M-7) x 8 x 8) = O(NM)
  - **공간 복잡도:** O(NM)
- **핵심 로직:**
  1. 주어진 보드 전체에서 **모든 8x8 구간**을 탐색
  2. 해당 구간을 “W 시작 체스판”, “B 시작 체스판” 패턴과 비교
  3. 두 경우 더 작은 칸을 칠해야 하는 값을 선택
  4. 모든 구간을 검사한 뒤 전체 최소값을 출력

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const [N, M] = input[0].split(" ").map(Number);
  const board = input.slice(1).map((line) => line.split(""));

  const makePattern = (start) => {
    const pattern = Array.from({ length: 8 }, () => Array(8).fill(""));
    for (let i = 0; i < 8; i++) {
      for (let j = 0; j < 8; j++) {
        if ((i + j) % 2 === 0) {
          pattern[i][j] = start;
        } else {
          pattern[i][j] = start === "B" ? "W" : "B";
        }
      }
    }
    return pattern;
  };

  const patternB = makePattern("B");
  const patternW = makePattern("W");

  const countDiff = (x, y, pattern) => {
    let diff = 0;
    for (let i = 0; i < 8; i++) {
      for (let j = 0; j < 8; j++) {
        if (board[x + i][y + j] !== pattern[i][j]) diff++;
      }
    }
    return diff;
  };

  let minCount = Infinity;
  for (let i = 0; i <= N - 8; i++) {
    for (let j = 0; j <= M - 8; j++) {
      const diffB = countDiff(i, j, patternB);
      const diffW = countDiff(i, j, patternW);
      minCount = Math.min(minCount, diffB, diffW);
    }
  }

  console.log(minCount);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

![image.png](attachment:e5ec4d69-86eb-4901-9b7a-e1ed034c0c08:image.png)

- **0회차(제출 전)**
  - 하나의 색을 Pivot(예: `“B”`)으로만 잡고 계산했으나, 경우에 따라 `“W”` 기준일 때 최소값이 나올 수 있음을 테스트로 알게 됨
    **⇒ 두 상황을 모두 비교해 봐야 함**
- **1회차(맞았습니다!!)**

  ```jsx
  const minMap = new Map();
  minMap.set("x", 51);
  minMap.set("y", 51);
  minMap.set("count", 65);

  const countFix = function (a, b) {
    let minCount = 65;
    for (let step = 1; step < 3; step++) {
      const pivot = step === 1 ? "B" : "W";
      ...
      if (!((row + col) % 2)) {
            if (pivot !== inputArr[x][y]) count++;
          } else {
            if (pivot === inputArr[x][y]) count++;
          }
        }
      }
      ...
    }
    return minCount;
  };
  ```

  - 공부한 `Map` 을 일부러 활용했지만, 단순히 최소값만 저장하기 때문에 필요 이상으로 복잡해짐
  - 2차원 배열 처리도 직관적이지 않아 리팩토링 필요성을 느낌
  - 좌우 2의 배수 칸 차이날 때, `pivot`과의 비교로 개수를 셈

- **2회차(맞았습니다!!)**
  - `Array.from`을 사용하여 깔끔하게 2차원 배열 생성
  - 채스판 패턴(`patternB`, `patternW`)을 사전에 정의해 코드 가독성 높임
  - 최소값을 `Infinity`로 초기화하고 `Math.min()`을 사용하여 단순화

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **입력 처리 단축**

  ```jsx
  //이전
  const nNm = input[0].split(" ");
  const N = Number(nNm[0]);
  const M = Number(nNm[1]);

  //이후
  const [N, M] = input[0].split(" ").map(Number);
  ```

  ```jsx
  // 이전
  const inputArr = new Array(N);

  for (let i = 0; i < N; i++) {
    inputArr[i] = input[i + 1].split("");
  }

  //이후
  const board = input.slice(1).map((line) => line.split(""));
  ```

- **이차원 배열 생성**
  ```jsx
  const pattern = Array.from({ length: 8 }, () => Array(8).fill(""));
  ```
  - `Array.from(…, () ⇒ …)` : 매번 다른 배열을 새로 생성
- **최소값 초기화**

  ```jsx
  //이전
  let minCount = 65; //문제 상황 상 가능 한 가장 큰 수 +1

  //이후
  let minCount = Infinity;
  ```

  ```jsx
  minCount = Math.min(minCount, diffB, diffW);
  ```

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- `Array.fill` 의 참조 문제
