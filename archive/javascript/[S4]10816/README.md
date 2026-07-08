## 📝 문제 요약

> N개의 숫자 카드를 가지고 있을 때, M개의 다른 숫자 카드가 각각 몇 개씩 있는지 찾는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조: 해시 맵(Hash Map)**
  - 처음에는 JavaScript의 기본 `Object`를 해시 맵으로 사용했고, 이후 key-value 자료구조의 의도가 더 명확한 `Map` 객체를 사용함
- **시간/공간 복잡도:**
  - **시간 복잡도: O(N + M)**
    - N개의 카드를 `Map`에 기록하는 데 O(N), M개의 카드를 `Map`에 조회하는 데 O(M)
  - **공간 복잡도: O(N)**
    - N개의 카드 중 중복을 제외한 숫자들을 Map에 저장해야 하므로, 카드의 종류만큼 공간이 필요함
- **핵심 로직:**

  1. 상근이가 가진 N개의 카드(`nArr`)를 순회하며 각 숫자 개수를 센다
  2. 숫자를 key, 개수를 value로 하는 `Map`객체를 사용해 `{숫자: 개수}` 형태로 저장함

     개수: (`(map.get(num) || 0) + 1`)

  3. M개의 카드(`mArr`)를 순회하며, `Map`에서 해당 숫자의 Value를 찾음
  4. Map에 숫자가 존재하지 않으면(`undefined`), 개수는 0

     개수: `map.get(targetNum) || 0`

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const nArr = input[1].split(" ").map((num) => Number(num));
  const mArr = input[3].split(" ").map((num) => Number(num));

  const countMap = new Map();

  for (const num of nArr) {
    countMap.set(num, (countMap.get(num) || 0) + 1);
  }

  const result = mArr.map((targetNum) => countMap.get(targetNum) || 0);

  console.log(result.join(" "));
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

![image.png](attachment:a3c0c3b5-55ad-4f4a-9600-0f06c477d927:image.png)

- **1회차(맞았습니다!)**
  - 일반 객체(`{}`)를 해시맵으로 활용하여 첫 풀이에 성공함
  - `++count[num] || 1` 과 학습한 (무기명)**즉시 실행 함수**를 적용하여 구현함
    → 코드 명확성 측면에서 `Map`과 `for…of`를 사용하는 것이 더 낫다고 판단하여 리팩토링함
- **2회차(맞았습니다!)**
  - 1회차를 개선하기 위해, `Map`객체, `for…of`로 리팩토링
  - `countMap.set(num, (countMap.get(num) || 0) + 1)` 으로 가독성과 안정성 높임
- **3회차(참고)**
  - AI가 알려준 다른 알고리즘 접근법으로 **정렬 + 이분 탐색**을 고려해 보았으나, 시간 복잡도가 `O((N+M)logN)`으로 해시맵 보다 비효율적이라, 참고만 했음

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **`Map` 객체의 활용**: JavaScript에서 key-value 데이터를 다룰 때, 일반 객체 `Object`보다 `Map`이 더 정확하고 안전한 선택이다. `get()`, `set()`, `has()` 등 명시적인 메서드를 사용해 데이터를 조작하며, 모든 타입의 키를 허용한다는 장점이 있다
- **논리 연산자를 이용한 기본값 처리**: `A || B` 구문은 A가 falsy(예: `undefined`, `null`, `0`)일 때 B를 반환한다. \*여기서 undefined도 falsy라는 점을 간과하여 +undefined → NaN을 사용해 로직을 구현했었음..

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- `Map`과 `Object`의 정확한 차이점은?
