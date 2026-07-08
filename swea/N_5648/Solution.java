import java.io.*;
import java.util.*;

public class Solution {
    static List<Set<Atom>> Atoms; // 방향별 원자 집합 (0:상, 1:하, 2:좌, 3:우)
    static PriorityQueue<Conflict> conQueue; // 충돌을 시간순으로 관리
    static int N, totalE;

    // 원자 정보를 담는 클래스
    static class Atom {
        int x,y,e; 
        boolean isPoped; // 소멸 여부 체크

        Atom(int x, int y, int e){
            this.x = x;
            this.y = y;
            this.e = e;
            isPoped = false;
        }
    }

    // 충돌을 담는 클래스
    static class Conflict implements Comparable<Conflict> {
        int time; // 충돌 발생 시간 (정수연산을 위해 2배 확장)
        Atom atomA, atomB;

        public Conflict(int time, Atom atomA, Atom atomB) {
            this.time = time;
            this.atomA = atomA;
            this.atomB = atomB;
        }

        // 우선순위 큐 정렬을 위해 오버라이드
        @Override
        public int compareTo(Conflict other){
            return Integer.compare(this.time, other.time);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=T ;tc++){
            N = Integer.parseInt(br.readLine());
            totalE = 0;
            Atoms = new ArrayList<>();
            conQueue = new PriorityQueue<>();

            // Atoms 초기화
            for(int i = 0 ; i < 4; i++){
                Atoms.add(new HashSet<>());
            }

            // 원자 입력 & 세팅
            for(int i = 0;i<N;i++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());

                Atoms.get(dir).add(new Atom(x, y, e)); // 각 dir셋(Atoms)에 원자 추가
            }

            play();
            sb.append('#').append(tc).append(' ').append(totalE).append('\n');
        }
        System.out.print(sb);
    }

    static void play(){
        // 모든 원자 쌍을 비교하여 발생 가능한 충돌 큐에 삽입 (O(N^2))
        for(int dir = 0 ; dir < 4 ; dir++){
            for(Atom atom : Atoms.get(dir)){
                findConflict(atom, dir);
            }
        }
        // 충돌 시간 바탕으로 에너지 계산
        calcE();
    }

    // 충돌 탐색
    static void findConflict(Atom a1, int dir){
        switch (dir) {
            case 0: // 상 방향 원자
                // 상 - 하 충돌 (같은 X축, 상대가 위에 있음)
                for(Atom a2 : Atoms.get(1)){
                    if(a1.x == a2.x && a2.y > a1.y){
                        int time = a2.y - a1.y; //int형을 하기 위해 2배
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }
                // 상 - 좌 충돌 (대각선)
                for(Atom a2 : Atoms.get(2)){
                    if(a2.x > a1.x && a2.y > a1.y && (a2.x - a1.x == a2.y - a1.y)){ 
                        int time = 2*(a2.y-a1.y);
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }   
                // 상 - 우 충돌 (대각선)
                for(Atom a2 : Atoms.get(3)){
                    if(a1.x > a2.x && a2.y > a1.y && (a1.x - a2.x == a2.y - a1.y)){
                        int time = 2*(a2.y-a1.y);
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }   break;
            case 1: // 하 방향 원자
                // 하 - 좌 충돌
                for(Atom a2 : Atoms.get(2)){
                    if(a2.x > a1.x && a1.y > a2.y && (a2.x - a1.x == a1.y - a2.y)) {
                        int time = 2*(a1.y - a2.y);
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }   
                // 하 - 우 충돌
                for(Atom a2 : Atoms.get(3)){
                    if(a1.x > a2.x && a1.y > a2.y && (a1.x - a2.x == a1.y - a2.y)){
                        int time = 2*(a1.y - a2.y);
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }   break;
            case 2: // 좌 방향 원자
            // 좌 - 우 충돌 (같은 Y축, 상대가 왼쪽에 있음)
                for(Atom a2 : Atoms.get(3)){
                    if(a1.y == a2.y && a1.x > a2.x){
                        int time = a1.x - a2.x;
                        conQueue.add(new Conflict(time, a1, a2));
                    }
                }   break;
        }
    }

    // 충돌 시간 바탕으로 에너지 계산하는 함수
    static void calcE(){
        while(!conQueue.isEmpty()){
            // 동시 충돌이 있을 수 있으니, 여러개 poll()
            Conflict curr = conQueue.poll();
            int currentTime = curr.time;

            Set<Atom> sameTimeAtoms = new HashSet<>(); // 같은 시간에 충돌한 원자들 -> 다중 충돌 처리

            // 둘 다 최초 충돌인 경우에만 삽입
            if(!curr.atomA.isPoped && !curr.atomB.isPoped){
                sameTimeAtoms.add(curr.atomA);
                sameTimeAtoms.add(curr.atomB);
            }
            
            // 동일 시간 다른 충돌 이벤트도 확인
            while(!conQueue.isEmpty() && currentTime == conQueue.peek().time){ // 불필요한 삭제/삽입 제거하기 위해 peek()로 먼저 확인
                Conflict next = conQueue.poll();
                if(!next.atomA.isPoped && !next.atomB.isPoped){
                    sameTimeAtoms.add(next.atomA);
                    sameTimeAtoms.add(next.atomB);
                }
            }

            // 실제 충돌 확정된 원자들 에너지 계산 & 소멸 처리
            for(Atom atom: sameTimeAtoms){
                totalE += atom.e;
                atom.isPoped = true;
            }
        }
    }
}
