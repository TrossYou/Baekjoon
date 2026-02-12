import java.io.*;
import java.util.*;

public class Solution2 {
    static int[] trees; // 트리의 높이를 받는 배열
    static List<Queue<Integer>> remainTree; // 남은 트리의 높이 - 3의 배수로 나눈 나머지별로 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            int N = Integer.parseInt(br.readLine());
            int max = 0;
            int totalDate = 0;
            remainTree = new ArrayList<>();
            for(int i = 0 ; i<3 ; i++) remainTree.add(new LinkedList<>());

            trees = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                trees[i] = Integer.parseInt(st.nextToken());
                if(trees[i] > max) max = trees[i]; // 최대 높이 구하기
            }

            // 로직
            // 3으로 나눈 나머지에 따라, 연속으로 채울 수 있는 날짜(최소)를 연산으로 가능
            // 3으로 나눈 나머지가 0 -> (짝/홀) +2*몫 (끝은 시작과 동일)
            // 3으로 나눈 나머지가 1 -> (홀) + 2*몫 + 1 (짝)
            // 3으로 나눈 나머지가 2 -> (짝) + 2*몫 + 1 (홀)
            // 따라서, 차이를 바로바로 계산해서, 짝 홀이 일치하면 바로바로
            // 일치하지 않는 애는 따로 Queue에 저장 -> 추후 계산
            boolean isOdd = true; // 홀수면 true, 짝수면 false
            for(int i=0; i<N; i++){
                int diff = max - trees[i];
                if(diff == 0) continue; // diff가 0이면 바로 패스
                int quot = diff/3;
                int remain = diff%3;

                // 홀수로 시작할 때 -> 나머지 0이거나 1가능
                if(isOdd){
                    if(remain == 2){
                        // Queue에도 계산할 나무가 없는 경우
                        if(remainTree.get(0).isEmpty() && remainTree.get(1).isEmpty()){ 
                            
                            remainTree.get(2).add(diff); // i번 높이차 저장
                        }else if(remainTree.get(1).isEmpty()){ // 나머지 0만 있는 경우
                            int tmp = remainTree.get(0).poll();
                            totalDate += 2 * (tmp/3);
                        }else{ // 둘 다 있거나, 1만 있는 경우 -> 1부터 소비
                            int tmp = remainTree.get(1).poll();
                            totalDate += 2 * (tmp/3) + 1;
                            isOdd = !isOdd; // 짝홀 변경됨
                        }
                    }else{
                        if(remain == 1){
                            isOdd = !isOdd; // 나머지가 1이면 짝홀 변경
                            totalDate += 2*quot +1;
                        }else{
                            totalDate += 2 * quot;
                        }
                    }
                }else{ // 짝수로 시작할 때 -> 나머지 0이거나 2 가능
                    if(remain == 1){
                        // Queue에도 계산할 나무가 없는 경우
                        if(remainTree.get(0).isEmpty() && remainTree.get(2).isEmpty()){
                            remainTree.get(1).add(diff);
                        }else if(remainTree.get(2).isEmpty()){ // 나머지가 0만 있는 경우
                            int tmp = remainTree.get(0).poll();
                            totalDate += 2 * (tmp/3);
                        }else{
                            int tmp = remainTree.get(2).poll();
                            totalDate += 2 * (tmp/3) + 1;
                            isOdd = !isOdd;
                        }
                    }else{
                        if(remain == 2){
                            isOdd = !isOdd; // 나머지가 2일때만 짝홀 변경
                            totalDate += 2 * quot + 1;
                        }else{
                            totalDate += 2 * quot;
                        }
                    }
                }
            }

            // 디버깅 해보니 하나의 큐만 남음
            // 큐가 모두 비었을 때
            if(!remainTree.get(0).isEmpty() || !remainTree.get(1).isEmpty() || !remainTree.get(2).isEmpty()){ 
                if(!remainTree.get(0).isEmpty()){
                    // 나머지가 0인 큐만 남는다면..
                    for(int tmp: remainTree.get(0)){
                        totalDate += 2 * (tmp/3);
                    }
                }else if(!remainTree.get(1).isEmpty()){
                    // 나머지가 1인 큐만 남는다면..
                    if(!isOdd) totalDate++; // 짝수라면 일단 하루 추가
                    for(int tmp: remainTree.get(1)){
                        totalDate += 2 * (tmp/3) + 1;
                        //계속 짝홀 하나씩 낭비됨
                        totalDate++;
                    }
                    totalDate--; // 마지막 계산제거
                }else{
                    // 나머지가 2인 큐만 남는다면..
                    if(isOdd) totalDate++;
                    for(int tmp: remainTree.get(2)){
                        totalDate += 2 * (tmp/3) + 1;
                        totalDate++; // 짝홀 변경
                    }
                    totalDate--; // 마지막 계산 제거
                }
            }

            sb.append('#').append(tc).append(' ').append(totalDate).append('\n');
        }
        System.out.print(sb);
    }
}
