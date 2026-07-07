## 📝 문제 요약

> 세 줄의 입력이 주어진다. 각 입력은 **FizzBuzz** 규칙에 따라 출력된 문자열 또는 숫자 `i`이다. 이 세 입력은 연속된 세 자연수 이고, 네 번째에 와야 할 수에 대한 **FizzBuzz** 결과를 출력하는 문제.

- **FizzBuzz 규칙**
  - 3과 5의 배수: “FizzBuzz”
  - 3의 배수: “Fizz”
  - 5의 배수: “Buzz”
  - 둘 다 아니면: i 그대로 출력

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **핵심 아이디어:**
  세 개의 연속된 입력 중, FizzBuzz 규칙에 따라 **최소 하나는 반드시 숫자**이다!
  (3의 배수, 5의 배수는 연속된 세 개의 문자열 내에 두 번 이상 존재할 수 없음)
  ⇒ 입력값 중 숫자를 찾아, 그 숫자를 기준으로 네 번째 수를 역산
- **알고리즘/자료구조:**
- **시간/공간 복잡도:** **O(1)**
- **핵심 로직:**
  1. 세 개의 입력값(`string[]`) 중에서 숫자인 값을 찾아 값(`target`)과 인덱스(`targetIndex`)를 구함
  2. 찾아낸 `target`과 `targetIndex`를 이용해 네 번째 수(`target + 3 - targetIndex`)를 계산
  3. 계산된 네 번째 수에 **FizzBuzz 규칙을** 적용하여 최종 결과 출력

---

## ✨ 2. 나의 최종 코드

- 코드 보기
  ```jsx
  fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const targetIndex = input.findIndex((v) => !isNaN(Number(v)));
  const target = Number(input[targetIndex]);

  const fourth = target + (3 - targetIndex);

  let result = "";
  if (fourth % 3 === 0) result += "Fizz";
  if (fourth % 5 === 0) result += "Buzz";

  console.log(result || fourth);
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Trubleshooting Log)

![image.png](attachment:f01ee2bd-c43c-4099-b175-d69e8d2871c8:image.png)

**1회차(맞았습니다!!)**

- `for` 반복문을 순회하며 `isNaN`(is Not a number)을 통해 숫자인 입력을 찾는 방식으로 성공
- **FizzBuzz** 결과 문자열을 만든 후, 삼항 연산자를 사용해 빈 문자열이면 숫자를 할당하는 방식
- 코드 보기
  ```jsx
  fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");
  let target, targetIndex;

  //1. 입력문 중 수 찾아내기
  for (let i = 0; i < input.length; i++) {
    if (!Number.isNaN(Number(input[i]))) {
      target = Number(input[i]);
      targetIndex = i;
      break;
    }
  }

  //2. 이어서 나올 수 찾기
  const fourth = target + (3 - targetIndex);

  //3. 문자열로 바꾸기
  let result = "";
  result += fourth % 3 === 0 ? "Fizz" : "";
  result += fourth % 5 === 0 ? "Buzz" : "";

  //4. 3의 배수도, 5의 배수도 아니면 수 반환
  result = result === "" ? fourth : result;

  console.log(result);
  ```

**2회차(리팩토링: 맞았습니다!!)**

- **주요 수정:** `for`문을 `findIndex()`로, 삼항 연산자를 논리 연산자(`||`) 단축 평가로 변경하여 코드를 간결하게 개선
- **발생 오류**: `findIndex(v ⇒ !Number.isNaN(v))`로 작성했을 때, `v`는 `string` 타입이므로 `isNaN`이 의도대로 동작하지 않았다!
  - 예: `Buzz \ Fizz \ 11` 입력시, `targetIndex` 는 `2`가 되기를 희망. but `0`을 반환
    ```jsx
    // 틀린 식
    const targetIndex = input.findIndex((v) => !Number.isNaN(v));

    //고친 식
    const targetIndex = input.findIndex((v) => !Number.isNaN(Number(v));
    ```
- 코드 보기
  ```jsx
  fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  const targetIndex = input.findIndex((v) => !isNaN(Number(v)));
  const target = Number(input[targetIndex]);

  const fourth = target + (3 - targetIndex);

  let result = "";
  if (fourth % 3 === 0) result += "Fizz";
  if (fourth % 5 === 0) result += "Buzz";

  console.log(result || fourth);
  ```

**3회차(리팩토링: 맞았습니다!!)**

- 주요 수정: `isNaN`는 `Number.isNaN`과 다르게 암묵적 형변환을 진행하기 때문에, 명시 형변환을 제거
  **⇒ 코드는 간결하나 에러 방지를 위해 명시적 변환 선호하기!**
  ```jsx
  // 이전
  const targetIndex = input.findIndex((v) => !Number.isNaN(Number(v));

  //이후
  const targetIndex = input.findIndex((v) => !isNaN(v));
  ```

---

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- `Array.prototype.findIndex()`: 특정 조건을 만족하는 첫 요소의 인덱스를 찾을 수 있다.
  → `for`문과 `break` 필요 없음
  ```jsx
  const targetIndex = input.findIndex((v) => !isNaN(Number(v));
  ```
- 논리 연산자(`||`)를 이용한 단축 평가
  `result`가 빈 문자열(`””`)일 경우 `Falsy` 값으로 취급됨. 따라서 `result`가 비어있으면 우항의 `nextNum`이 반환됨
  ```jsx
  result || fourth;
  ```
- `isNaN`과 `Number.isNaN`:
  `isNaN`은 값을 문자열로 암묵적 형변환을 한다. 반면 `Number.isNaN`은 형변환을 하지 않는다
  **→ `Number.isNaN()`이 더 안전하고 명확한 방법!**
  ```jsx
  Number.isNaN(Number(v));
  ```

---

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questioins)

- `findIndex`와 같이 유용한 배열 고차함수(e.g. `find`, `some`, `every`)의 학습
- 단축 평가 활용 연습
