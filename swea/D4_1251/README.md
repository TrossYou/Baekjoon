<aside>

## 📘 학습한 내용

- 완전그래프 MST 문제에서 `Prim O(N²)`가 `간선 생성 + PriorityQueue` 방식보다 메모리와 시간 면에서 더 유리할 수 있다는 점을 학습했다.
- 환경부담금 `E`는 모든 간선에 동일하게 곱해지는 상수이므로, MST를 구성할 때는 `E * L²` 대신 `L²`만 비교해도 같은 결과가 나온다는 점을 이해했다.
- 오버플로우 문제는 반환 타입만 `long`으로 바꾼다고 해결되지 않고, **연산이 수행되는 시점의 타입**이 중요하다는 점을 확인했다.
- 따라서 거리 계산은 `long`으로 승격한 뒤 제곱해야 하며, 최종 비용은 마지막에 한 번만 `Math.round(E * totalDist)`로 처리하는 것이 안전하다.

</aside>

## 📝 문제 요약

> N개의 섬 좌표가 주어질 때, 모든 섬을 연결하는 해저터널의 총 환경부담금 `E × L²`의 합이 최소가 되도록 해야 한다.  
> 즉, 모든 섬을 연결하는 최소 스패닝 트리(MST)를 구한 뒤, 최종 환경부담금을 반올림하여 출력하는 문제이다.

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** Prim 알고리즘, 배열 기반 최소 거리 관리
- **시간/공간 복잡도:** 시간 `O(N²)`, 공간 `O(N)`
- **핵심 로직:**
  1. `minDist[i]`를 현재 MST와 i번 섬을 연결하는 최소 거리 제곱값으로 유지한다.
  2. 방문하지 않은 섬 중 `minDist`가 가장 작은 섬을 선택해 MST에 추가한다.
  3. 새로 추가한 섬을 기준으로 다른 섬들과의 거리 제곱을 계산하며 `minDist`를 갱신한다.
  4. MST에 포함된 거리 제곱 합을 모두 구한 뒤, 마지막에만 `E`를 곱하고 반올림한다.

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- Prim 초기화 및 최소 정점 선택

  ```java
  static long play() {
    long totalDist = 0L;
    minDist[0] = 0L; // 0번 섬 추가
    visited[0] = true;
    int cnt = 1;

    for (int i = 1; i < N; i++) {
      minDist[i] = getDist(0, i); // 초기 최소 거리 설정
    }

    while (cnt != N) {
      int minIdx = -1;
      long minD = Long.MAX_VALUE; // 최소 거리

      for (int i = 0; i < N; i++) {
        if (!visited[i] && minD > minDist[i]) {
          minD = minDist[i];
          minIdx = i;
        }
      }

      visited[minIdx] = true;
      totalDist += minD;
      cnt++;
  ```

- 새로 추가한 정점을 기준으로 최소 거리 갱신

  ```java
      for (int i = 0; i < N; i++) {
        if (!visited[i]) {
          long dist = getDist(minIdx, i);
          if (dist < minDist[i]) {
            minDist[i] = dist;
          }
        }
      }
    }
    return totalDist;
  }
  ```

- 오버플로우 방지를 위한 거리 계산과 최종 비용 계산

  ```java
  static long getDist(int a, int b) {
    long dx = (long) xArr[a] - xArr[b];
    long dy = (long) yArr[a] - yArr[b];

    return dx * dx + dy * dy;
  }

  long totalDist = play();
  ans = Math.round(E * totalDist);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 처음에는 거리 계산에서 `dx`, `dy`를 `int`로 두고 `dx * dx + dy * dy`를 수행했다.
- 하지만 좌표 차가 최대 `1,000,000`까지 가능하므로, 제곱값은 `1,000,000,000,000`이 되어 `int` 범위를 초과한다.
- 반환 타입을 `long`으로 두더라도, 이미 `int` 곱셈 단계에서 오버플로우가 발생한 뒤라 문제는 해결되지 않았다.
- 이를 해결하기 위해 `dx`, `dy`를 `long`으로 승격한 뒤 제곱하도록 수정했다.

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- `(long)(dx * dx + dy * dy)`는 이미 오버플로우가 발생한 뒤 캐스팅하는 것이므로 안전하지 않다.
- 올바른 방식은 `(long) dx * dx`처럼 **곱셈 전에 타입을 승격**시키는 것이다.
- MST 문제에서 간선 비용에 모두 동일한 상수 `E`가 곱해진다면, MST 선택 과정에서는 `E`를 생략해도 결과가 달라지지 않는다.
- 완전그래프에서는 간선을 모두 저장하는 방식보다 배열 기반 Prim이 더 효율적일 수 있다.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 같은 문제를 Kruskal 알고리즘으로 구현했을 때, 입력 크기별 성능 차이는 얼마나 날까?
- Prim의 배열 기반 `O(N²)` 방식과 PriorityQueue 기반 방식은 어떤 조건에서 성능 역전이 일어날까?
- 좌표 기반 MST 문제에서 추가적인 기하 최적화가 가능한 문제 유형은 무엇이 있을까?
