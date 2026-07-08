## 📝 문제 요약

> 13자리 ISBN 코드 중 `*` 로 표시된 한 자리를 찾아내는 문제. ISBN 규칙에 따라, 홀수 번째 숫자는 1을 곱하고 짝수 번째 자리 숫자는 3을 곱하여 모두 더했을 때 **총합이 10의 배수**가 되어야 함.

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
- **시간/공간 복잡도:** 입력의 길이가 13으로 고정 → 시간 공간 복잡도 모두 **O(1)**
- **핵심 로직:**
  1. 주어진 13자리 문자열을 순회하며 `*`가 아닌 숫자들의 가중치 합(`sum`)을 계산하고, `*`의 위치(`starIndex`)를 따로 저장
  2. `starIndex`에 따라 1을 곱할지 3을 곱할지 `starWeight`에 저장
  3. (최적화 전) `*`에 0~9 숫자를 하나씩 대입해보며, `sum`과 더했을 때 10의 배수가 되는 숫자를 찾음.

     (최적화 후) 0~9를 대입하기 보다, 모뮬러 연산(%)을 이용해 수학적으로 바로 계산. //AI의 아이디어

---

## ✨ 2. 나의 최종 코드

코드를 ai의 도움으로 리팩토링 해 나아감 (3번의 리팩토링)

- 코드 보기
  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("");

  // 1. reduce를 사용해 숫자들의 가중치 합과 '*'의 인덱스를 한 번에 계산
  const { sum, starIndex } = input.reduce(
    (accumObject, curr, index) => {
      if (curr === "*") {
        accumObject.starIndex = index;
      } else {
        const weight = index % 2 === 0 ? 1 : 3;
        accumObject.sum += weight * curr;
      }
      return accumObject;
    },
    { sum: 0, starIndex: -1 }
  );

  // 2. '*'의 위치에 따른 가중치(1 또는 3)를 계산
  const starWeight = starIndex % 2 === 0 ? 1 : 3;

  // 3. sum에 더해 10의 배수를 만들기 위해 필요한 목표값 계산
  const target = (10 - (sum % 10)) % 10;

  // 4. 가중치에 따라 star 값을 수학적으로 바로 계산
  const star = starWeight === 1 ? target : (target * 7) % 10;

  console.log(star);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾  3-1. 오류 해결 과정 (Trubleshooting Log)

**오류1**

- 오류: 다른 값이 나옴.
- 원인: check는 String 타입이므로 + 연산 시 문자열 연산자로 실행되어 다른 값이 됨.
  ```jsx
  ...
  const check = input[len - 1];
  ...
  const starValue = 10 - (check + sum) % 10;
  ```
- 해결: `check = Number(input[len-1]);` 으로 형변환 해줌

---

**1회차 (틀렸습니다)**

