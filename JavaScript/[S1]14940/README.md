## 📝 문제 요약

> nxm 지도에서 목표지점(2)로부터 모든 지점까지의 최단거리를 구하는 문제

**0**: 갈 수 없는 땅(벽) → 결과 0
**1**: 갈 수 있는 땅 → 목표지점으로부터의 거리 또는 -1(도달 불가 시)
**2**: 목표지점 → 결과 0

>

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** BFS(너비 우선 탐색) + Queue
- **시간/공간 복잡도:**
  - 시간 복잡도: O(nm)
  - 공간 복잡도: O(nm)
- **핵심 로직:**
  1. 목표지점(2)를 찾아서 BFS 시작점으로 설정
  2. BFS로 갈 수 있는 땅(1)만 탐색하며 거리 계산
  3. 초기화 시 0은 미리 0으로, 나머지는 -1로 설정하여 도달 불가능한 땅 구분
  4. visited 배열로 중복 방문

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const [n, m] = input[0].split(" ").map(Number);
  const board = input.slice(1).map((line) => line.split(" ").map(Number));

  const result = Array.from({ length: n }, (_, i) =>
    Array.from({ length: m }, (_, j) => (board[i][j] === 0 ? 0 : -1))
  );

  const visited = Array.from({ length: n }, () => Array(m).fill(false));

  class Queue {
    constructor(cap) {
      this.x = new Int32Array(cap);
      this.y = new Int32Array(cap);
      this.front = 0;
      this.rear = 0;
    }

    enqueue(ix, iy) {
      this.x[this.rear] = ix;
      this.y[this.rear] = iy;
      this.rear++;
    }

    dequeue() {
      const ix = this.x[this.front];
      const iy = this.y[this.front];
      this.front++;
      return [ix, iy];
    }
  }

  const que = new Queue(n * m);

  const findStartPoint = () => {
    for (let i = 0; i < n; i++) {
      for (let j = 0; j < m; j++) {
        if (board[i][j] === 2) return [i, j];
      }
    }
    return [-1, -1];
  };

  const [x, y] = findStartPoint();

  if (x !== -1) {
    visited[x][y] = true;
    result[x][y] = 0;
    que.enqueue(x, y);

    const dx = [-1, 1, 0, 0];
    const dy = [0, 0, -1, 1];

    while (que.front < que.rear) {
      const [x, y] = que.dequeue();

      for (let d = 0; d < 4; d++) {
        const targetX = x + dx[d];
        const targetY = y + dy[d];

        if (targetX < 0 || targetY < 0 || targetX >= n || targetY >= m)
          continue;
        if (visited[targetX][targetY]) continue;
        if (board[targetX][targetY] !== 1) continue;

        visited[targetX][targetY] = true;
        result[targetX][targetY] = result[x][y] + 1;
        que.enqueue(targetX, targetY);
      }
    }
  }

  let out = "";
  for (let i = 0; i < n; i++) out += result[i].join(" ") + "\n";
  process.stdout.write(out);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

**[핵심 사고 과정]**

1. **알고리즘 선택 고민**
   - DP? 이전 연산 결과를 참고해서 누적하는 방식?
   - 아니면 재귀적 접근? → 이건 `DFS`..
   - `BFS`를 구현해보자!
2. **자료구조 구현**

   : JS로 `Queue` 구현 방법 고민

   - 블로그 참고: `Object` `storage` + `front`/`rear` 포인터를 사용하여 `Queue` `class` 구현
   - 문제 : `Object`는 문자열/`Symbol`만 키로 사용 가능
   - 개선: `Map`으로 변경
   - **추후 개선: `Int32Array` + 포인터 방식으로 메모리 최적화**

3. **중복 처리 최적화 고민**

   : `enqueue` 시, 이미 큐에 있지만 처리되지 않은 아이템이 중복으로 추가된다..

   - 방법1: `dequeue`에 검사 → 시간 복잡도 증가
   - 방법2: `checkIfInQueue` `Map` 추가 → 공간복잡도 증가
   - 결론: “어차피 `O(nm)` 공간복잡도에서 상수배는 큰 영향 없다. 시간 효율을 위해 `Map` 추가!”
   - **개선: visited ``2차함수로 구현**

4. **객체 참조 미스**

   “`checkIfIinQueue`”가 ㄷ으로 구현되었을 때, `{x, y}` 형태의 키가 제대로 작동하지 않음

   → `{x, y}` 객체는 매번 새로운 참조값 생성

   **⇒ `“x,y”` 형태의 문자열 키로 변경**

**[주요 오류들]**

1. **재귀 호출로 인한 StackSizeExceed**
   - 초기에 `DFS` 방식으로 재귀 호출 → `BFS` + `while` 문으로 변경
2. **객체를 `Map` 키로 사용하는 문제**
   - `{x, y}` 객체는 매번 새로운 참조하여 키값으로 부적합 → 문자열 `“x,y”` 형태로 변경
3. **틀렸습니다: 0 영역 처리 누락**

   → `result` 초기화 시 0은 미리 0으로 설정

4. **콤마 연산자 실수**
   - `visited[(x,y)] = true → visited[x][y] = true;`

---

**[시행착오와 해결 과정]**

![image.png](attachment:d7659eec-6c82-4f93-84c9-0c4913aff038:image.png)

- **1차(런타임 에러)**: 재귀호출
  **→ 재귀호출을 수정해봄.(잘못함)**
- **2차(런타임 에러)**: 재귀, Map 키 문제
  **→ 재귀호출(`DFS`)를 BFS`로` 변경. `Map`을 문자열 키로 수정**
- **3,4차(틀렸습니다):** 0 영역 누락
  → 반례 찾아, 로직 수정

  ```jsx
  // 반례!
  //입력
  3 3
  2 0 1
  0 0 1
  1 1 0

  //예상 출력      //나의 출력
  0 0 -1         0 0 -1
  0 0 -1         0 0 -1
  -1 -1 0       -1 -1 -1
  ```

- **5차(맞았습니다!)**: `fillZero` 함수 추가하여, 값이 0이지만 반영되지 않은 값 0으로 설정
- **6차: 리팩토링 :** 성능 최적화 + 가독성 개선

---

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **Array.from 활용법:**
  ```jsx
  // 2차원 배열 초기화 + 조건부 값 설정
  const result = Array.from({ length: n }, (_, i) =>
    Array.from({ length: m }, (_, j) => (board[i][j] === 0 ? 0 : -1))
  );
  ```
- **자료구조 선택의 중요성:**
  - `2D 배열` vs `Map`: 격자 문제에서는 2D 배열이 캐시 친화적이고 상수 비용이 작음
  - `Queue` 구현: `Int32Array` + `head`/`tail` 방식이 메모리 효율적
- **출력 최적화:**
  - `console.log` 반복 호출보다 `process.stdout.write`로 한 번에 출력이 빠름
- **BFS vs DFS:**
  - 최단거리 문제에서 `BFS`가 적절
  - 재귀 호출 대신 `while` + `queue`로 스택 오버플로우 방지

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 다양한 알고리즘, 자료구조 더 공부하기!!!
  문제 난이도에 비해 너무 많은 시간이 들어갔다ㅠㅠ
