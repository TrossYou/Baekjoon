<aside>

### 📘 학습한 내용

> 전날 여러 시도 끝에 배운 패턴을 완벽하게 이해했고, 활용할 수 있음을 증명함.
> 어제(1654 랜선자르기) 6회 시도 → 오늘 1회만에 해결

</aside>

## 📝 문제 요약

> N개의 나무를 절단기로 높이 H만큼 잘라 M미터의 나무를 가져가려 할 때, 설정 가능한 최대 높이를 구하는 문제.
> 핵심: 어제 학습한 매개변수 탐색을 동일하게 적용
>
> [(1654 랜선자르기)](https://www.notion.so/1654-2c090836b87f80f8bcd1f7a2b09eb9b1?pvs=21)

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - 매개변수 탐색 (Parametric Search)
  - 이분 탐색 (Binary Search)
  - 어제 학습한 패턴 직접 적용
- **시간/공간 복잡도:**
  - 시간: O(N log H) (H = 최대 나무 높이, 이분 탐색 log H x 각 검증 O(N))
  - 공간: O(N) (나무 배열)
- **핵심 로직:**
  1. 탐색 범위: [0, max(trees)]
  2. mid 높이로 잘랐을 때 얻는 나무 길이 계산
  3. M 이상 획득 가능 → 더 높게 설정 시도 (start = mid + 1)
  4. M 미만 → 더 낮게 설정 (end = mid - 1)
  5. 조기 종료 최적화로 성능 개선

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const [N, M] = input[0].split(" ").map(Number);
  const trees = input[1].split(" ").map(Number);

  const canCut = (height) => {
    let length = 0;
    for (const tree of trees) {
      if (tree > height) {
        length += tree - height;
        if (length >= M) return true;
      }
    }
    return false;
  };

  const binarySearch = (start, end) => {
    let result = 0;
    while (start <= end) {
      const mid = Math.floor((start + end) / 2);
      if (canCut(mid)) {
        result = mid;
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }
    return result;
  };

  const max = Math.max(...trees);
  console.log(binarySearch(0, max));
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

**특이사항: 어제 학습내용과 거의 동일**

- **1차시도(맞았습니다!!)**
  - 문제를 보자마자 “어제 풀었던 1654 랜선 자르기와 동일한 구조” 파악
    → 동일하게 구현하여 **한 번에 통과**
- **2차시도(맞았습니다!!) - 리팩토링**
  1. 함수명: `getLength` → `canCut` (불린 함수 네이밍)
  2. 반복문: `for (let i = 0; … )` → `for … of` (인덱스 불필요)
  3. 변수명: `middle` → `mid` (업계 표준)

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

-

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 삼분 탐색(Ternary Search)
