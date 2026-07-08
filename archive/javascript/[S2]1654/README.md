## 📝 문제 요약

> K개의 랜선을 잘라 N개의 같은 길이 랜선을 만들 때, 만들 수 있는 **최대 길이**를 구하는 문제.
> 핵심: “길이 X로 N개 이상 만들 수 있는가?” 라는 결정 문제를 이분 탐색으로 해결

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - 매개변수 탐색(Parametric Search)
  - 이분 탐색(Binary Search)
  - 결정 문제로의 변환: 최적화 → “X로 가능한가?”
- **시간/공간 복잡도:**
  - 시간: O(KlogM) (M = 최대 랜선 길이, 이분탐색 log M x 각 검증 O(K))
  - 공간: O(K) (랜선 배열)
- **핵심 로직:**
  1. 탐색 범위: [1, max(cables)]
  2. mid 길이로 만들 수 있는 개수 계산
  3. N개 이상 가능 → 더 긴 길이 시도 (start = mid + 1)
  4. N개 미만 → 더 짧은 길이 시도 (end = mid -1)

---

## ✨ 2. 나의 최종 코드

- 코드 보기
  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const [K, N] = input[0].split(" ").map(Number);
  const cables = input.slice(1, K + 1).map(Number);

  const canMake = (length) => {
    let count = 0;
    for (const len of cables) {
      count += Math.floor(len / length);
      if (count >= N) return true;
    }
    return false;
  };

  const binarySearch = (start, end) => {
    let result = 0;
    while (start <= end) {
      const mid = Math.floor((start + end) / 2);

      if (canMake(mid)) {
        result = mid;
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }
    return result;
  };

  const maxLength = Math.max(...cables);
  console.log(binarySearch(1, maxLength));
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

**사고의 흐름: 완전탐색 → 휴리스틱 → 이분탐색**

- **1차시도(시간초과)**
  - 접근: 최댓값부터 1씩 감소하며 완전탐색
    ```jsx
    for (let i = max; i > 0; i--) {
      if (calculate(i) >= N) {
        console.log(i);
        break;
      }
    }
    ```
  - 시간: O(max x K) = O(2^31 x 10^4)
  - 문제: 탐색 범위가 너무 넓음 (최대 2^31 - 1)
- **2,3차시도(시간초과)**

  - 개선: sum/N을 최댓값으로 설정

  ```jsx
  const startVal = Math.floor(sum / N);
  for (let i = startVal; i > 0; i--) {
    ...
  }
  ```

  - 논리: “평균 길이가 최댓값일 것”
  - 문제: 여전히 선형 탐색 O(N) → 시간초과

  **→ 알고리즘 자체를 바꿔야 함**

- **4,5차시도(틀렸습니다)**

  - 개선: 이분탐색 적용 → O(logN)
    ```jsx
    const maxVal = Math.min(Math.floor(sum / N), min);
    const res = binarySearch(1, maxVal);
    ```
  - 문제: **탐색 범위 설정 요류**
    - sum / N: 평균값 (최댓값 아님)
    - min: 가장 짧은 랜선 → 무의미
  - 반례 발견:
    ```jsx
    // 반례1: 꼭 결과 평균 이하이지 않을 수 있다(모든 케이블을 다 써야 하는건 아님).
    3 1
    1
    1
    10000
    // 평균: 3334
    // 결과: 10000

    // 반례2: 오버플로우
    2 2
    2147483647
    2147483647
    ```

- **6차시도(맞았습니다!!)**

  - maxVal = Math.max(…inputArr)

  ```jsx
  const maxVal = Math.max(...inputArr);
  const res = binarySearch(1, maxVal);
  ```

- **7차시도(맞았습니다!!) - 리팩토링**
  - **개선 포인트**
    1. **변수명 명확화**
       - `inputArr` → `cables`
       - `calculate` → `canMake`
       - `res` → `result`
    2. **배열 생성 간소화**

       ```jsx
       // Before
       const inputArr = new Array();
       for (let i = 0; i < K; i++) {
         inputArr[i] = Number(input[1 + i]);
       }

       // After
       const cables = input.slice(1, K + 1).map(Number);
       ```

    3. **조기 종료 최적화**

       ```jsx
       const canMake = (length) => {
         let count = 0;
         for (const len of cables) {
           count += Math.floor(len / length);
           if (count >= N) return true;
         }
         return false;
       };
       ```

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- 매개변수탐색
- **이분탐색:** 재귀함수가 아닌 while로 구현
- 스프레드 연산자(`…`)
  ```jsx
  const arr = [1, 2, 3];

  // 잘못된 사용

  Math.max(arr); // // Math.max([1,2,3]); -> NaN (배여ㄹㅡㄹ 숫자로 비교 불가)

  // 올바른 사용
  Math.max(...arr); // 전개되어 Math.max(1, 2, 3)
  ```
  - **객체 전개**
    ```jsx
    const obj1 = { a: 1, b: 2 };
    const obj2 = { c: 3 };

    const merged = { ...obj1, ...obj2 };
    // { a: 1, b: 2, c: 3 }
    ```

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

-
