import java.io.*;
import java.util.*;

public class Solution {
    private static int[] numbers;
    private static int N,minNum,maxNum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        
        for(int tc=1;tc<=T;tc++){
            N = Integer.parseInt(br.readLine());
            int[] operators = new int[4];
            st = new StringTokenizer(br.readLine());
            
            for(int i=0;i<4;i++){
                operators[i] = Integer.parseInt(st.nextToken());
            }

            numbers = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0;i<N;i++){
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            int answer = play(operators, numbers);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }   

    static int play(int[] operators, int[] numbers){
        maxNum = Integer.MIN_VALUE;
        minNum = Integer.MAX_VALUE;

        // 연산자 순서 저장 리스트
        ArrayList<Integer> operList = new ArrayList<>();
        recursive(operators, operList);



        return maxNum - minNum;
    }

    static void recursive(int[] operators, ArrayList<Integer> operList){
        if(operList.size() == N-1){
            int res = calculate(operList);
            if(res < minNum) minNum = res;
            if(res > maxNum) maxNum = res;
            return;
        }
        
        for(int i = 0 ; i < 4; i++){
            if(operators[i] > 0){
                operators[i]--; // 해당 연산자 사용
                operList.add(i);
                recursive(operators, operList);
                // 백트래킹: 원상복구
                operList.remove(operList.size() - 1);
                operators[i]++;
            }
        }
    }

    static int calculate(ArrayList<Integer> operList){
        int res = numbers[0];
        for(int i = 1 ; i < N  ;i++){
            int oper = operList.get(i-1); // 순서대로 접근
            switch (oper) {
                case 0:
                    res += numbers[i];
                    break;
                case 1:
                    res -= numbers[i];
                    break;                
                case 2:
                    res *= numbers[i];
                    break;                
                case 3:
                    res /= numbers[i];
                    break;
            }
        }
        return res;
    }
}
