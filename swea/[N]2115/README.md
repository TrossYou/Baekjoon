<aside>
📘

## 학습한 내용

- 비트마스크를 이용한 **부분집합 완전탐색**
</aside>

## 📝 문제 요약

> N x N 크기의 벌통 격자에서
두 명의 일꾼이 **가로로 연속된 M개의 벌통**을 각각 선택하여 꿀을 채취한다.
> 
> - 두 일꾼의 선택 구간은 **겹치면 안 된다.**
> - 각 일꾼은 선택한 M칸 중 **일부만 골라** 꿀을 채취할 수 있다.
> - 단, 채취한 꿀의 총합은 **C를 초과할 수 없다.**
> - 수익은 각 벌통의 꿀의 양의 **제곱의 합.**
> 
> 이때 두 일꾼이 얻을 수 있는 **최대 수익**을 구하는 문제
> 

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
    - 완전탐색 (Brute Force)
    - 부분집합 탐색 (Bitmask)
- **시간/공간 복잡도:**
    - 시간 복잡도:
    - 공간 복잡도:
- **핵심 로직:**
    1. 각 일꾼의 선택은 “구간 시작점(Point)”으로 표현
        - `(r, c)` → `(r, c+M-1)` 까지 가로 M칸
    2. 가능한 모든 시작점을 구한 뒤, 겹치지 않게 두 개를 선택
    3. 각 구간에 대해 
        - M칸 중 **부분집합(subset)**을 탐색
        - `합 ≤ C`를 만족하는 경우 중, `제곱합`이 최대가 되는 값 선택
    4. 두 일꾼의 수익을 더해 전체 최댓값 갱신

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드 (부분집합 + 점수 계산)

```jsx
static int getMaxScore(Point p) {
    int r = p.r; 
    int c = p.c;

    int maxScore = 0;
    
    // 비트마스크로 부분집합 탐색
    for(int mask = 1 ; mask < (1<<M) ; mask++){
        int sum = 0;
        int squareSum = 0;

        for(int i = 0 ; i < M ; i++){
            if((mask & (1<<i)) != 0){
                int v = map[r][c+i];
                sum += v;
                squareSum += v * v;
            }
        }
        
        if(sum <= C){
            maxScore = Math.max(maxScore, squareSum);
        }
    }

    return maxScore;
}

```

<aside>
💡

📌 **M ≤ 5** 이기 때문에

- DP보다 **비트마스크 완전탐색이 구현도 간단하고 실수도 적음**
</aside>

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- `Point[]` 배열을 재사용해서 `List`에 추가
    
    → 모든 원소가 **같은 참조**를 가리키는 버그 발생
    
    - 해결:
    
    ```java
    containers.add(newPoint[]{
    	new Point(a.r, a.c),
    	new Point(b.r, b.c)
    });
    ```
    
    → **항상 새로운 객체/배열 생성**
    

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- 작은 제약(M ≤ 5)에서는
    - **DP보다 완전탐색이 더 안전하고 빠른 선택**일 수 있음

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- **DP(Knapsack)** 활용한 풀이