# 📘 학습한 내용

<aside>
💡

- **Parametric Search** 
최적화 문제(최대 성능?) -> 결정 문제(가능해? Y/N)로 변환 -> 이분 탐색 해결
- **Java Collection**`Map`, `TreeSet` (자동 정렬), `tailSet` (범위 검색), `Iterator` (안전한 삭제)
- **Optimization**
선형 탐색(Linear Scan) -> 이분 탐색(Binary Search)으로 시간 단축
</aside>

---

# 📝 문제 요약

> **목표**: 예산 **B** 내에서 **n**개 부품(종류별 1개) 조립 시, **최소 성능(Quality)의 최댓값** 구하기
> 

---

# 💡 1. 나의 접근 방식 & 핵심 아이디어

### 알고리즘 / 자료구조

- **알고리즘**: **이분 탐색 (Binary Search)**
    - 성능1씩 감소(선형) -> 시간 초과(TLE)
    - **이분 탐색** 도입 -> 탐색 범위 10억을 **O(log N)**으로 단축
- **자료구조**: `HashMap<String, TreeSet<Part>>`
    - Key: 부품 종류 / Value: 부품 목록
    - `TreeSet`: 성능(Quality) 오름차순 자동 정렬

### 시간 / 공간 복잡도

- **시간 복잡도**: **O(N * log(MaxQuality))**
    - 이분 탐색(약 30회) × 검증 로직(N)
- **공간 복잡도**: **O(N)**

### 핵심 로직

1. **전처리 (Pre-processing)**
    - 선택지 1개인 부품 -> **즉시 구매 & 예산/범위 갱신** (탐색 최적화)
2. **파라메트릭 서치**
    - `mid` = 목표 성능
    - `isPossible(mid)` -> 가능하면 성능UP (`low = mid + 1`), 불가능하면 DOWN
3. **검증 함수 (`isPossible`)**
    - `tailSet`으로 **목표 성능 이상**인 부품 추출
    - 추출된 부품 중 **최저가(Min Price)** 선택 -> 예산 확인

---

# ✨ 2. 나의 최종 코드

### 전역변수 & 클래스

```java
// 부품 정보 (성능 기준 오름차순 정렬)
static class Part implements Comparable<Part>{
    String name;
    int price, quality;

    public Part(String name, int price, int quality) {
        this.name = name;
        this.price = price;
        this.quality = quality;
    }

    @Override
    public int compareTo(Part other){
        return Integer.compare(this.quality, other.quality);
    }
}

private static Map<String, TreeSet<Part>> partTypeMap;
private static int n, b;
```

### 메인 로직 & 이분 탐색

```java
public static void main(String[] args) throws IOException {
    // ... 입력 설정 ...

    int T = Integer.parseInt(br.readLine());
    while(T-- > 0){
        // ... n, b 입력 ...
        partTypeMap = new HashMap<>();

        // 1. 입력 & Map 구성
        for(int i=0; i<n; i++){
            // ... 파싱 ...
            partTypeMap.computeIfAbsent(type, k -> new TreeSet<>())
                       .add(new Part(name, price, quality));
        }

        // 2. 전처리: 선택지 1개인 부품 확정
        int maxQ = 1_000_000_000;
        int rest = b;

        Iterator<String> keys = partTypeMap.keySet().iterator();
        while (keys.hasNext()) {
            String type = keys.next();
            if(partTypeMap.get(type).size() == 1) {
                Part p = partTypeMap.get(type).first();
                maxQ = Math.min(maxQ, p.quality); // 상한선 갱신
                rest -= p.price;
                keys.remove();
            }
        }

        // 3. 이분 탐색 (Parametric Search)
        int ans = 0;
        int low = 0, high = maxQ;

        while(low <= high){
            int mid = (low + high) / 2;

            if(isPossible(mid, rest)){
                ans = mid;      // 정답 후보
                low = mid + 1;  // 성능 높여보기
            } else{
                high = mid - 1; // 성능 낮추기
            }
        }
        sb.append(ans).append('\\n');
    }
    System.out.print(sb);
}
```

### 검증 함수 (isPossible)

```java
static boolean isPossible(int limitQ, int rest){
    for (String type : partTypeMap.keySet()) {
        TreeSet<Part> parts = partTypeMap.get(type);

        // limitQ 이상인 부품들(View) 추출
        Part dummy = new Part("", 0, limitQ);
        SortedSet<Part> validParts = parts.tailSet(dummy);

        if(validParts.isEmpty()) return false; // 불가능

        // validParts 중 '최저가' 탐색
        int minPrice = Integer.MAX_VALUE;
        for(Part p: validParts){
            minPrice = Math.min(minPrice, p.price);
        }

        rest -= minPrice;
        if(rest < 0) return false; // 예산 초과
    }
    return true;
}
```

---

# 🤔 3. 문제 회고

### 🐾 3-1. 트러블 슈팅

1. **ConcurrentModificationException**
    - **원인**: `for-each` 루프 도중 `map.remove()` 직접 호출
    - **해결**: `Iterator` 사용 -> `iter.remove()`로 안전 삭제
2. **시간 초과 (TLE)**
    - **원인**: 선형 탐색 `(High -> Low, --1)`
    - **해결**: 이분 탐색 (Binary Search) 적용 -> **O(log N)**
3. **논리 오류 (Greedy)**
    - **원인**: `ceiling()` 사용 -> 성능 만족하는 것 중 **가장 낮은 성능** 선택 (비쌀 수 있음)
    - **해결**: `tailSet()` -> 성능 만족하는 것 중 **가장 싼 가격** 선택

### 🌱 3-2. 배운 점

- **TreeSet 활용**: `tailSet(val)`을 통해 특정 범위 데이터를 **O(1)**(View 반환)에 접근 가능
- **파라메트릭 서치**:  최적화 문제 -> 결정 문제로 변경

### 🧐 3-3. 궁금한 점

-