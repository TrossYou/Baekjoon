import java.io.*;
import java.util.*;

class Node {
    int start,end;

    Node(int start, int end){
        this.start = start;
        this.end = end;
    }
}

public class Main2 {
    static HashMap<String, Integer> memo = new HashMap<>();

    static int combination(int n, int r) {
        if (r < 0 || r > n) return 0;
        if (r == 0 || r == n) return 1;

        String key = n + "," + r;
        if (memo.containsKey(key)) return memo.get(key);

        if (r > n / 2) r = n - r;

        int numerator = 1;
        int denominator = 1;

        for (int i = 0; i < r; i++) {
            numerator *= n - i;
            denominator *= i + 1;
        }

        int result = numerator / denominator;
        memo.put(key, result);
        return result;
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        if(N < 10){
            System.out.println(0);
            return ;
        }else if(N == 10){
            System.out.println(1);
            return ;
        }

        List<Node> list = new ArrayList<Node>(); // Node를 저장하는 리스트
        int[] idxInfo = new int[N+1]; //list의 시작 인덱스를 표시하는 배열 
        
        // list 초기화
        list.add(new Node(0,9));
        list.add(new Node(9,0));
        idxInfo[10] = 0; // 10일 때의 정보는 0부터

        for(int i = 11; i <= N ; i++){
            idxInfo[i] = list.size(); // idxInfo 초기화
            for(int k = idxInfo[i-1] ; k < list.size(); k++){ // i-1의 node 들
                Node base = list.get(k);
                if(base.start == 0){
                    list.add(new Node(1, base.end)); 
                    if(base.end == 9) list.add(new Node(base.start, 8));
                    else{
                        list.add(new Node(base.start, base.end-1));
                        list.add(new Node(base.start, base.end+1));
                    }
                }else{
                    list.add(new Node(base.start-1,base.end));
                    list.add(new Node(base.start+1,base.end));
                    if(base.end == 9) list.add(new Node(base.start, 8));
                    else{
                        list.add(new Node(base.start, base.end-1));
                        list.add(new Node(base.start, base.end+1));
                    }
                }
            }
        }
        
        // 결과 계산
        int answer = 0;
        int dupNum = N - 10;
        int quot = dupNum % 2; // 와리가리 횟수

        //와리가리 계산 : N-1일 때에 각각 와리가리 9Cquot
        int idx = idxInfo[N-1]; //N-1의 시작 idx -> 와리가리 개수 계산용
        int nIdx = idxInfo[N];
        int gapNum = nIdx - idx;
        answer += gapNum*combination(8+quot, 8);

        answer += gapNum;

        System.out.println(answer);
    }    
}
