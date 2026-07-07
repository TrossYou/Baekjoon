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
