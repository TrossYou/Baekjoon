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
            for(int j = 0 ; j < N ; j++) answer = Math.max(answer, initialMap[i][j]);
        }
        
        dfs(0,initialMap);
        System.out.println(answer);
    }

    static void dfs(int depth, int[][] map){
        if(depth == 5) return ; // depth가 5인 경우 종료

        for(int dir = 0 ; dir <= 3 ; dir++){
            // map 깊은 복사 -> dir마다 갱신
            int[][] nowMap = new int[N][N];
            for(int i = 0 ; i < N ; i++){
                nowMap[i] = map[i].clone();
            }
            int[][] nextMap = move(dir, nowMap);
            if(isEqual(map, nextMap)) continue;
            dfs(depth + 1, nextMap);
        }
    }

    static int[][] move(int dir, int[][] map){
        int[][] localMap = rotate(dir, map);
        int[][] moved = new int[N][N];

        // 왼쪽으로 이동 및 병합
        for(int i = 0 ; i < N ; i++){
            int idx = 0; // 채울 위치
            for(int j = 0 ; j < N ; j++){
                if(localMap[i][j] == 0) continue;
                if(idx != 0 && localMap[i][j] == moved[i][idx-1]){
                    moved[i][idx-1] *= 2;
                }else{
                    moved[i][idx++] = localMap[i][j];

                }
            }
        }

        // 다시 원래 방향으로 회전
        return dir == 0 ? moved : rotate(4 - dir, moved);
    }

    static boolean isEqual(int[][] aMap,int[][] bMap){
        for(int i = 0 ; i < N ; i++){
            for(int j = 0 ; j < N ; j++){
                if(aMap[i][j] != bMap[i][j]) return false;
            }
        }
        return true;
    }

    //시계방향으로 돌리는 메서드 90도 t번 회전
    static int[][] rotate(int t, int[][] map){
        t %= 4; // 4번 회전은 원위치
        int[][] result = map;
        while(t-- > 0){
            int[][] next = new int[N][N];
            for(int i = 0 ; i < N ; i++){
                for(int j = 0 ; j < N ; j++){
                    next[i][j] = result[N-j-1][i];
                }
            }
            result = next;
        }
        return result;
    }
}
