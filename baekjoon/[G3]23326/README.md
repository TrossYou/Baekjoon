<aside>

## 📘 학습한 내용

- `TreeSet`을 이용한 효율적인 탐색 (Red-Black Tree)
- Java `TreeSet`의 `ceiling()` 메서드 활용 및 Null 처리
- `Set.remove()`의 반환값을 이용한 코드 최적화
- 함수 호출 오버헤드 및 리터럴 타입에 따른 성능 미세 팁
</aside>

## 📝 문제 요약

> 원형으로 연결된 **N**개의 구역 중 특정 구역이 '**명소**'로 지정됩니다.
> 
> 
> 현재 위치에서 시계 방향으로 가장 가까운 명소까지의 거리를 출력하거나, 
> 
> 명소 지정 상태 변경, 현재 위치 이동 등의 쿼리를 효율적으로 처리해야 합니다.
> 

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:** `TreeSet` (이진 검색 트리)
- **시간/공간 복잡도:**
    - 쿼리당 **O(log N)** (TreeSet 탐색 및 수정)
    - 전체 시간 복잡도: **O(QlogN)**
- **핵심 로직:**
    1. 명소의 위치를 정렬된 상태로 유지 - `TreeSet`
    2. 현재 위치 `curLoc` 보다 크거나 같은 첫 번째 명소를 찾기 위해 `TreeSet.ceiling()` 사용
    3. 만약 현재 위치 이후에 명소가 없다면, 다시 1번 위치부터 가장 가까운 명소(`first()`)를 계산

---

## ✨ 2. 나의 코드

### 핵심 구현 코드

1. 명소(`spot`)을 **`TreeSet`** 에 저장
    
    ```java
    TreeSet<Integer> spotSet = new TreeSet<>();
    for (int i = 1; i <= N; i++) {
        if (st.nextToken().equals("1")) spotSet.add(i);
    }
    ```
    

1. 원형 배열 인덱스 구하는 함수: `getIdx(int i)`
    
    ```java
    static int getIdx(int i){
    	  int idx = i % N;
    	  if(idx == 0) idx = N;
    	  return idx;
    }
    ```
    

1. 1, 2액션 처리
    
    ```java
    char action = st.nextToken().charAt(0); // 액션 입력(1,2,3)
    if(action == '1'){
        int idx = Integer.parseInt(st.nextToken());
        if(!spotSet.remove(idx)) spotSet.add(idx); // spotSet에 없으면, 추가 / 있으면 제거만
    } else if(action == '2'){
        curLoc = getIdx(curLoc + Integer.parseInt(st.nextToken())); 
    }
    ```
    

1. 3번 액션 함수: `thirdAction()`
    
    ```java
    static int thirdAction(){
        if(spotSet.isEmpty()) return -1; // spot이 없는 경우 -1
        Integer next = spotSet.ceiling(curLoc); // curLoc이상 중 최소
        if(next != null){ // curLoc 이상 spot이 있는 경우
            return next - curLoc; 
        }
        return (N - curLoc) + spotSet.first(); // curLoc 이상 spot이 없는 경우, 가장 앞 장소로 계산
    }
    ```
    

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

**[1차시도]  `boolean` 배열(isSpot)  - spot이면 true, 아니면 false 저장**

`thirdAction` 수행 시 3번 액션마다 배열 전체를 순회하므로 O(N)이 소요됨 → **시간초과**   
</br>

**[2차시도] `HashSet` - spot인덱스를 spotSet에 저장**

정렬되어 있지 않아, 가까운 명소 찾을 때 결국 Set 전체 순회 (O(N)) → **시간초과**

</br>

**[3차시도] `TreeSet` - spot인덱스를 spotSet에 저장**

쿼리에서 값 탐색 시간을 줄이기 위해, 정렬 기반 set 도입 → 성공


### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

1. **TreeSet의 특성**
    
    레드-블랙 트리 기반으로 추가/삭제는 **O(logN)**이지만, 정렬된 상태를 유지하여 범위를 탐색하는 `ceiling()`, `floor()` 등의 메서드를 매우 빠르게 사용할 수 있음
    
2. **Wrapper 객체 주의**
    
    `ceiling()`은 찾는 값이 없을 경우 `null`을 반환하므로, 이를 받는 변수는 기본형 `int`가 아닌 참조형 `Integer`로 선언하여 `NullPointerException`을 방지해야 함.
    
3. **Set 활용**
    
    `if(contains) remove else add` 로직을 `if(!remove) add` 로 간결&탐색 횟수 줄이기 가능.
    
4. **미세 성능 최적화**
    - 잦은 함수 호출이 오버헤드를 발생시킬 수 있어, 단순 계산(1,2액션)은 인라인화
    - `“\n”`(String) 보다 `‘\n’`(char)를 사용하는 것이 미세하게나마 성능에 유리함

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 원형 배열 구조에서 인덱스를 처리할 때 `(curLoc + x - 1) % N + 1`과 같은 보정 공식 외에 더 직관적인 처리 방법이 있을지
  
- 다른 이진 탐색 기반 자료구조들과 `TreeSet`의 성능 차이