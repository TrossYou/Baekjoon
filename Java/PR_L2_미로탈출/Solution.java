package Java.PR_L2_미로탈출;

import java.util.*;

class Solution {
    int size, ans;
    boolean isFind;
    int[][] cntMap;    
    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, 1, 0, -1};

    public int solution(String[] maps) {
        size = maps[0].length();

        // 시작 좌표
        int sr = -1;
        int sc = -1;
        // 레버 좌표
        int lr = -1;
        int lc = -1;
        // 출구 좌표
        int er = -1;
        int ec = -1;

        // 각 좌표 
        for(int i = 0; i < size; i++){
            for(int j = 0; j<size ;j++){
                switch (maps[i].charAt(j)) {
                    case 'S' -> {
                        sr = i;
                        sc = j;
                    }
                    case 'L' -> {
                        lr = i;
                        lc = j;
                    }
                    case 'E' -> {
                        er = i;
                        ec = j;
                    }
                    default -> {
                    }
                }
            }
        }

        // 채워 나가기
        cntMap = new int[size][size];
        for(int i =0; i < size; i++) Arrays.fill(cntMap[i], -1); // 방문하지 않은 좌표 -1로 채우기

        // 1) 레버까지 최소 
        fillCnt(sr, sc, lr, lc, 0, maps);
        return cntMap[0][0];

        // 2) 레버에서 exit까지 최소
        isFind = false;
        fillCnt(lr, lc, er, ec, 0,maps);
        return ans == 0 ? -1 : ans;
    }

    public void fillCnt(int curR, int curC, int targetR, int targetC,int v, String[] maps){
        if(isFind) return;

        cntMap[curR][curC] = v;
        if(curR == targetR && curC == targetC){ // 끝나면 반환
            ans += v;
            isFind = true;
            // initCntMap();
            return;
        }

        for(int dir = 0; dir<4; dir++){
            int nr = curR + dr[dir];
            int nc = curC + dc[dir];

            if(nr > 0 && nr < size && nc > 0 && nc < size){
                if(maps[nr].charAt(nc) == 'X' || cntMap[nr][nc] != -1) continue;
                fillCnt(nr, nc, targetR, targetC,v+1,maps);
            }
        }
    }

    public void initCntMap(){
        for(int i = 0; i< size; i++) Arrays.fill(cntMap, -1);
    }
}