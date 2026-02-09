<aside>
📘

## 학습한 내용
**백트래킹(Backtracking)의 정확한 구현 패턴과 자료구조 선택의 중요성**

- 백트래킹에서 상태 복원의 중요성 (선택 → 탐색 → 복원)
- 순열 생성을 위한 재귀 함수 설계
- 자료구조의 특성과 실제 사용 목적의 일치성 검증

</aside>

## 📝 문제 요약

> N개의 숫자와 사칙연산자 카드들이 주어질 때, 연산자를 숫자 사이에 배치하여 만들 수 있는 수식의 최댓값과 최솟값의 차이를 구하는 문제.
> 
> **핵심 조건:**
> - 연산자 우선순위 무시 (왼쪽에서 오른쪽으로 순차 계산)
> - 모든 연산자 카드를 사용해야 함
> - 숫자의 순서는 변경 불가
> - 나눗셈은 소수점 버림

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** 백트래킹(Backtracking), ArrayList
- **시간/공간 복잡도:** O((N-1)!) / O(N)
  - 최악의 경우 N=12일 때 11! ≈ 3,990만 개의 순열 탐색
- **핵심 로직:**
    1. **연산자 순열 생성**: 주어진 연산자 개수를 바탕으로 모든 가능한 배치 순서를 백트래킹으로 생성
    2. **순차 계산**: 생성된 각 순열에 대해 왼쪽부터 오른쪽으로 연산 수행
    3. **최대/최소 추적**: 계산 결과를 비교하여 최댓값과 최솟값 갱신

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

```java
static void recursive(int[] operators, ArrayList<Integer> operList){
    // 종료 조건: N-1개의 연산자를 모두 선택
    if(operList.size() == N-1){
        int res = calculate(operList);
        if(res < minNum) minNum = res;
        if(res > maxNum) maxNum = res;
        return;
    }
    
    // 백트래킹: 각 연산자 종류별로 시도
    for(int i = 0 ; i < 4; i++){
        if(operators[i] > 0){
            operators[i]--;              // 선택
            operList.add(i);
            recursive(operators, operList);
            operList.remove(operList.size() - 1);  // 복원
            operators[i]++;              // 복원
        }
    }
}

static int calculate(ArrayList<Integer> operList){
    int res = numbers[0];
    for(int i = 1 ; i < N  ;i++){
        int oper = operList.get(i-1); // 순서대로 접근
        switch (oper) {
            case 0: res += numbers[i]; break;
            case 1: res -= numbers[i]; break;
            case 2: res *= numbers[i]; break;
            case 3: res /= numbers[i]; break;
        }
    }
    return res;
}
```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **백트래킹 로직 오류**
  - ❌ **문제**: `int[] tmp = operators.clone()`을 for문 밖에서 한 번만 수행하고, while문으로 연산자를 모두 소진하는 구조
  - ✅ **해결**: if문으로 개수 체크 후 재귀 호출 이후 `operators[i]++`로 복원하는 정확한 백트래킹 패턴 적용
  
- **연산자 순서 역전 문제**
  - ❌ **문제**: Stack에서 `pop()`으로 꺼내면 LIFO 특성 때문에 역순으로 연산자가 나옴
  - ✅ **해결**: `get(i-1)`로 인덱스 순서대로 접근하도록 변경

- **최대/최소 갱신 로직 오류**
  - ❌ **문제**: `if-else if` 구조로 인해 결과값이 최솟값이면서 최댓값일 수 있는 경우(예: 첫 번째 계산) 하나만 갱신됨
  - ✅ **해결**: 독립적인 두 개의 if문으로 분리

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **백트래킹의 핵심 = 상태 복원**: 재귀 호출 전후로 상태를 정확히 복원해야 올바른 순열 탐색이 가능함
  - 선택(감소) → 재귀 → 복원(증가) 패턴의 중요성
  
- **재귀의 for문 시작점과 중복 허용**: 각 재귀에서 `for(i=0; i<4; i++)`로 시작하면 같은 연산자를 여러 번 선택 가능 (예: `++`, `--`)
  - 조합이 아닌 순열 문제이므로 매번 처음부터 탐색해야 함

- **자료구조 선택의 의도성**: Stack의 LIFO 특성을 사용하지 않는다면 `ArrayList`가 더 명확
  - Stack은 Vector를 상속받아 불필요한 동기화 오버헤드 존재
  - 코드의 의도를 드러내는 자료구조 선택이 중요

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- **비재귀 구현**: Next Permutation 알고리즘을 이용한 반복문 기반 구현도 가능할 것 같음
  - 성능 차이와 코드 복잡도 트레이드오프 비교해보기
