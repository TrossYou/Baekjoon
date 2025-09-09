## 📝 문제 요약

> 입력으로 주어지는 각 문자열에 포함된 괄호 `()`와 `[]`의 균형이 맞는지 확인하는 문제. 괄호 외의 다른 문자는 무시하며, 마침표(`.`)는 문자열의 끝을 의미한다. 균형이 맞으면 “yes” 아니면 “no”를 출력한다.

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

<aside>
💡

`여는괄호`가 나오면 어딘가에 쌓아두고, `닫는괄호`가 나오면 가장 마지막에 있는 괄호와 짝이 맞는지 확인해야 한다. 이때 스택(`Stack`)자료구조가 가장 적합하다고 판단

</aside>

- **알고리즘/자료구조: 스택(Stack)**
- **시간/공간 복잡도:**
  - 시간 복잡도: **O(N)**(N은 문자열의 길이). 각 문자를 한 번씩 순회한다
  - 공간 복잡도: **O(N)**. 최악의 경우(모든 문자가 `여는괄호`) 스택에 모든 괄호가 저장될 수 있다.
- **핵심 로직:**
  1. 문자열을 처음부터 끝까지 한 글자씩 순회
  2. 여는 괄호(`(`, `[`)이면 스택에 `push`
  3. 닫는 괄호(`)`, `]`)이면 스택에서 `pop` 하여 가장 최근의 여는 괄호랑 짝이 맞는지 비교
     - 이때 스택이 비어있거나, 짝이 맞지 않으면 `“no”`를 바로 반환
  4. 문자열 순회가 끝난 후, 스택이 비어 있으면 `“yes”`, 그렇지 않으면 `“no”`를 반환

---

## ✨ 2. 나의 최종 코드

- 코드 보기
  ```jsx
  fs = require("fs");
  const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

  function solve(line) {
    const stack = [];
    const bracketPairs = { ")": "(", "]": "[" };
    const openBracket = new Set(["(", "["]);

    for (const char of line) {
      if (openBracket.has(char)) stack.push(char);
      else if (char in bracketPairs) {
        const latest = stack.pop();
        if (latest === undefined || latest !== bracketPairs[char]) {
          return "no";
        }
      }
    }
    return stack.length === 0 ? "yes" : "no";
  }

  input.forEach((line) => {
    if (line !== ".") console.log(solve(line));
  });
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Trubleshooting Log)

![image.png](attachment:a4ed608d-f6ea-477b-93cb-bf72ff207240:image.png)

- **1회차(맞았습니다!!)**
  - **What**: `if-else` 문으로 모든 괄호 케이스를 분기 처리. `pop`한 괄호가 맞지 않을 경우 다시 `push` 하는 복잡한 로직 사용
  - **Why**: 스택의 기본 개념에 충실하여 가장 직관적인 방식으로 구현
  - **How**: 정답은 맞혔지만 코드가 길고 복잡하며, 반복되는 코드가 많아서 아 비효율 적이라고 생각함.
    - 문제 조건 상 마지막 문장은 `“.”` 입력이라는 조건을 믿고 `idx === input[i].length - 2`는 패스하는 코드로, 테스트는 통과했지만 안전하지 않았음
- **2회차(맞았습니다!!)**
  - **What:** 반복되는 로직을 `check` 함수로 분리
  - **Why**: 코드의 중복을 줄이고 재사용성을 높이고자 시도
  - **How**: 구조적으로는 약간 개선되었으나, 여전히 외부의 `parenStack` 변수를 직접 수정하는 방식이었음
  → 재사용 코드 최소화를 위해 check 함수 및 코드 개선
- **3회차(맞았습니다!!)**
  - **What**: 괄호의 짝을 `bracketParis` 객체로 정의하여 `if-else` 문을 대체하고 코드의 반복 줄임
    - 짝을 객체로 정의하는 방법 고민 → `Map`, `Object`를 생각했지만, 구현을 하지 못해 ai 도움
      → `Object`로 `bracketPairs` 구현
  - **Why**: `)`일 때와 `]`일 때, 코드가 거의 비슷하지만 짝이 달라서 따로 구현하는 것을 개선하고 싶었음
  - **How**: 코드의 가독성과 확장성이 크게 향상됨. 하지만 여전히 전역 변수를 직접 수정
- **4회차(최종 리팩토링 - 맞았습니다!!)**
  - **What**: 각 줄을 처리하는 로직 전체를 solve 함수로 캡슐화. 전역 변수 대신 지역 변수 stack을 사용
    → 하나의 char을 판단하는 기존 `check`함수는 `stack`을 전역변수로 할 수 밖에 없지만,
    문장 단위로 함수를 구현할 경우, `stack`을 **지역변수**로 다룰 수 있다!
  - **Why**: 전역 상태에 의존하는 코드 문제점 해결
  - **How**: 각 줄의 연산이 독립적으로 수행되어 코드의 안정성과 예측 가능성 높아짐

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **상태 관리의 중요성**: 전역 변수(혹은 외부 스코프의 변수)에 의존하는 함수는 버그를 유발하기 쉽다
  → 각 기능은 독립적으로 동작하도록 자체적인 상태를 갖게 캡슐화 하는 것이 좋다
- 선언적 코드의 장점: `if-else`로 모든 케이스를 나열하는 것보다, `bracketPairs = { “)”: “(”, “]”:”[” }` 으로 짝 관계를 선언해두니 코드가 훨씬 명확하고 간결해짐
- **자료구조/메서드의 시간 복잡도**: 여는 괄호를 확인할 때, 배열의 `includes()` (`O(n)`)보다 Set의 `.has()`(`O(1)`)가 이론적으로 더 효율적이라는 것을 알게됨
- `Object.values(parenStack).includes(char)`과 `char in bracketParis`의 차이
  - `values().inclueds()`: 객체의 값에 포함되어 있는지 확인하는 코드
  - `키 in 객체`: 객체에 키가 있는지 확인하는 코드
- `for(const char of line)` / `char in bracketPairs`
  : `for…of`, `in` 반복문

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questioins)

- `forEach`와 `for...of`의 비교: 뭐가 더 좋은 코드일까?
  - `forEach`는 `for...of`와 다르게 중간에 종료하지 못하지면,
    중간에 중단할 일이 없다면 `forEach`가 코드가 간단하고, 좋은 코드가 아닐까? 생각함
  → `for…of` +`break` 권장
  **이유1**. 코드의 의도를 명확하게 표현할 수 있음
  **이유2** .예상치 못한 상황에 더 안정적임
