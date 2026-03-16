## 📝 문제 요약

> N×N 지형에서 가로/세로 각 줄이 활주로로 가능한지 판별하는 문제이다.  
> 높이 차가 1인 구간은 길이 X 경사로를 설치해 연결할 수 있고, 경사로는 겹치면 안 된다.  
> 결국 **각 행/열을 독립적으로 검사**해서 가능한 줄의 개수를 세면 된다.

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** 시뮬레이션(구현), 1차원 라인 검사
- **시간/공간 복잡도:**
  - 시간: O(N²)  
    (2N개 라인 × 각 라인 최대 N칸 검사)
  - 공간: O(N)  
    (라인 복사용 `line[]`)
- **핵심 로직:**
  1. 각 행/열을 `line[]`으로 만들어 같은 검사 함수(`canBuild`)에 넣는다.
  2. 인접 높이 차(`diff`)를 기준으로 평지/오르막/내리막/실패를 분기한다.
  3. 오르막은 직전 평지 길이(`flat`)가 X 이상이어야 하고, 내리막은 앞으로 X칸이 모두 같은 높이여야 한다.
  4. 내리막 설치 후 `i += X - 1`로 경사로 구간을 건너뛰어 겹침을 방지한다.

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- 행/열을 동일한 함수로 처리한 부분

```java
static void play() {
  int[] line = new int[N];

  // 행 검사
  for (int row = 0; row < N; row++) {
    for (int col = 0; col < N; col++) {
      line[col] = map[row][col];
    }
    if (canBuild(line))
      ans++;
  }

  // 열 검사
  for (int col = 0; col < N; col++) {
    for (int row = 0; row < N; row++) {
      line[row] = map[row][col];
    }
    if (canBuild(line))
      ans++;
  }
}
```

- 최종 라인 검사 함수 (`line[]` 버전)

```java
static boolean canBuild(int[] line) {
  int flat = 1;

  for (int i = 1; i < N; i++) {
    int diff = line[i] - line[i - 1];

    if (diff == 0) {
      flat++;
    } else if (diff == 1) { // 오르막
      if (flat < X)
        return false;
      flat = 1;
    } else if (diff == -1) { // 내리막
      int nextH = line[i];
      for (int k = 0; k < X; k++) {
        int idx = i + k;
        if (idx >= N || line[idx] != nextH)
          return false;
      }
      i += X - 1; // 경사로 구간 스킵
      flat = 0;
    } else {
      return false;
    }
  }

  return true;
}
```

- 초기 구현에서 사용했던 체크 방식(비교용)

```java
static boolean check(int row, int col, boolean isHorizontal, boolean[] visited) {
  int frontH = isHorizontal ? map[row][col - 1] : map[row - 1][col];
  int backH = map[row][col];
  int diff = frontH - backH;

  if (diff > 1 || diff < -1)
    return false;

  if (isHorizontal) {
    if (diff == 1) {
      for (int i = 0; i < X; i++) {
        if (col + i >= N || visited[col + i] || map[row][col + i] != backH)
          return false;
        visited[col + i] = true;
      }
    } else if (diff == -1) {
      for (int i = 1; i <= X; i++) {
        if (col - i < 0 || visited[col - i] || map[row][col - i] != frontH)
          return false;
        visited[col - i] = true;
      }
    }
  } else {
    if (diff == 1) {
      for (int i = 0; i < X; i++) {
        if (row + i >= N || visited[row + i] || map[row + i][col] != backH)
          return false;
        visited[row + i] = true;
      }
    } else if (diff == -1) {
      for (int i = 1; i <= X; i++) {
        if (row - i < 0 || visited[row - i] || map[row - i][col] != frontH)
          return false;
        visited[row - i] = true;
      }
    }
  }

  return true;
}
```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 처음에는 행/열을 따로 분기하며 `check`를 호출해서 구현했는데, 같은 패턴이 반복되어 실수 가능성이 있었다.
- 경사로 겹침 처리에서 `visited[]` 인덱스를 잘못 다루면 오답이 나기 쉬웠다.
- 이후 `line[] + flat 카운트` 방식으로 바꾸면서 분기 수가 줄어 디버깅이 훨씬 쉬워졌다.

### 🌱 3-2. 새롭게 알게 된 점 (Learning Points)

- 2차원 구현 문제도 **1차원 라인 검증 함수**로 추상화하면 코드가 단순해진다.
- 내리막 처리에서 인덱스를 스킵하면( `i += X - 1` ) 겹침 제약을 자연스럽게 만족시킬 수 있다.
- 구현 문제는 정답을 맞춘 뒤, 상태 변수 개수를 줄이는 리팩토링이 품질 향상에 매우 효과적이다.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- `visited[]` 방식과 `flat` 방식 중, 다른 경사로 문제에서도 더 일반화하기 좋은 패턴은 무엇일까?
- 같은 문제를 함수형 분리(입력/검증/출력)까지 더 엄격히 해서 템플릿화해보고 싶다.
- 다음 목표: 유사 구현 문제를 3개 더 풀면서, 라인 스캔 패턴을 공통 템플릿으로 정리하기.
