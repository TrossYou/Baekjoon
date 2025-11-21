import java.util.Scanner;

public class BAEK_S2_1012 {
	static int M,N,K;
	static boolean[][] map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for (int time = 0; time < T; time++) {
			M=sc.nextInt();
			N=sc.nextInt();
			K=sc.nextInt();
			int res = 0;
			map = new boolean[M][N];
			for (int i = 0; i < K; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				map[x][y] = true;
			}

			for(int x=0;x<M;x++) {
				for(int y=0;y<N;y++) {
					if(map[x][y]) {
						res++;
						dfs(x,y);
					}
				}
			}
			System.out.println(res);
		}
	}
	
	public static void dfs(int x, int y) {
		map[x][y] = false;
		int[] dx = { 0, 0, -1, 1 };
		int[] dy = { -1, 1, 0, 0 };

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			if (nx < 0 || nx >= M || ny < 0 || ny >= N)
				continue;
			if (map[nx][ny]) {
				dfs(nx, ny);
			}
		}
	}
}
