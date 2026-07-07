<aside>

## 📘 학습한 내용

- 단순 DFS의 지수 시간 복잡도 **O(4^(MxN))**
- DP(메모이제이션)를 활용한 시간 복잡도 최적화 **O(MxN)**
- DP 배열 초기화 시 `-1`(미방문)과 `0`(경로 없음)의 상태 구분
  → 0으로 초기화 시 경로 없는 길을 재탐색하며 시간 up

</aside>

## 📝 문제 요약

> - **목표**: M x N 지도에서 `(0,0)`부터 `(M-1, N-1)`까지 이동하는 가능한 모든 경로의 수 구하기
> - 이동 조건: 상하좌우 인접한 칸 중, 현재 위치보다 **높이가 낮은 칸**으로만 이동 가능

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** DFS + DP
- **시간/공간 복잡도:**
  - 시간: O(M x N )
  - 공간: O(M x N)
- **핵심 로직:**
  1. **상태 정의**: `dp[r][c]` = `(r, c)`에서 도착점까지 도달하는 경로의 총 개수
  2. **초기화**: 배열 전체를 `-1`로 초기화하여 ‘미방문 상태’ 명시
  3. **메모이제이션 (Top-Down)**: `dp[r][c] ≠ -1`일 경우, 재탐색 없이 즉시 값 반환
  4. **탐색 및 누적:** 4방향 탐색 진행 → 내리막길이면 재귀 호출(`play`) → 반환된 경로 수를 현재 DP 값에 합산

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- **메인 함수 (입력 처리 및 실행)**
  : DP 배열을 `-1`(미방문)로 초기화하고, 탐색을 시작함.

  ```java
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken());
    N = Integer.parseInt(st.nextToken());

    map = new int[M][N];
    dp = new int[M][N];

    // dp 초기화: 미방문(-1)과 방문했으나 경로없음(0)을 구분하기 위함
    for (int i = 0; i < M; i++) {
      Arrays.fill(dp[i], -1);
    }

    // 지도 높이 입력 받기
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    // (0, 0)에서 탐색 시작 및 결과 출력
    System.out.println(play(0, 0));
  }
  ```

- **Top-Down DP 핵심 로직 (재귀 + 메모이제이션)**
  : 중복 계산을 막고, 조건(내리막길)에 맞는 경로만 누적

  ```java
  static int play(int r, int c) {
    // 1. Base Case: 목적지 도달 시 경로 1개 추가
    if (r == M - 1 && c == N - 1) {
      return 1;
    }

    // 2. Memoization: 이미 계산된 칸은 재탐색 없이 즉시 반환
    if (dp[r][c] != -1) {
      return dp[r][c];
    }

    // 3. 탐색 및 경로 누적
    int v = map[r][c];
    dp[r][c] = 0; // 방문 처리 (이후부터 4방향에서 찾은 경로 수 누적)

    for (int dir = 0; dir < 4; dir++) {
      int nr = r + dr[dir];
      int nc = c + dc[dir];

      // 맵 범위를 벗어나지 않으면서 '내리막길'인 경우에만 이동
      if (nr >= 0 && nr < M && nc >= 0 && nc < N && map[nr][nc] < v) {
        dp[r][c] += play(nr, nc); // 재귀적으로 탐색한 결과를 현재 칸에 더함
      }
    }

    return dp[r][c];
  }
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **문제:** 단순 DFS 구현으로 인해 갈림길마다 연산이 중복되어 시간 초과(TLE)발생 **O(4^(MxN))**
- **해결:** 이미 탐색한 경로의 수를 기록하는 DP 배열을 추가하여 시간 복잡도를 **O(M x N)**으로 단축
- **주의점**: DP 배열을 `0`으로 초기화 시 ‘미방문’과 ‘막다른 길’이 구분되지 않아 중복 탐색이 발생함.
  → `-1`로 초기화하여 상태를 명확히 분리함

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- 겹치는 부분 문제가 발생하는 그래프 탐색에서는 DP도입이 필수적
- 2차원 배열에서 상하좌우 4방향 이동이 모두 가능한 경우, 반복문(Bottom-Up)보다 재귀(Top-Down) 방식이 의존성 처리에 훨씬 직관적임

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 높이 기준 내림차순 정렬을 활용한 Bottom-Up 방식의 DP 구현 및 성능 비교
