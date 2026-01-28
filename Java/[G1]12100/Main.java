import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        answer = Integer.MIN_VALUE;

        int[][] initialMap = new int[N][N];
        for(int i = 0 ; i < N ; i++){
            initialMap[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        dfs(0,initialMap);
    }

    static void dfs(int depth, int[][] map){
        if(depth == 5) return ; // depth가 5인 경우 종료
        // map 깊은 복사
        int[][] nowMap = new int[N][N];
        for(int i = 0 ; i < N ; i++){
            nowMap[i] = map[i].clone();
        }

        for(int dir = 1 ; dir <= 4 ; dir++){
            int[][] nextMap = move(dir, nowMap);
            if(isEqual(nowMap, nextMap)) continue;
            dfs(depth + 1, nextMap);
        }
    }

    static int[][] move(int dir, int[][] map){
        int[][] localMap = rotate(dir,map);
        
        // 왼쪽으로 이동 및 계산
        for(int i = 0 ; i < N ; i++){
            for(int j = 0 ; j < N-1 ; j++){
                if(localMap[i][j] == localMap[i][j+1]){
                    localMap[i][j] *= 2;
                }
            }
        }

        // 다시 map 원복

        return new int[N][N];
    }

    static boolean isEqual(int[][] aMap,int[][] bMap){
        return false;
    }

    //시계방향으로 돌리는 메서드 90도 t번 회전
    static int[][] rotate(int t, int[][] map){
        int[][] result = new int[N][N];
        while(t-->0){
            for(int i = 0 ; i < N ; i++){
                for(int j = 0 ; j < N ; j++){
                    result[i][j] = map[N-j][i];
                }
            }
        }
        return result;
    }
}
