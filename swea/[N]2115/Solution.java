import java.io.*;
import java.util.*;

public class Solution {
    // 시작점 
    static class Point {
        int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
    }

    static int N, M, C;
    static int[][] map;
    static int maxValue;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                }
            }

            maxValue = 0;
            play();

            sb.append("#").append(tc).append(" ").append(maxValue).append("\n");
        }
        System.out.print(sb);
    }

    static void play() {
        List<Point[]> containers = getTwoContainers();
        for (Point[] pair : containers) {
            int score = getScore(pair);
            if (score > maxValue) maxValue = score;
        }
    }

    static List<Point[]> getTwoContainers() {
        List<Point[]> containers = new ArrayList<>();

        // 시작점이 될 수 있는 후보 점 모두 저장
        List<Point> starts = new ArrayList<>();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c <= N - M; c++) {
                starts.add(new Point(r, c));
            }
        }

        // 후보 점 중 겹치지 않는 2가지 점 뽑아 containers에 추가
        for (int i = 0; i < starts.size()-1; i++) {
            for (int j = i + 1; j < starts.size(); j++) {
                Point a = starts.get(i);
                Point b = starts.get(j);

                if (!isOverlapped(a, b)) {
                    containers.add(new Point[]{ new Point(a.r, a.c), new Point(b.r, b.c) });
                }
            }
        }

        return containers;
    }

    // 두 점의 구역이 겹치는 지 확인하는 함수
    static boolean isOverlapped(Point a, Point b) {
        if (a.r != b.r) return false;
        int aStart = a.c;
        int aEnd = a.c + M - 1;
        int bStart = b.c;
        int bEnd = b.c + M - 1;
        return !(aEnd < bStart || bEnd < aStart); // 겹치면 true
    }

    // 각 페어에 대해서 점수 계산하는 함수
    static int getScore(Point[] pair) {
        int total = 0;

        for (Point p : pair) {
            total += getMaxScore(p); 
        }

        return total;
    }

    static int getMaxScore(Point p) {
        int r = p.r; 
        int c = p.c;

        int maxScore = 0;
        
        // 비트마스크로 부분집합 -> 비트마스크가 모든 부분 집합을 찾아줌.
        for(int mask = 1 ; mask < (1<<M) ; mask++){
            int sum = 0;
            int squareSum = 0;

            for(int i = 0 ; i < M ; i++){
                // mask에 i번째 인자가 있다는 말
                if((mask&(1<<i)) != 0){
                    int v = map[r][c+i];
                    sum += v;
                    squareSum += v * v;
                }
            }
            
            if(sum <= C){
                maxScore = Math.max(maxScore, squareSum);
            }
        }

        return maxScore;
    }
}
