import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    // 상하좌우 이동
    final static int[] dx = {0, 1, 0, -1};
    final static int[] dy = {-1, 0, 1, 0};

    static int[][] map;              // 0: 빈칸, 1: 벽, 2: 바이러스
    static List<Point> zeroList;     // 빈 칸 좌표 목록
    static List<Point> virusList;    // 바이러스 좌표 목록
    static int N, M, zeroNum;
    static List<Point> walls = new ArrayList<>(3);

    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {

        readInput();
        int answer = placeThreeWalls();
        System.out.println(answer);

    }    

    // 입력 읽기 및 빈 칸, 바이러스 위치 저장
    static void readInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
    
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        zeroList = new ArrayList<>();
        virusList = new ArrayList<>();
        map = new int[N][M];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 0){ 
                    zeroNum++;
                    zeroList.add(new Point(j, i));
                }
                else if(map[i][j] == 2) virusList.add(new Point(j, i));
            }
        }
    }

    // 빈 칸 중 3곳을 선택하여 벽을 세우는 모든 경우의 수 탐색
    static int placeThreeWalls(){
        int maxRes = 0;

        // 조합: C(zeroNum, 3)
        for(int i = 0; i < zeroNum - 2; i++){
            for(int j = i + 1; j < zeroNum - 1; j++){
                for(int k = j + 1; k < zeroNum; k++){

                    walls.clear();
                    walls.add(zeroList.get(i));
                    walls.add(zeroList.get(j));
                    walls.add(zeroList.get(k));

                    maxRes = Math.max(maxRes, calculateSafeArea(walls));
                }
            }
        }
        return maxRes;
    }
    // 벽을 세운 후 안전 영역 계산
    static int calculateSafeArea(List<Point> walls){
        int safeArea = zeroNum - 3;

        // 벽 설치
        for(Point wall : walls){
            map[wall.y][wall.x] = 1;
        }
         
        // 바이러스 확산 (DFS)
        int totalInfected = 0;
        boolean[][] visited = new boolean[N][M];
        for(Point virus : virusList){
            totalInfected += dfs(virus.x, virus.y, visited);
        }

        // 벽 복구
        for(Point wall : walls){
            map[wall.y][wall.x] = 0;
        }

        return safeArea - totalInfected; // 안전 구역 - 감염된 부분
    }
    
// DFS로 바이러스 확산 시뮬레이션
    static int dfs(int x, int y, boolean[][] visited){
        int infectedCount = 0;

        // 상하좌우 탐색
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            // 범위 내 + 빈 칸 + 미방문
            if(0 <= nx && nx < M && 0 <= ny && ny < N && map[ny][nx] == 0 && !visited[ny][nx]) {
                infectedCount++;
                visited[ny][nx] = true;
                infectedCount += dfs(nx, ny, visited);
            }
        }

        // 상하좌우 다 탐색 끝나면 infectedCount 반환
        return infectedCount;
    }

}
