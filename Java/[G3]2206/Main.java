import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] map;
    static int N,M;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new boolean[N+1][M+1];

        for(int i = 1; i <= N ; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= M ; j++){
                map[i][j] = st.nextToken().equals("1")? false : true; // 이동 가능 여부 
            }
        }

        System.out.println(bfs());
    }

    static int bfs(){
        int answer = Integer.MAX_VALUE;
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(1,1,1,false));
        
        while(!q.isEmpty()){
            Node node = q.poll();
            if(node.x == N && node.y == M) answer = Math.min(answer, node.dist+1);
            for(int i = 0 ; i < 4 ; i++){
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if(nx <= 0 || nx > N || ny <= 0 || ny > M) continue;
                if(node.isSmashed){
                    //이미 벽을 부순 상황
                    if(map[nx][ny]){
                        q.add(new Node(nx, ny, node.dist+1,node.isSmashed));
                    }
                }else {
                    if(map[nx][ny]){
                        q.add(new Node(nx, ny, node.dist+1, node.isSmashed));
                    }else{
                    //벽 부수고 진행
                        q.add(new Node(nx, ny, node.dist+1, true));
                    }
                }
            }
        }
        return answer;
    }
}

class Node {
    int x, y, dist;
    boolean isSmashed;

    public Node(int x, int y, int dist, boolean isSmashed) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.isSmashed = isSmashed;
    }
}