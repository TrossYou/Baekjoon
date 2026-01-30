# 백준 2206번 - 벽 부수고 이동하기

## 📝 문제 요약

> N×M 맵에서 (1,1)에서 (N,M)까지 최단 경로로 이동. 0은 이동 가능, 1은 벽. **벽을 최대 1개까지 부수고 이동 가능**. 최단 거리를 구하여라.

> 범위: 1 ≤ N, M ≤ 1,000

**문제 링크:** https://www.acmicpc.net/problem/2206

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - **초반 아이디어:** BFS + 2차원 visited + poll() 후 visited 마킹
  - **리팩토링:** BFS + 3차원 visited[N][M][2] + add 전 visited 마킹 + ArrayDeque + 0-indexed

- **시간/공간 복잡도:**
  - 시간복잡도: O(N×M) - 각 위치를 최대 2번(벽 안 부숨/부숨) 방문
  - 공간복잡도: O(N×M) - visited 배열 + 큐

- **핵심 로직:**
  1. **visited를 3차원으로**: `visited[x][y][0]` = 벽 안 부수고 도달, `visited[x][y][1]` = 벽 부수고 도달
  2. **큐에 넣기 전에 visited 체크 및 마킹** → 중복 방지로 메모리 초과 해결
  3. 다음 칸이 빈 칸이면 이동, 벽이면 아직 안 부쉤을 때만 벽 부수고 이동
  4. 목적지 도달 시 즉시 return (BFS는 최단 거리 보장)

---

## ✨ 2. 나의 최종 코드

<details>
<summary>코드 보기</summary>

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int x, y, dist;
        boolean isSmashed;

        public Node(int x, int y, int dist, boolean isSmashed) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.isSmashed = isSmashed;
        }
    }

    static boolean[][] map;
    static int N, M;
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j) == '0';
            }
        }
        System.out.println(bfs());
    }

    static int bfs() {
        boolean[][][] visited = new boolean[N][M][2];
        Queue<Node> q = new ArrayDeque<>();
        visited[0][0][0] = true;
        q.add(new Node(0, 0, 1, false));

        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.x == N - 1 && node.y == M - 1) {
                return node.dist;
            }
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= M || visited[nx][ny][node.isSmashed ? 1 : 0])
                    continue;

                if (map[nx][ny]) { // 이동 가능
                    visited[nx][ny][node.isSmashed ? 1 : 0] = true;
                    q.add(new Node(nx, ny, node.dist + 1, node.isSmashed));
                } else {
                    if (!node.isSmashed) {
                        visited[nx][ny][1] = true;
                        q.add(new Node(nx, ny, node.dist + 1, true));
                    }
                }
            }
        }
        return -1;
    }
}
```

</details>

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **1차 시도 (메모리 초과)**

  - **접근:** 2차원 visited, poll() 후 visited 마킹
  
  - **문제:** 같은 위치가 큐에 중복으로 들어감
    - `visited[nx][ny]`를 큐에서 꺼낸 **후에** 체크하므로
    - 아직 poll되지 않은 위치는 visited가 false → 중복 add!
    - 예: `(2,2)`가 여러 경로에서 큐에 10번, 100번 들어감
    - 큐 크기가 기하급수적으로 증가 → **메모리 초과**
  
  - **발견:** "왜 add 직전에 마킹하는가?"에 대한 이해 부족

- **2차 시도 (여전히 메모리 초과)**

  - **개선:** add 전에 visited 체크로 변경
  
  - **문제:** 2차원 visited로는 상태 구분 불가
    - 같은 위치 `(3,3)`에 두 가지 방법으로 도달:
      - 경로 A: 벽 안 부숨 (아직 부술 기회 있음)
      - 경로 B: 벽 부숨 (기회 소진)
    - 경로 B가 먼저 도달 → `visited[3][3] = true`
    - 경로 A는 차단됨 → **하지만 경로 A가 더 유리할 수 있음!**
  
  - **깨달음:** 같은 위치여도 "벽 부술 기회 남았는가"에 따라 다른 상태

- **3차 시도 (StringTokenizer 문제)**

  - **개선:** visited를 3차원 `[N][M][2]`로 변경
  
  - **문제:** `"0100"` 입력을 StringTokenizer로 파싱
    - StringTokenizer는 공백 기준으로 분리 → `"0100"` 전체가 하나의 토큰
    - `split("")`로 바꿨으나 불필요한 배열 객체 생성
  
  - **해결:** `charAt(j)`로 직접 접근 → 메모리 절약

- **4차 시도 (맞았습니다!!)**

  - **최종 개선:**
    - 3차원 visited + add 전 마킹
    - LinkedList → ArrayDeque (더 빠른 큐 연산)
    - 0-indexed로 변경 (불필요한 +1 제거)
    - Node를 static class로 (외부 참조 제거)

### 🌱 3-2. 새롭게 알게 된 점 (Learning Points)

- **BFS visited 마킹 타이밍의 중요성**
  
  ```java
  // ❌ 메모리 초과 (poll 후 마킹)
  Node node = q.poll();
  visited[node.x][node.y] = true;  // 너무 늦음!
  
  // ✅ 정상 (add 전 마킹)
  if (!visited[nx][ny]) {
      visited[nx][ny] = true;  // 큐에 넣기 직전!
      q.add(new Node(nx, ny, ...));
  }
  ```
  
  → poll 후 마킹하면 같은 위치가 큐에 여러 번 들어가서 메모리 초과!

- **3차원 visited로 상태 구분**
  
  ```java
  visited[x][y][0] = 벽 안 부수고 (x,y) 도달
  visited[x][y][1] = 벽 부수고 (x,y) 도달
  ```
  
  → 같은 위치여도 "남은 기회"가 다르면 다른 상태로 취급해야 함!

- **StringTokenizer vs charAt()**
  
  - `"0100".split("")` → 배열 객체 생성 (메모리 낭비)
  - `str.charAt(i)` → 직접 접근 (효율적)

- **LinkedList vs ArrayDeque**
  
  - LinkedList: 노드마다 포인터 2개 → 메모리/속도 느림
  - ArrayDeque: 배열 기반 → 일반적으로 더 빠름

- **static class의 메모리 이점**
  
  ```java
  class Node { ... }              // 외부 클래스 참조 (8바이트) 포함
  static class Node { ... }       // 참조 없음, 더 가벼움
  ```
  
  → 큐에 수십만 개 Node가 들어가면 메모리 차이가 큼!

- **0-indexed vs 1-indexed**
  
  - `map[N+1][M+1]` → `map[N][M]`: 불필요한 칸 제거
  - 경계 체크: `nx > N` → `nx >= N`: 더 직관적

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- **양방향 BFS** 적용 시 시간 단축 가능한가?
- **벽을 K개까지** 부술 수 있다면? (visited를 [N][M][K+1]로?)
- **가중치가 있는 그래프**에서는? (다익스트라 + 상태 구분)
- 더 많은 BFS 상태 공간 탐색 문제 풀어보기
