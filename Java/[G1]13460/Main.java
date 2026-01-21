import java.util.*;
import java.io.*;

public class Main {
    static char[][] map;
    // 순서대로 상우하좌
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0,-1};

    static class State {
        int rx, ry, bx, by, depth;
        State(int rx, int ry, int bx, int by, int depth){
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.depth = depth;
        }
    }

    static String toStr(State s){
        return s.rx+","+s.ry+","+s.bx+","+s.by;
    }

    static int bfs(State iState){
        Queue<State> q = new LinkedList<>();
        q.add(iState);
        Set<String> visited = new HashSet<>();
        visited.add(toStr(iState));
        int answer = Integer.MAX_VALUE;
        
        while(!q.isEmpty()){
            State s = q.poll(); 

            if(s.depth > 10) continue;

            for(int k = 0 ; k < 4 ; k++){
                State ns = roll(s, k);
                if(ns == null) continue;
                if(ns.depth > 10) continue;  // 새 상태가 10 초과면 스킵
                if(map[ns.rx][ns.ry] == 'O'){
                    answer = Math.min(answer, ns.depth);
                    continue;
                } 
                if(visited.add(toStr(ns))){
                    q.add(ns);
                }
            }
        }
    
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // 한 상태를 굴려서 '새 상태'를 반환 
    // 성공시: 새 State 객체
    // 실패시 : null (파랑이 구멍)
    static State roll(State s, int k){
        char first = 'R';
        //상황 별 R,B 우선순위 계산
        switch(k){
            case 0: // 위로 이동 -> x가 더 작은 공 우선
                first = s.rx < s.bx ? 'R' : 'B';
                break;
            case 1: // 우측 이동 -> y가 더 큰 공 우선
                first = s.ry < s.by ? 'B' : 'R';
                break;
            case 2: // 아래로 이동 -> x가 더 큰 공 우선
                first = s.rx < s.bx ? 'B' : 'R';
                break;
            case 3: // 좌측 이동 -> y가 더 작은 공 우선
                first = s.ry < s.by ? 'R' : 'B';
                break;
        }

        int rNx = s.rx + dx[k];
        int rNy = s.ry + dy[k];
        int bNx = s.bx + dx[k];
        int bNy = s.by + dy[k];

        boolean bInHole = false;
        
        if(first == 'R'){
            // red 이동
            while(!(rNx == s.bx && rNy == s.by) && isPassable(rNx, rNy) && map[rNx][rNy] != 'O'){ 
                rNx += dx[k];
                rNy += dy[k];
            }
            if(map[rNx][rNy] != 'O'){
                rNx -= dx[k];
                rNy -= dy[k];
            }

            //blue 이동
            while(!(bNx == rNx && bNy == rNy) && isPassable(bNx, bNy) && map[bNx][bNy] != 'O'){
                bNx += dx[k];
                bNy += dy[k];
            }
            if(map[bNx][bNy] == 'O'){
                bInHole = true;
            } else {
                bNx -= dx[k];
                bNy -= dy[k];
            }
        }else {
            // blue 이동
            while(!(bNx == s.rx && bNy == s.ry) && isPassable(bNx, bNy) && map[bNx][bNy] != 'O'){
                bNx += dx[k];
                bNy += dy[k];
            }
            if(map[bNx][bNy] == 'O'){
                bInHole = true;
            } else {
                bNx -= dx[k];
                bNy -= dy[k];
            }
            // red 이동 (새로운 Blue 위치 체크)
            while(!(rNx == bNx && rNy == bNy) && isPassable(rNx, rNy) && map[rNx][rNy] != 'O'){ 
                rNx += dx[k];
                rNy += dy[k];
            }
            if(map[rNx][rNy] != 'O'){
                rNx -= dx[k];
                rNy -= dy[k];
            }
        }
        // 결과 판정
        if(bInHole) return null;  // Blue 구멍 = 무조건 실패 (Red 상관없이)
        
        return new State(rNx, rNy, bNx, bNy, s.depth+1);
    }

    static boolean isPassable(int x, int y){
        return map[x][y] != '#';
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        int rx = 0, ry = 0, bx = 0, by = 0;
        for(int i = 0 ; i < N ; i++){
            map[i] = br.readLine().toCharArray();
            for(int j = 0 ; j < M ; j++){
                if(map[i][j] == 'R'){
                    rx = i;
                    ry = j;
                }else if(map[i][j] == 'B'){
                    bx = i;
                    by = j;
                }
            }
        }
        State initialState = new State(rx, ry, bx, by, 0);

        System.out.println(bfs(initialState));
    }
}
