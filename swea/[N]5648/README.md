<aside>

## 📘 학습한 내용

- 시뮬레이션
- `PriorityQueue` 활용
- 부동 소수점(float) 연산 회피 기법 → 2배
</aside>

## 📝 문제 요약

> 2차원 평면에서 이동하는 N개의 원자들이 주어질 때,
> 
> 
> **충돌하여 소멸하는 원자들의 에너지**를 구하는 시뮬레이션 문제.
> 
> - **조건**: 상하좌우 이동, 1초에 1만큼 이동, 두 개 이상의 원자가 충돌 시 소멸 및 에너지 방출.
> - **제약**: **N ≤1,000**, 좌표 범위 **-1,000 ~ 1,000.**

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
    - **PriorityQueue(우선순위 큐)**: 충돌 사건을 시간 순서대로 처리
    - 매 초마다 원자를 이동시키는 대신, 충돌이 일어날 예정인 사건을 미리 계산해 큐에 넣고
    시간 순으로 꺼내서 처리
- **시간/공간 복잡도:**
    - 시간 복잡도: **O(N^2)** (모든 쌍 비교) + **O(KlogK)** (큐 정렬 및 처리, K는 충돌 사건 수)
    - N = 1,000 이므로 N^2은 10^6회 연산 → 속도  빠름
- **핵심 로직:**
    1. **모든 충돌 쌍 미리 계산**
        
        : 현재 살아있는 원자들끼리 만날 가능성이 있는 경우를 모두 계산하여 큐에 삽입
        
    2. **좌표 2배 확장**
        
        : 0.5초 단위 충돌을 처리하기 위해, `float` 대신 시간을 2배로 늘려 `int` 연산으로 처리
        
    3. **Lazy Evaluation (지연 처리)**: 큐에서 꺼낸 시점에 이미 소멸된(`isPoped`) 원자가 포함된 사건이면 무시

---

## ✨ 2. 나의 최종 코드

### 핵심 구현 코드

- **전역변수 & 클래스 구현**
    
    ```java
    static List<Set<Atom>> Atoms; // 방향별 원자 관리
    static PriorityQueue<Conflict> conQueue; // 충돌 이벤트 큐
    static int N, totalE;
    
    // 1. 원자 클래스: 소멸 여부(isPoped) 관리가 핵심
    static class Atom {
        int x, y, e; 
        boolean isPoped; // 이미 터진 원자인지 확인하는 플래그
    
        Atom(int x, int y, int e){
            this.x = x; this.y = y; this.e = e;
            isPoped = false;
        }
    }
    
    // 2. 충돌 이벤트 클래스: 시간 순 정렬을 위해 Comparable 구현
    static class Conflict implements Comparable<Conflict> {
        int time; // 정수 연산을 위해 2배 확장된 시간 사용
        Atom atomA, atomB;
    
        public Conflict(int time, Atom atomA, Atom atomB) {
            this.time = time;
            this.atomA = atomA;
            this.atomB = atomB;
        }
    
        @Override
        public int compareTo(Conflict other){
            // 시간이 작은 순서(먼저 일어난 충돌)대로 정렬
            return Integer.compare(this.time, other.time);
        }
    }
    ```
    
- **모든 원자 쌍 순회 & 결과 도출**
    
    ```java
    static void play(){
        // 3. 모든 원자 쌍을 비교하여 잠재적 충돌 이벤트를 모두 큐에 삽입 (O(N^2))
        for(int dir = 0 ; dir < 4 ; dir++){
            for(Atom atom : Atoms.get(dir)){
                findConflict(atom, dir);
            }
        }
        // 4. 시간 순서대로 이벤트를 꺼내며 실제 충돌 처리
        calcE();
    }
    ```
    
