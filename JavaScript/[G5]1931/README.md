## 📝 문제 요약

> N개의 회의(시작/끝 시간)가 주어질 때, 겹치지 않게 배정할 수 있는 최대 회의 개수를 구하는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - 그리디(Greedy) + 정렬
  - 끝나는 시간 기준 오름차순 정렬 후, 겹치지 않는 회의 순차 선택
- **시간/공간 복잡도:**
  - 시간: **O(n log n)** (정렬)
  - 공간: **O(n)** (회의 배열 저장)
- **핵심 로직:**
  1. 회의를 끝나는 시간 기준 정렬 (같으면 시작 시간 기준)
  2. 이전 회의 종료 시간 이후 시작하는 회의만 선택
  3. 선택한 회의 개수가 최대값

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const n = Number(input[0]);
  const meetings = [];

  for (let i = 1; i <= n; i++) {
    const [s, e] = input[i].split(" ").map(Number);
    meetings.push([s, e]);
  }

  meetings.sort((a, b) => (a[1] !== b[1] ? a[1] - b[1] : a[0] - b[0]));

  let count = 0;
  let end = 0;

  for (const [s, e] of meetings) {
    if (s >= end) {
      end = e;
      count++;
    }
  }

  console.log(count);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

**사고의 흐름: DP → Greedy로의 전환**

**[1차시도(시간초과)]**

- **접근:** “DP로 접근해보자”
  - 시작 시간 기준 내림차순 정렬
  - 각 회의마다 이전 회의들을 역순 탐색하며 누적 최댓값 계산
- **구현:**
  ```jsx
  ...
  for (let i = 1; i < n; i++) {
    for (let j = i - 1; j >= 0; j--) {
      if (scheduleArr[j].start >= scheduleArr[i].end) {
        maxNum[i] = maxNum[j] + 1;
        max = max > maxNum[i] ? max : maxNum[i];
        break;
      }
    }
  }
  ...
  ```
- **문제:** 이중 루프 → O(n^2) = 10^10 연산 → 시간초과

**[2차시도(틀렸습니다)]**

\*chatGPT에게 알고리즘 힌트를 받음

- **개선:** dp를 버리고 그리디로 변경

  - 끝나는 시간이 가장 빠른 것들을 그리디로 추가
    → 시간복잡도: O(n)

  ```jsx
  ...
  end = scheduleArr[0].end;
  count++;

  for (let i = 1; i < n; i++) {
    if (scheduleArr[i].start >= end) {
      end = scheduleArr[i].end;
      count++;
    }
  }
  ...

  ```

- **문제**: 입력이 정렬되어 있다는 보장 없음

  - 반례

    ```jsx
    // 입력
    3
    3 4
    1 2
    2 3

    // 예상 출력
    2

    // 실제 출력
    1
    ```

**[3차시도(맞았습니다!!)]**

- **개선 :** 끝나는 시간 기준 정렬 추가
  ⇒ 시간복잡도 O(nlogn) = 5 \* 10^5
  ```jsx
  ...
  scheduleArr.sort((a, b) =>
    a.end !== b.end ? a.end - b.end : a.start - b.start
  );
  ...
  ```

**[4차시도(리팩토링 - 맞았습니다!!)]**

- **개선**

  1. 객체 → 튜플(배열): `{start, end}` → `[s, e]`

     \*메모리 오버헤드 감소 (객체 프로퍼티 메타데이터 제거)

  2. 초기값 처리 간결화: `end = 0` 으로 첫 회의도 루프에서 처리
  3. 변수명 명확화: `scheduleArr` → `meetings`

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- DP는 “중복 부분 문제”가 있을 때 유효
- 그리디의 전제조건은 “정렬” 이 있어야 함!

- **DP vs Greedy 판단 기준**
  - DP: 이전 상태들의 조합으로 최적해 구성
  - Greedy: 매 순간 최선의 선택이 전역 최적해 보장

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- DP와 Greedy의 다양한 문제 접하고, 알고리즘을 단번에 파악할 수 있는 능력 기르기
