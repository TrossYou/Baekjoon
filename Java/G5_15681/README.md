<aside>

## 📘 학습한 내용

- 트리 구조에서 서브트리의 크기를 구하는 `DP` 기법 습득
- 대규모 데이터(**N=100,000**) 처리를 위한 메모리 관리 및 재귀 제한 해결 방법 학습
</aside>

## 📝 문제 요약

> 주어진 무방향 그래프를 루트가 **R**인 트리로 변환했을 때, 특정 노드 **U**를 루트로 하는 서브트리의 정점 개수를 구하는 문제
>
> 정점 수(**N**) 및 쿼리 수(**Q**) 최대 100,000개

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** 트리, 그래프 탐색(BFS/DFS), DP
- **시간/공간 복잡도:**
  - 시간: O(N+Q)
  - 공간: O(N)
- **핵심 로직:**
  1. **인접 리스트 구현**: N이 크므로 인접 행렬 대신 `List<Integer>[]` 사용
  2. **트리 구조 정립**: 주어진 간선은 무방향이므로, BFS를 통해 부모-자식 관계 명확화
  3. **Bottom-up DP**: 리프 노드부터 루트 방향으로 올라오며 서브트리 크기 누적 계산

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- **초기 접근 (Top-down DFS - 메모리 초과 위험)**
  ```java
  // 재귀를 이용한 방식: 편향 트리일 경우 StackOverflow 위험 존재
  static int getSize(int curNode) {
      if (sizeArr[curNode] != -1) return 0;
      sizeArr[curNode] = 1;
      for (int node : connects[curNode]) {
          sizeArr[curNode] += getSize(node);
      }
      return sizeArr[curNode];
  }
  ```
- **최종 개선 (BFS + Bottom-up DP)**

  ```java
  // BFS로 탐색 순서(orders)와 부모(parent) 기록
  static void play() {
      Queue<Integer> q = new LinkedList<>();
      q.add(R);
      isVisited[R] = true;
      while (!q.isEmpty()) {
          int node = q.poll();
          orders.add(node);
          for (int child : adjs[node]) {
              if (isVisited[child]) continue;
              isVisited[child] = true; // 큐 삽입 전 방문 체크가 효율적
              parent[child] = node;
              q.add(child);
          }
      }
  }

  // 탐색 순서의 역순으로 서브트리 크기 누적
  Arrays.fill(sizeArr, 1);
  for (int i = orders.size() - 1; i >= 0; i--) {
      int cur = orders.get(i);
      int p = parent[cur];
      if (p != 0) sizeArr[p] += sizeArr[cur];
  }
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **초기 시도 (`boolean[][]`):** **N=100,000**일 때 **10^{10}** 크기의 행렬이 필요하여 **Memory Limit Exceeded(MLE)** 발생 → 인접 리스트(`ArrayList[]`)로 변경하여 해결
- **재귀 방식 고려:** 자바의 기본 스택 사이즈로 인한 **StackOverflowError** 가능성 인지
  → 비재귀 방식(BFS + 순서 역순 순회)으로 설계 변경하여 안정성 확보

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **인접 리스트의 중요성:** 정점의 개수가 일정 수준(**N > 10,000**)을 넘어가면 공간 복잡도 **O(V^2)**인 인접 행렬은 사용할 수 없음
- **BFS 순서 활용:** BFS 탐색 결과를 리스트에 저장한 뒤 역순으로 처리하면, 트리에서 부모-자식 관계의 DP를 반복문만으로 구현할 수 있다는 점을 학습

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

-