- **충돌 예측 로직: 방향별로 만날 수 있는 조건만 필터링**
    
    ```java
    // 5. 충돌 예측 로직: 방향별로 만날 수 있는 조건만 필터링
    static void findConflict(Atom a1, int dir){
        // 예시: 상(Up) 방향 원자 처리
        if (dir == 0) {
            // vs 하(Down): 같은 열, 상대가 위에 있어야 함 -> 수직 충돌
            for(Atom a2 : Atoms.get(1)){
                if(a1.x == a2.x && a2.y > a1.y){
                    int time = a2.y - a1.y; // 상대 속도 2 -> 거리/2가 시간이나, 2배 확장하므로 거리 그대로 시간 사용
                    conQueue.add(new Conflict(time, a1, a2));
                }
            }
            // ... (좌/우 대각선 충돌 로직 등 생략) ...
            // 대각선 충돌 시: 거리가 같아야 만남. time = 2 * (거리)
        }
        // ... (다른 방향 케이스 생략) ...
    }
    ```
    

- **충돌 처리 및 에너지 계산**
    
    ```java
    // 6. 충돌 처리 및 에너지 계산 (핵심 로직)
    static void calcE(){
        while(!conQueue.isEmpty()){
            Conflict curr = conQueue.poll();
            int currentTime = curr.time;
            
            // 다중 충돌 처리를 위한 임시 Set
            Set<Atom> sameTimeAtoms = new HashSet<>(); 
    
            // 유효성 검사: 둘 다 아직 살아있는 원자여야 함
            if(!curr.atomA.isPoped && !curr.atomB.isPoped){
                sameTimeAtoms.add(curr.atomA);
                sameTimeAtoms.add(curr.atomB);
            }
            
            // 7. 동시 충돌 처리: 큐의 다음 이벤트가 현재 시간과 같다면 함께 처리
            while(!conQueue.isEmpty() && currentTime == conQueue.peek().time){
                Conflict next = conQueue.peek(); // 일단 확인만 (poll X)
                // 이미 터진 원자가 포함된 이벤트라면 제거하고 무시
                if(next.atomA.isPoped || next.atomB.isPoped) {
                    conQueue.poll(); 
                    continue;
                }
                // 유효한 동시 충돌이라면 추가
                next = conQueue.poll();
                sameTimeAtoms.add(next.atomA);
                sameTimeAtoms.add(next.atomB);
            }
    
            // 확정된 원자들 폭발 처리
            for(Atom atom: sameTimeAtoms){
                totalE += atom.e;
                atom.isPoped = true; // 사망 플래그 On
            }
        }
    }    
    ```
    

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

1. **원자 소멸 시점 문제**
    - **초기 접근***:* 충돌 시간을 고려하지 않고 **단순히 충돌 가능한 모든 쌍**을 찾아서 에너지 합산.
    - **문제점**: A와 B가 1초에 충돌해 사라졌는데, 2초 뒤에 A와 C가 충돌하는 이벤트가 처리되는 오류 발생.
    - **해결**: `PriorityQueue`를 도입하여 시간이 빠른 순서대로 이벤트를 정렬하고, `isPoped` 플래그를 통해 이미 사라진 원자의 이벤트는 **무시(Skip)**하도록 변경.
2. **`PriorityQueue`와 `float` 비교 문제**
    - **문제점**: 0.5초 단위 충돌이 발생할 수 있어 시간을 `float`로 처리하려 했으나, `compareTo` 오버라이딩 시 `float` 반환이 불가능하고 부동 소수점 오차 위험이 있음.
    - **해결**: **좌표계 확장 기법** 사용. 모든 거리 계산과 시간을 **2배**로 늘려 `int`형으로 변환.
        
        (0.5초 → 1단위 시간)
        

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **이벤트 기반 시뮬레이션**: 단순히 `time++` 하며 모든 원자를 이동시키는 것보다,
    
     '**사건(Event)**'을 중심으로 큐에 넣고 처리하는 것이 훨씬 효율적일 수 있다.
    
    → 시뮬레이션 코드로 작성한 친구보다 약 12배 빠름 
    
- **좌표 2배 확장 (Scale-up)**: 격자판 문제에서 '중간 지점(0.5)' 이슈가 생길 때,
    
    `float`를 쓰기보다 좌표를 2배로 늘리면 정수 연산만으로 깔끔하게 해결된다.
    
- **동시성 처리**: `PriorityQueue`에서 `peek()`을 활용하여 같은 우선순위(시간)를 가진 이벤트를 묶어서 처리

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- N이 엄청 크다면?