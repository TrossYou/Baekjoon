<aside>
📘

## 학습한 내용

- 슬라이딩 윈도우로 연속 구간을 **O(1)** 갱신 → 전체 **O(N)** 해결
- `HashSet` 재생성 방식은 **O(N·k)** + GC 부담으로 비효율적
- **카운팅 배열 + distinct 관리**가 훨씬 빠르다
- 쿠폰은 매번 체크하지 않고 **초기 상태에 미리 포함**시키면 분기 제거 가능
</aside>

## 📝 문제 요약

> 원형 초밥 벨트에서 k개를 연속 선택할 때, 쿠폰 초밥을 포함하여 만들 수 있는
> 
> 
> **서로 다른 종류의 최대 개수**를 구하는 문제.
> 

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
    - 슬라이딩 윈도우 + 카운팅 배열(`int[] cntArr`)
- **시간/공간 복잡도:**
    - 시간: **O(N)**
        - 초기 윈도우 세팅 O(k) + 윈도우 이동 O(N)
    - 공간: **O(d + N)**
        - 카운팅 배열 O(d), 원형 처리를 위한 2N 배열 O(2N)
- **핵심 로직:**
    1. 원형 배열을 선형으로 처리하기 위해 `circleArr`를 **2N 크기**로 생성
        - `circleArr[0..N-1]` 입력
        - `circleArr[N..N+k-2]`에 앞부분 복사(윈도우가 끝에서 넘어갈 때 대비)
    2. `cntArr[type]`로 현재 윈도우에 해당 타입이 몇 개 있는지 관리
    3. 초기 윈도우(0..k-1) 세팅 후 윈도우 이동
        
        → 빠지는 초밥 1개 제거
        
        → 들어오는 초밥 1개 추가
        
        - 해당 타입의 개수가 `0↔1` 되는 순간만 distinct 변경
    - 쿠폰은 시작할 때 포함시켜 계산 단순화

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

1. **원형을 선형처럼 처리하기 위해 2N 배열 사용**

```jsx
// 원형을 선형처럼 처리하기 위해 2N 배열 사용
circleArr = new int[2 * N];
for (int i = 0; i < N; i++) {
  circleArr[i] = Integer.parseInt(br.readLine());
  if (i < k - 1) circleArr[N + i] = circleArr[i]; // 끝에서 넘어갈 구간 미리 복사
}
```

1. **슬라이딩 윈도우 적용**

```java
int[] cntArr = new int[d + 1]; // 타입별 개수 카운트

// 쿠폰 초밥을 미리 포함시켜 로직 단순화
cntArr[c]++;
int typeNum = 1;

// 1) 초기 윈도우 세팅 (0..k-1)
for (int i = 0; i < k; i++) {
  int sushiType = circleArr[i];
  if (++cntArr[sushiType] == 1) typeNum++;
}

int maxNum = typeNum;

// 2) 슬라이딩 윈도우 (start = 1..N-1)
for (int i = 1; i < N; i++) {
  int minusType = circleArr[i - 1];
  int plusType = circleArr[i + k - 1];

  if (--cntArr[minusType] == 0) typeNum--;
  if (++cntArr[plusType] == 1) typeNum++;

  if (maxNum < typeNum) maxNum = typeNum;
  if (maxNum == k + 1) break; // 가능한 최댓값이면 조기 종료
}

```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- 초기에는 매 구간마다 `HashSet`으로 종류를 계산 → 시간초과
- 문제는 **구간을 매번 새로 계산**한 것
- 이전 결과를 재사용하는 슬라이딩 윈도우로 변경 후 해결
                

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- distinct 문제는 Set보다 **freq 배열**이 강력하다
- 쿠폰 같은 “항상 포함되는 요소”는 **초기 상태 주입**이 좋은 패턴
- 원형 자료구조는
    - `% N` 처리뿐 아니라, **배열을 펼치는 방식**으로도 깔끔하게 구현 가능
    

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

.