- **코드**
  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("");
  const len = input.length;
  const check = Number(input[len - 1]);
  let sum = 0;
  let starWeight;

  for (let i = 0; i < len - 1; i++) {
    if (input[i] === "*") {
      starWeight = i % 2 === 0 ? 1 : 3;
      continue;
    }
    let weight;
    if (i % 2 === 0) {
      weight = 1;
    } else {
      weight = 3;
    }
    sum += weight * input[i];
  }

  const starValue = 10 - ((check + sum) % 10);
  const result = starValue / starWeight;

  console.log(result);
  ```
- **반례:** `97803064061*7`
  - 정답: `5`
  - 출력: `1.6666666666666667`
- **분석:** \*의 값을 나누기로 계산한 방식은 `starWeight`가 3일 때 오류가 발생한다.

---

**2회차 (맞았습니다!!)**

- **핵심 수정**
  1. 굳이 `check + sum`을 따로 할 필요가 없음 → 반복문은 `*`을 제외한 13자리 전체의 합을 구함
  2. \*을 찾기 위해, 0~9까지 모두 대입해보는 두 번째 반복문 추가함
- **수정한 코드**
  ```jsx
  //기존 코드
  // const starValue = 10 - ((check + sum) % 10);
  // const result = starValue / starWeight;

  let result;
  for (let star = 0; star < 10; star++) {
    if ((sum + starWeight * star) % 10 === 0) {
      result = star;
      break;
    }
  }
  ```

---

**3회차 (맞았습니다!!)**

- **핵심 수정**: 첫 번째 `for`문을 `reduce` 메소드로 변경. 주석 제거
- **수정 이유**: 코드의 **가독성과 스타일 개선**을 위한 리팩토링. “배열의 요소들을 하나의 값으로 축약한다”는 의도 명확히 함
- **수정한 코드**
  ```jsx
  /* 기존 코드
  for (let i = 0; i < len - 1; i++) {
    if (input[i] === "*") {
      starWeight = i % 2 === 0 ? 1 : 3;
      continue;
    }
    let weight;
    if (i % 2 === 0) {
      weight = 1;
    } else {
      weight = 3;
    }
    sum += weight * input[i];
  }
  */

  const { sum, starIndex } = input.reduce(
    (accumObject, curr, index) => {
      if (curr === "*") {
        accumObject.starIndex = index;
      } else {
        const weight = index % 2 === 0 ? 1 : 3;
        accumObject.sum += weight * curr;
      }
      return accumObject;
    },
    { sum: 0, starIndex: -1 }
  );
  ```

---

**4회차 (맞았습니다!!)**

- **핵심 수정:** `reduce` 로직 다듬기 & 모듈러 연산으로 두번째 `for`문 제거
- **수정 이유**: `for` 문 없이 `*`값을 바로 찾을 수 없을까 고민. AI에 질문하여 `모뮬러 연산` 알게되었고, 이용함
  → 시간 복잡도 조금 개선
- **수정한 코드**
  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("");
  const len = input.length;

  const { sum, starIndex } = input.reduce(
    (accumObject, curr, index) => {
      if (curr === "*") {
        accumObject.starIndex = index;
      } else {
        const weight = index % 2 === 0 ? 1 : 3;
        accumObject.sum += weight * curr;
      }
      return accumObject;
    },
    { sum: 0, starIndex: -1 }
  );
  const starWeight = starIndex % 2 === 0 ? 1 : 3;

  const target = 10 - (sum % 10);
  let star;
  if (starWeight === 1) {
    star = target;
  } else {
    star = (target * 7) % 10; // 모뮬러 연산
  }

  console.log(star);
  ```

---

**5회차 (맞았습니다!!)**

- **핵심 수정**: `if/else` 문을 삼항 연산자로 변경 & `target`연산 시 `sum`이 10의 배수인 경우 `target`이 10이 나오는 것을 개선
- **수정한 코드**
  ```jsx
  const fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("");
  const len = input.length;

  const { sum, starIndex } = input.reduce(
    (accumObject, curr, index) => {
      if (curr === "*") {
        accumObject.starIndex = index;
      } else {
        const weight = index % 2 === 0 ? 1 : 3;
        accumObject.sum += weight * curr;
      }
      return accumObject;
    },
    { sum: 0, starIndex: -1 }
  );
  const starWeight = starIndex % 2 === 0 ? 1 : 3;
  const target = (10 - (sum % 10)) % 10; //수정한 부분
  const star = starWeight === 1 ? target : (target * 7) % 10; //수정한 부분

  console.log(star);
  ```

---

### **🌱** 3-2. **새롭게 알게 된 점 (Learning Points)**

- `reduce`에서 객체를 반환할 수 있다는 점
  ```jsx
  const { sum, starIndex } = input.reduce(
  	...
  	return accumObject;
  }
  ```
- **모듈러 연산**: 특정 조건 하에서 반복문을 사용한 탐색(`Brute-force`)을 **O(1) 시간 복잡도의 수학적 계산**으로 대체할 수 있다는 점을 배움.
  - `(3 * star) % 10 = target` 방정식을 풀 때, 양변에 7을 곱하면 `star = (target * 7 ) % 10` 이 된다는 것을 알게 됨.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questioins)

1. **모듈러 학습**: 3과 7의 관계처럼, 다른 수들의 모뮬러 연산의 역원을 활용하는 추가 문제 풀어보기
2. `reduce`를 사용하는 더 복잡한 케이스 연습해보기
