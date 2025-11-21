## 📝 문제 요약

> NxN 색종이가 0(하얀색), 1(파란색)으로 채워져 있을 때,
> 전체 종이를 “한 색으로만 이루어진 정사각형”이 될 때까지 계속해서 4등분으로 나누고,
> 최종적으로 얻어진 **하얀색 색종이 개수**와 **파란색 색종이 개수**를 구하는 문제

---

## 💡 1. 나의 접근 방식 & 핵심 아이디어

- **알고리즘/자료구조:**
  - **입력 배열**: 전역 2차원 배열 `colorMap`
  - **결과 카운트**: 전역 변수 `white`, `blue`
  - 분할 정복(재귀) 함수:
    - `calcSqure(int x, int y, int size)`
      → (x,y)를 좌상단으로 하고, 한 변 길이가 `size`인 정사각형을 처리
  - 색 일치 여부 확인 함수:
    - `isUniformColor(int x, int y, int size)`
- **시간/공간 복잡도:**
  - **시간**
    - 입력: O(N^2) → NxN 모든 칸을 한 번씩 입력
    - 계산(재귀 + 색 확인):
      - 각 단계에서 4등분하면서, 각 영역이 쪼개질수록 검사하는 칸 수가 줄어듦
      - 전체적으로 볼 때, **각 칸은 상수 번 정도만 검사됨**
        ⇒ 총 계산량도 **O(N^2)** 정도
    - N 최대 128(2^7) → N^2의 최대: 2^14 → 16,384 ⇒ 1초 충분
  - **공간**
    - `colorMap`: **O(N^2)**
    - 재귀 호출 스택: 최악 깊이 **log_2(N)** (N이 2의 거듭제곱이라)
      → 매우 작음
- **핵심 로직:**
  1. `calcSqure(x, y, size)`에서 먼저 `isUniformColor(x, y, size)`로 **현재 정사각형이 하나의 색으로만 이루어져 있는지 검사**
  2. 모두 같은 색이면:
     - `colorMap[x][y]`가 0이면 `white++`, 1이면 `blue++`
     - 바로 리턴 (더 이상 쪼개지 않음)
  3. 하나라도 다른 색이 섞여 있으면:
     - `size`를 절반(`half = size / 2`)로 줄인 뒤, 현재 정사각형을 네 부분으로 나누어 재귀 호출
       - `(x, y)`
       - `(x + half, y)`
       - `(x, y + half)`
       - `(x + half, y + half)`
  4. 시작은 전체 종이 `(0, 0, N)`에서 `calcSqure(0, 0, N)` 호출

---

## ✨ 2. 나의 최종 코드

- 코드 보기

  ```jsx
  import java.util.*;

  public class BAEK_S2_2630 {
       static int[][] colorMap;
       static int white, blue;

      public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);
          int N = sc.nextInt();
          colorMap = new int[N][N];

          for (int x = 0; x < N; x++) {
              for (int y = 0; y < N; y++) {
                  colorMap[x][y] = sc.nextInt();
              }
          }

          calcSquare(0, 0, N);

          System.out.println(white);
          System.out.println(blue);
      }

      static void calcSquare(int x, int y, int size) {
          if (isUniformColor(x, y, size)) {
              if (colorMap[x][y] == 0) white++;
              else blue++;
              return;
          }

          int half = size / 2;
          calcSquare(x, y, half);
          calcSquare(x + half, y, half);
          calcSquare(x, y + half, half);
          calcSquare(x + half, y + half, half);
      }

      static boolean isUniformColor(int x, int y, int size) {
          int mainColor = colorMap[x][y];
          for (int i = 0; i < size; i++) {
              for (int j = 0; j < size; j++) {
                  if (colorMap[x + i][y + j] != mainColor) return false;
              }
          }
          return true;
      }
  }

  ```

---

## 🤔 3. 문제 회고 (Retrospective)

### 🐾 3-1. 오류 해결 과정 (Troubleshooting Log)

- **1차시도(맞았습니다!!)**

  - `calcSqure`를 재귀함수로 구현
  - 처음 버전에서는 `calcSqure` 안에서 색 일치 검사와 4분할을 한번에 처리
  - `size /= 2`로 인자를 수정하면서 재귀 호출하는 구조

  ```jsx
  import java.util.*;

  public class BAEK_S2_2630 {
  	public static int[][] colorMap;
  	public static int white, blue;

  	public static void calcSquare(int sx, int sy, int size) {
  		int mainColor = colorMap[sx][sy];
  		for (int i = 0; i < size; i++) {
  			for (int j = 0; j < size; j++) {
  				if(colorMap[sx+i][sy+j] != mainColor) {
  					size /= 2;
  					calcSquare(sx,sy,size);
  					calcSquare(sx+size,sy,size);
  					calcSquare(sx,sy+size,size);
  					calcSquare(sx+size,sy+size,size);
  					return;
  				}
  			}
  		}
  		if(mainColor==0) white++;
  		else blue++;
  	}
  	...
  }

  ```

- **2차시도(맞았습니다!!) - 리팩토링**

  - 색 일치 여부 확인을 `isUniformColor`로 분리
  - `size /= 2` 대신 `int half = size / 2` 사용해서 기존 `size`를 훼손하지 않도록 개선

  ```jsx
  import java.util.*;

  public class BAEK_S2_2630 {
  	 ...
  	static void calcSquare(int x, int y, int size) {
  		if(isUniformColor(x, y, size)) {
  			if(colorMap[x][y] == 0) white++;
  			else blue++;
  			return;
  		}

  		int half = size / 2;
  		calcSquare(x,y,half);
  		calcSquare(x+half,y,half);
  		calcSquare(x,y+half,half);
  		calcSquare(x+half,y+half,half);
  	}

  	static boolean isUniformColor(int x, int y, int size) {
  		int mainColor = colorMap[x][y];
  		for(int i = 0;i < size; i++) {
  			for(int j = 0; j< size; j++) {
  				if(colorMap[x+i][y+j] != mainColor) return false;
  			}
  		}
  		return true;
  	}
  }
  ```

### **🌱 3-2. 새롭게 알게 된 점 (Learning Points)**

-

### 🧐 3-3. 더 궁금한 점 & 다음 목표 (Further Questions)

-
