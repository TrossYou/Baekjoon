import java.util.*;
import java.io.*;

public class Solution {
    static int N, ans, cnt, maxDate, groupId;
    static int[][] map, groupMap;
    static int[] groupSize;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer sb = new StringBuffer();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            N = Integer.parseInt(br.readLine());
            ans = 0; // 100일 중 가장 많았던 덩어리 수(정답)
            cnt = 0; // 현재 남아있는 덩어리의 개수
            groupId = 0; // 그룹 아이디
            maxDate = 0; // 치즈맛 최고값 
            map = new int[N][N]; // 치즈 입력 map
            groupMap = new int[N][N]; // 치즈 그룹 번호 map
            groupSize = new int[N*N/2+1]; // 각 그룹 사이즈 - 그룹은 1부터 최대 N*N / 2개

            for(int i=0; i<N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] > maxDate) maxDate = map[i][j];
                }
            }

            play();
            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
    }

    // 시뮬레이션 함수
    static void play(){
        // 날짜를 감소시키며 그루핑
        for(int date = maxDate ; date > 0 ; date--){
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(map[i][j] == date){
                        grouping(j, i);    
                    }
                }
            }
            // 순회 후 ans 갱신
            if(ans < cnt){
                ans = cnt;
            }
            if(ans == N*N/2) break; // 최대값(N*N/2) 도달하면 가지치기
        }
    }

    // (x,y)를 주변 그룹 상태에 따라 그루핑하는 함수
    static void grouping(int x, int y){
        int nearBy = 0; // 주변에 있는 그룹 개수
        int bitMask = 0; // 그룹의 위치 표시
        for(int idx=0; idx<4; idx++){
            int nx = x + dx[idx];
            int ny = y + dy[idx];
            // idx방향에 그룹이 있다면,
            if(nx >=0 && nx < N && ny >= 0 && ny < N && groupMap[ny][nx] != 0){
                nearBy++;
                bitMask |= (1<<idx); // 비트마스크로 방향 저장
            }
        }

        // 주변에 그룹이 하나도 없다면 -> 새로 생성
        if(nearBy == 0){
            groupMap[y][x] = ++groupId; // 그룹 추가
            groupSize[groupId]++; // 그룹 크기 세팅
            cnt++; // 총 그룹 수 추가
        }else if(nearBy == 1){ // 주변에 그룹이 하나라면
            for(int i=0; i<4; i++){ 
                if((bitMask & (1<<i)) != 0){ // 그룹의 방향을 찾아서
                    groupMap[y][x] = groupMap[y+dy[i]][x+dx[i]]; // 해당 그룹으로 지정
                    groupSize[groupMap[y][x]]++; // 그룹 사이즈 관리
                    break;
                }
            }
        }else{
            groupMerge(x, y, bitMask); // 주변에 그룹이 여러개라면, 상황에 따라 다름
        }
    }

    // 주변 그룹을 합병하는 함수
    static void groupMerge(int x, int y, int bitMask){
        int maxDir = -1;
        int maxSize = 0;
        // 주변 중 가장 큰 그룹 찾기
        for(int i=0; i<4; i++){
            if((bitMask & (1<<i))!=0){
                if(maxSize < groupSize[groupMap[y+dy[i]][x+dx[i]]]){ 
                    maxSize = groupSize[groupMap[y+dy[i]][x+dx[i]]];
                    maxDir = i;
                }
            }
        }

        int group = groupMap[y+dy[maxDir]][x+dx[maxDir]]; // 가장 큰 그룹 번호
        groupMap[y][x] = group;
        for(int i = 0; i < 4; i++){
            if(i == maxDir) continue;
            if((bitMask & (1<<i)) != 0 && groupMap[y+dy[i]][x+dx[i]] != group){
                cnt--;
                changeGroup(x+dx[i],y+dy[i], groupMap[y+dy[i]][x+dx[i]], group); // i위치의 그룹을 다 가장 큰 그룹으로 변경
            }
        }
    }

    // from그룹 좌표를 group그룹으로 합병
    static void changeGroup(int sx, int sy, int from, int group){
        groupMap[sy][sx] = group;
        groupSize[from]--;
        groupSize[group]++;
        for(int  i = 0; i<4; i++){
            int nx = sx + dx[i];
            int ny = sy + dy[i];
            if(nx >= 0 && nx < N && ny >= 0 && ny < N && groupMap[ny][nx] == from){
                changeGroup(nx, ny, from, group);
            }
        }
        return;
    }
}
