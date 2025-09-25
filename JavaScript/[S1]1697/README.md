## 📝 문제 요약

> 시작점 n에서 목표 k까지 이동. 가능한 이동은 x-1, x+1, x\*2. 최소 이동 횟수(최단거리)를 구하여라.
> 범위: 0 ≤ n,k ≤ 100,000

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - **초반 아이디어:** Set으로 이전에 계산한 수는 중복되지 않게 저장하며 count를 늘려가며 진행
  - **리팩토링:** BFS + 거리배열(dist) + 배열 큐(head/tail), Int32Array로 최적화
- **시간/공간 복잡도:**
  - 시간복잡도: O(MAX), MAX=100000
  - 공간복잡도: O(MAX) (dist, 큐)
- **핵심 로직:**
  1. dist를 -1로 초기화하고 시작점 n의 dist[n] = 0으로 설정과 큐에 n 삽입
  2. 큐에서 꺼낸 x의 가능한 x-1, x+1, x\*2가 범위 내이고, 미방문좌표이면, dist갱신 후 큐에 삽입
  3. k를 만나면 해당 dist 출력

---

## ✨ 2. 나의 최종 코드

- 코드 보기
  ```jsx
  const fs = require("fs");
  const [n, k] = fs
    .readFileSync("/dev/stdin")
    .toString()
    .trim()
    .split(" ")
    .map(Number);

  const MAX = 100000;
  const dist = new Int32Array(MAX + 1).fill(-1);

  const que = new Int32Array(MAX + 1);
  let head = 0;
  let tail = 0;

  que[tail++] = n;
  dist[n] = 0;

  while (head < tail) {
    const num = que[head++];
    const numDist = dist[num];
    if (k === num) break;

    if (num - 1 >= 0 && dist[num - 1] === -1) {
      que[tail++] = num - 1;
      dist[num - 1] = numDist + 1;
    }
    if (num + 1 <= MAX && dist[num + 1] === -1) {
      que[tail++] = num + 1;
      dist[num + 1] = numDist + 1;
    }
    if (num * 2 <= MAX && dist[num * 2] === -1) {
      que[tail++] = num * 2;
      dist[num * 2] = numDist + 1;
    }
  }

  console.log(dist[k]);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

![image.png](attachment:4b782cd4-f9c8-41ae-99ea-19772e97ccab:image.png)

- **1차시도(틀렸습니다!)**
  - **접근:** 레벨별 상태를 `Map`에 저장(`collectMap`), 각 레벨 후보는 중복되지 않게 `Set`에 모음
  - **문제**: 후보가 기하급수적으로 커져 메모리/시간 급증. 방문 체크 부재로 중복 심함
    - 반례는 모르겠지만,,, 100,000 0 을 입력했을 때,,,
      100,000이 나와야 하지만 `set`이 너무 커짐.
          → 굳이 그동안의 기록을 저장해 둘 필요가 없다.

          → 전체도 `set`으로 해서,, 이전에 나온 숫자는 더 이상 계산할 필요가 없음(무조건 더 많이 걸림) ⇒ 지역 `set`, 누적 `set` 두가지로,,
- **2차시도(틀렸습니다)**

  - **개선:** `accumSet`을 만들어서, 중복 추가를 방지함
  - **발견한 오류:** `100000 0` 을 입력했을 때, `Set maximum size exceeded` 에러 발생
    **→ 해결 방법:** 계산 시, 0과 100000 범위 체크 추가

- **3차시도(틀렸습니다)**

  - **개선:** `0 ≤ nx ≤ 100000` 범위 체크 추가
    ⇒ 크기와 시간 대폭 줄임
    ```jsx
    if (!accumSet.has(num - 1) && num - 1 >= 0) stepSet.add(num - 1);
    if (!accumSet.has(num + 1) && num + 1 <= 100000) stepSet.add(num + 1);
    if (!accumSet.has(num * 2) && num * 1 <= 100000) stepSet.add(num * 2);
    ```
  - **발견한 오류:** `n === k` 처리 누락
  - **교훈**: 엣지 케이스 주의!!!

- **4회차(맞았습니다!!)**

  - **개선**: `n === k`인 상황을 초반에 검사해서 0을 반환함

  ```jsx
  if (n === k) {
    console.log(0);
    isSeek = true;
  }
  ```

- **5회차(런타임 에러)**
  - 단순 오타
- **6차(맞았습니다!!) - 리팩토링**
  - **개선:** 표준 `BFS`로 전환
    - **dist 배열**(-1=미방문)을 방문/거리 겸용으로 사용
    - `Int32Array` 큐 + `head`/`tail`로 `O(1)` `enqueue`/`dequeue`
    - 각 상태를 최대 1번만 큐에 넣음 → 메모리/시간 모두 안정적
- **추가 최적화 아이디어**
  - x ≥ k 이면, 정답은 x - k(뒤로만 가면 됨)
  - x > k 이면, x+1, x\*2 계산은 하지 않음.(가지치기) → 평균시간 단축

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **Set/Map 한계와 사용**
  - Set은 인덱스/map메서드 없음 → 상태 관리엔 배열 + 인덱스가 유리
- **Set을 배열로 바꾸는 방법**

  ```jsx
  Array.from(step); // 또는 [...set]
  ```

- **일반 배열과 Int32Array 비교**

  - Array: 가변길이, 임의 타입 혼합 가능, “구멍”허용
  - Int32Array: 고정길이일 때, 더 최적화

- **BFS 구현 팁**
  - dist 활용
  - 큐를 배열 + head/tail로 구현

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 평균 시간 더 줄이는 휴리스틱(가지치기) 적용해보기
- 더 많은 BFS 문제 풀기
