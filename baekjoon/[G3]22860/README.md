<aside>

## 📘 학습한 내용

- `computeIfAbsent`: `key`의 존재를 확인 후, 없으면 생성하는 메서드
- `ArrayList`도 탐색 시간이 **O(1)**이다!
</aside>

## 📝 문제 요약

> 계층적인 폴더 구조와 파일 정보를 입력받아, 
특정 경로(쿼리) 하위에 있는 파일의 **종류 수**와 **총 개수**를 구하기. (하위 폴더의 파일도 포함)
> 

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
    - `Map<String, Folder>` : 이름으로 특정 폴더 객체에 즉시 접근하기 위한 `Map`
    - `Map<String, List<Folder>>` : 부모-자식 간의 트리 계층 구조를 저장하는 `Map`
    - `HashSet<String>`: 파일 이름의 중복을 자동으로 제거하여 파일 종류 수 계산
    - **DFS**(Bottom-Up 재귀): 말단 폴더부터 위로 올라오며 데이터를 병합(**Merge**)하는 방식
- **시간/공간 복잡도:**
    - 입력: **O(N+M)**
    - 병합: 모든 폴더를 한 번씩 방문 → **O(N)**
    - 공간: 폴더 및 파일 정보 저장 → **O(N + M)**

---

## ✨ 2. 나의 최종 코드
### 핵심 로직:

1. 입력 시 부모(P)와 자식(F) 폴더가 처음 등장할 때 `computeIfAbsent`를 통해 객체를 생성
    - `folderMap`: Folder 객체 저장
    - `childFolderMap`: 폴더의 부모-자식 관계 트리
2. `Folder` 클래스 내부에 `isMerged` 플래그를 두어 쿼리 중복 계산 방지
3. `mergeChildFolder` 재귀함수를 통해 자식 폴더의 `HashSet`과 `fileCount`를 누적 합산

### 핵심 구현 코드

- **Folder 클래스** 구현
    
    ```jsx
    static class Folder {
        String name; // name: 폴더 이름
        int fileCount; // fileCount: 파일 개수(merge계산 시, 자식 파일 수도 반영됨)
        HashSet<String> files; // files: 파일 이름 set
        boolean isMerged; // query에서 이미 files가 계산 되었는지 여부
    
        Folder(String name) {
          this.name = name;
          files = new HashSet<>();
        }
    
        void addFile(String name) {
          files.add(name); // O(1)
          fileCount++;
        }
    }
    ```
    
- **입력값 처리**: Folder 객체 저장 & 부모자식 관계 트리
    
    ```java
    // 1. 입력 받기(폴더 구조, 파일 세팅)
    for (int i = 0; i < N + M; i++) {
      st = new StringTokenizer(br.readLine());
      String P = st.nextToken();
      String F = st.nextToken();
      int C = Integer.parseInt(st.nextToken());
    
      // 부모 폴더 없다면 생성
      folderMap.computeIfAbsent(P, Folder::new);
      childFolderMap.computeIfAbsent(P, k -> new ArrayList<>());
    
      // F가 폴더일 때
      if (C == 1) {
        // 자식 폴더 없다면 생성
        Folder fInst = folderMap.computeIfAbsent(F, Folder::new);
        childFolderMap.computeIfAbsent(F, k -> new ArrayList<>());
    
        // P에 F 추가
        childFolderMap.get(P).add(fInst);
      }
      // F가 폴더가 아닐 때
      else {
        folderMap.get(P).addFile(F);
      }
    }
    ```
    

- **쿼리 처리**: mergeChildFolder()로 자식 폴더부터 파일 계산
    
    ```java
    // Bottom-Up 방식의 폴더 병합 로직
    static void mergeChildFolder(String targetName) {
      Folder folder = folderMap.get(targetName);
      if (folder.isMerged)
        return; // 이미 계산 끝났으면 종료
    
      // 하위 폴더 불러오기
      List<Folder> children = childFolderMap.get(targetName);
      if (children.isEmpty())
        return;
    
      // 말단 폴더인지 확인 -> 애초에 호출 시에도 검사
      for (Folder child : children) {
        mergeChildFolder(child.name); // 자식 먼저 계산
        folder.files.addAll(child.files); // 자식의 파일 종류 합치기
        folder.fileCount += child.fileCount; // 자식의 파일 총 개수 합치기
      }
      folder.isMerged = true; // 계산 완료 체크
    }
    ```
    

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **Generic Array Creation 에러**: `List<String>[]`와 같은 제네릭 배열 생성 시 자바의 타입 소거 원칙 때문에 에러 발생. 와일드카드 사용 후 형변환하거나 `List<List<>>` 구조로 변경하여 해결 가능함을 파악.
- **NullPointerException**: 입력 단계에서 부모 폴더가 정의되기 전 파일이 먼저 추가될 때 객체가 없어 에러 발생. `putIfAbsent` 또는 `computeIfAbsent`를 사용하여 객체 존재 여부를 먼저 보장하도록 수정.
- **병합 누락**: 부모가 자식의 파일만 가져오고 '손자'의 파일을 놓치는 문제 발생. 재귀 호출 순서를 `merge(자식)` 후 `addAll(자식데이터)` 순서로 변경하여 Bottom-Up으로 데이터가 올라오게 함.

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

- **ArrayList vs 배열**: `ArrayList`도 내부적으로 배열을 사용하여 인덱스 접근 시 $O(1)$의 성능을 보장하며, 제네릭 사용 시 배열보다 훨씬 안전하다는 점을 확인.
- **computeIfAbsent vs putIfAbsent**:
    - `putIfAbsent`: 이미 값이 있어도 인자로 넘긴 `new` 객체를 일단 생성함.
    - `computeIfAbsent`: 키가 없을 때만 람다식을 실행하여 객체를 생성하므로 메모리 효율 및 성능 면에서 더 유리함.
- **Memoization**: `isMerged` 플래그를 활용해 트리 탐색 시 중복 연산을 획기적으로 줄이는 방법 습득.

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

- 쿼리를 돌기 전, 루트인 `main`에 대해 `mergeChildFolder("main")`를 선제적으로 실행하여 전**체 트리를 사전 병합**하고 쿼리 시에는 결과만 출력하는 방식으로 리팩토링해보기.
