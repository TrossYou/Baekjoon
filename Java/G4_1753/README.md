# BOJ 1753 - 최단경로

### 학습한 내용

- 다익스트라에서 우선순위 큐에는 `간선 가중치`가 아니라 `시작점부터의 누적 거리`를 넣어야 함
- `PriorityQueue`에서 비교식은 `o1[1] - o2[1]`보다 `Integer.compare(o1[1], o2[1])`가 안전함(오버플로우 방지)
- 큐에 같은 정점이 여러 번 들어갈 수 있으므로, 꺼낸 값이 최신 거리인지 검증하는 패턴이 중요함
- `if (dist > d[to]) continue;` 또는 `if (curDist != dist[cur]) continue;`로 stale entry(오래된 값) 스킵 가능

## 📝 문제 요약

> 방향 그래프에서 시작 정점 K가 주어졌을 때, 모든 정점까지의 최단거리를 구하는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - 다익스트라(Dijkstra)
  - 인접 리스트(`List<List<int[]>>`)
  - 최소 힙(`PriorityQueue<int[]>`)
- **시간/공간 복잡도:**
  - 시간복잡도: `O((V + E) log V)`
  - 공간복잡도: `O(V + E)`
- **핵심 로직:**
  1. 거리 배열 `d[]`를 `INF`로 초기화하고 시작 정점은 `0`으로 설정
  2. 우선순위 큐에는 `[정점, 현재까지의 최단거리]`를 넣음
  3. 큐에서 꺼낸 값이 최신 최단거리보다 크면 스킵
  4. 인접 정점을 완화(relaxation)하고, 갱신된 경우에만 큐에 재삽입
  5. 끝까지 탐색 후 `INF`는 도달 불가로 출력

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- 거리 배열/그래프 초기화

```java
int[] d = new int[V + 1];
Arrays.fill(d, INF);
d[start] = 0;

List<List<int[]>> edges = new ArrayList<>(V + 1);
for (int i = 0; i <= V; i++) {
  edges.add(new ArrayList<>());
}
```

- 다익스트라 루프(최신 거리 검증 + 완화)

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
pq.add(new int[] { start, 0 });

while (!pq.isEmpty()) {
  int[] current = pq.poll();
  int to = current[0];
  int dist = current[1];

  if (dist > d[to]) continue;

  for (int[] e : edges.get(to)) {
    if (d[e[0]] > d[to] + e[1]) {
      d[e[0]] = d[to] + e[1];
      pq.add(new int[] { e[0], d[e[0]] });
    }
  }
}
```

- 결과 출력

```java
for (int i = 1; i <= V; i++) {
  if (d[i] == INF) sb.append("INF");
  else sb.append(d[i]);
  sb.append('\n');
}
System.out.print(sb);
```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 초기 구현에서 `pq.add(e)`처럼 간선 정보 자체(`[next, weight]`)를 큐에 넣는 실수를 함
- 이 경우 큐 정렬 기준이 누적 최단거리(`d[next]`)가 아니라 간선 가중치(`weight`)가 되어, 특정 반례에서 오답 발생
- 해결: 완화 성공 시 `pq.add(new int[] { next, d[next] })`로 수정
- 추가로 stale entry를 건너뛰는 조건(`if (dist > d[to]) continue;`)을 유지해 안정성 확보

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- 다익스트라 핵심은 “큐의 우선순위 기준”이며, 항상 누적거리여야 함
- `if (curDist != dist[cur]) continue;`는 동일 정점이 큐에 여러 번 들어간 상황에서 **오래된 거리 상태를 무시**하기 위한 패턴
- 비교식은 `Integer.compare`를 사용해 오버플로우 위험을 제거하는 습관이 좋음

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- `visited[]`를 쓰는 구현과 `stale entry skip`만 쓰는 구현의 장단점 정리해보기
- `int[]` 대신 `Edge/State` 클래스로 바꿨을 때 가독성/성능 차이 직접 비교해보기
- 동일 문제를 `long` 거리 배열 버전으로도 작성해 제약이 커질 때 대비해보기
