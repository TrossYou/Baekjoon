# SWEA 5658 - 보물상자 비밀번호

### 학습한 내용

`Arrays.sort` 내림차순 / `Integer.parseInt` 진수 변환

---

## 📝 문제 요약

> N개의 16진수 숫자가 정사각형 뚜껑의 네 변에 적혀 있다. (각 변에 N/4개씩)
> 뚜껑을 시계방향으로 회전할 때마다 숫자가 한 칸씩 이동하며, 각 변의 숫자는 하나의 16진수 수를 나타낸다.
> 모든 회전 상태에서 만들 수 있는 수 중 **K번째로 큰 수**를 10진수로 출력한다. (중복 제거)

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** 슬라이딩 윈도우 + HashSet + 정렬
- **시간/공간 복잡도:** O(N log N)
- **핵심 로직:**
  1. 입력 문자열을 2배로 이어 붙여 (`str + str`) 회전을 나누기 연산 없이 처리한다.
  2. `i`를 0부터 N-1까지 이동하면서 길이 `N/4`인 부분 문자열을 잘라 `HashSet`에 넣어 중복을 제거한다.
  3. `Integer` 배열로 변환 후 내림차순 정렬하여 `arr[K-1]`을 출력한다.

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- 문자열 2배 + 슬라이딩 윈도우로 모든 회전 상태 수집
  ```java
  String str = br.readLine();
  str += str; // 2배로 만들어서 나누기 연산 제거

  Set<String> set = new HashSet<>();
  for (int i = 0; i < N; i++) {
      set.add(str.substring(i, i + length));
  }
  ```
- `Integer[]`로 변환 후 내림차순 정렬
  ```java
  Integer[] arr = new Integer[set.size()];
  int i = 0;
  for (String s : set) {
      arr[i++] = Integer.parseInt(s, 16);
  }
  Arrays.sort(arr, Comparator.reverseOrder());
  ```
- K번째 큰 수 출력
  ```java
  sb.append('#').append(tc).append(' ').append(arr[K - 1]).append('\n');
  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 처음에 `int[]`로 배열을 선언했더니 `Comparator.reverseOrder()` 적용이 안 됨 → `Integer[]`로 변경하여 해결

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- `Arrays.sort()` 내림차순 정렬 시 `Comparator.reverseOrder()`는 **객체 타입**에만 사용 가능하므로 `int[]` 대신 **`Integer[]`** 을 사용해야 한다.
  ```java
  Integer[] arr = ...;
  Arrays.sort(arr, Comparator.reverseOrder()); // ✅
  ```
- `Integer.parseInt(String s, int radix)`의 두 번째 인자로 **진수**를 지정할 수 있다. 16진수 문자열을 10진수 `int`로 바로 변환 가능하다.
  ```java
  Integer.parseInt("1A3", 16); // → 419
  Integer.parseInt("FF",  16); // → 255
  ```

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- `TreeSet`을 사용하면 중복 제거 + 정렬을 한 번에 처리할 수 있을지 시도해보기
