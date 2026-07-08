import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static final int T = 10;
    static final int MAP_SIZE = 100;
    static final int LEFT = -1;
    static final int RIGHT = 1;
    static final int NO_BRIDGE = 0;

    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for(int tc=1; tc<=T ; tc++){
            br.readLine(); // tc 입력값 버리기
            map = new int[MAP_SIZE][MAP_SIZE];
            int targetX = -1;

            for(int i = 0 ; i < MAP_SIZE ; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < MAP_SIZE ; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] == 2) targetX = j;
                }
            }
            
            int answer = traceUp(targetX);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb);
    }

    // targetX로부터 거슬러 올라가 답을 찾는 함수
    static int traceUp(int targetX){
        int x = targetX;
        int y = MAP_SIZE - 1;

        while(y > 0){
            y--;
            int dir = hasSideBridge(x,y);
            if(dir != 0) x = moveSide(x,y,dir);
        }

        return x;
    }

    // 좌우측에 다리를 확인해서 방향과 함께 반환하는 함수
    static int hasSideBridge(int x, int y){
        if(x > 0 && map[y][x-1] == 1) return LEFT;
        else if(x < MAP_SIZE-1 && map[y][x+1] == 1) return RIGHT; 
        return NO_BRIDGE;
    }

    // dir방향으로 이동 후 x 좌표 반환하는 함수
    static int moveSide(int x, int y, int dir){
        while(0 <= x + dir && x + dir < MAP_SIZE && map[y][x+dir] == 1){
            x += dir;
        }
        return x;
    }
}
