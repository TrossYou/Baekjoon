import java.io.*;
import java.util.*;

public class Solution {
    // 플레이어 클래스
    static class Player{
        int x,y;

        Player(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
 
    // BC 클래스
    static class BC{
        int x,y,c,p; 
 
        BC(int x, int y, int c, int p){
            this.x = x; // x좌표
            this.y = y; // y좌표
            this.c = c; // 충전 범위
            this.p = p; // 충전 성능
        }
 
        // 플레이어가 이 BC 범위 안에 있는지 확인
        boolean isInside(Player p){
            int distance = Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
            return distance <= this.c;
        }
    }
 
    static final int MAP_SIZE = 10;
    static Player pA, pB;
    static int M, A, totalSum; // M: 이동 시간, A: BC개수, totalSum: 충전 총 양
    static List<Integer> aDirList, bDirList; // A와 B의 이동 방향 리스트
    static BC[] BCArr; //BC 정보 배열
    // 사용자 이동 방향 (순서대로 이동x, 상,우,하,좌)
    static int[] dx = new int[]{0, 0, 1, 0, -1};
    static int[] dy = new int[]{0, -1, 0, 1, 0};
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st1, st2;
 
        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T ; tc++){
            st1 = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st1.nextToken()); // 이동 시간
            A = Integer.parseInt(st1.nextToken()); // BC 개수
 
            st1 = new StringTokenizer(br.readLine()); // A의 경로
            st2 = new StringTokenizer(br.readLine()); // B의 경로
 
            // 전역변수 초기화
            pA = new Player(1, 1);
            pB = new Player(MAP_SIZE, MAP_SIZE);
            totalSum = 0;
            aDirList = new ArrayList<>();
            bDirList = new ArrayList<>();
            BCArr = new BC[A];
 
            // 0초에서도 충전 가능 전처리
            aDirList.add(0);
            bDirList.add(0);
            // [최적화] 한 번에 두 인풋을 처리 -> O(M)
            for(int i = 0 ; i < M ; i++){
                aDirList.add(Integer.valueOf(st1.nextToken()));
                bDirList.add(Integer.valueOf(st2.nextToken()));
            }
 
            // BC 정보 추가
            for(int i = 0 ; i < A ; i++){
                st1 = new StringTokenizer(br.readLine());
                BCArr[i] = new BC(Integer.parseInt(st1.nextToken()),Integer.parseInt(st1.nextToken()),Integer.parseInt(st1.nextToken()),Integer.parseInt(st1.nextToken()));
            }
 
            // 시간에 따라 플레이어 이동
            for(int i = 0 ; i <= M; i++){
                int dirA = aDirList.get(i);  // i시간에 A방향
                int dirB = bDirList.get(i);  // i시간에 B방향
                 
                // 각 플레이어 이동
                pA.x += dx[dirA];
                pA.y += dy[dirA];
                pB.x += dx[dirB];
                pB.y += dy[dirB];
 
                play();
            }
 
            sb.append('#').append(tc).append(' ').append(totalSum).append('\n');
        }
        System.out.print(sb);
    }
 
    // 현재 플레이어 위치에서 최대값 구해서 더하는 함수
    static void play(){
        List<BC> candA = new ArrayList<>(); // A가 가능한 BC 후보리스트(추후 정렬)
        List<BC> candB = new ArrayList<>();
 
 
        // 각 BC를 순회하며 A,B의 최대 충전량 구하기
        for(BC bc : BCArr){
            // A플레이어가 가능하다면 A후보리스트에 저장
            if(bc.isInside(pA)) candA.add(bc);
            // B플레이어가 가능한다면 B후보리스트에 저장
            if(bc.isInside(pB)) candB.add(bc); 
        }
 
        // A,B 둘 다 없음
        if(candA.isEmpty() && candB.isEmpty()) return;
 
        // B만 있는 경우
        if(candA.isEmpty()){
            candB.sort((bc1, bc2) -> bc2.p - bc1.p);
            totalSum += candB.get(0).p;
        }else if(candB.isEmpty()){ // A만 있는 경우
            candA.sort((bc1, bc2) -> bc2.p - bc1.p);
            totalSum += candA.get(0).p;
        }else{
            candA.sort((bc1, bc2) -> bc2.p - bc1.p);
            candB.sort((bc1, bc2) -> bc2.p - bc1.p);
            // 두 최선이 다르면
            if(candA.get(0) != candB.get(0)){
                totalSum += candA.get(0).p;
                totalSum += candB.get(0).p;
            }else{ // 두 최선이 같음
                int power = candA.get(0).p; //최선값
                // 둘 다 유일한 값
                if(candA.size() == 1 && candB.size() == 1){
                    totalSum += power;
                }else if(candA.size() == 1){ // B는 후보 더 있음
                    totalSum += candB.get(1).p; // B선태
                    totalSum += power; // A선택
                }else if(candB.size() == 1){ // A는 후보 더 있음
                    totalSum += candA.get(1).p; //A선택
                    totalSum += power; //B선택
                }else{ // A, B 둘 다 후보 있음
                    int secondA = candA.get(1).p;
                    int secondB = candB.get(1).p;
                     
                    int maxSecond = Math.max(secondA, secondB);
                    totalSum += maxSecond;
                    totalSum += power;
                }
            }
        }
    }
}