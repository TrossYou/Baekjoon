import java.io.*;
import java.util.*;

public class Main {
    private static TreeSet<Integer> spotSet; // 명소 위치 treeSet -> 자동 정렬
    private static int curLoc,N,Q;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 장소의 개수
        Q = Integer.parseInt(st.nextToken()); // 쿼리의 개수
        curLoc = 1; // 도현이의 현재 위치

        spotSet = new TreeSet<>(); // 명소 위치 
        st = new StringTokenizer(br.readLine());
        for(int i = 1  ; i <= N ; i++){
            if(st.nextToken().charAt(0) == '1') spotSet.add(i); // i장소가  명소라면 spotSet에 추가
        }

        for(int q = 0 ; q < Q ; q++){
            st = new StringTokenizer(br.readLine());
            char action = st.nextToken().charAt(0); // 액션 입력(1,2,3)
            if(action == '1'){
                int idx = Integer.parseInt(st.nextToken());
                if(!spotSet.remove(idx)) spotSet.add(idx); // spotSet에 없으면, 추가 / 있으면 제거만
            } else if(action == '2'){
                curLoc = getIdx(curLoc + Integer.parseInt(st.nextToken())); 
            } else {
                sb.append(thirdAction()).append('\n');
            }
        }
        System.out.print(sb);
    }    
    
    // 3번 액션 함수
    static int thirdAction(){
        if(spotSet.isEmpty()) return -1; // spot이 없는 경우 -1
        Integer next = spotSet.ceiling(curLoc); // curLoc이상 중 최소
        if(next != null){ // curLoc 이상 spot이 있는 경우
            return next - curLoc; 
        }
        return (N - curLoc) + spotSet.first(); // curLoc 이상 spot이 없는 경우, 가장 앞 장소로 계산
    }

    // 원형 배열에서 위치 반환하는 함수
    static int getIdx(int i){
        int idx = i % N;
        if(idx == 0) idx = N;
        return idx;
    }
}
