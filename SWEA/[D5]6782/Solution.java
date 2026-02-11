import java.io.*;
import java.util.*;

public class Solution {
    static int minCount; // 최소값
    static List<Integer> squareList;

    public static void main(String[] args) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        // int T = Integer.parseInt(br.readLine());
        int T = sc.nextInt();
        squareList = new ArrayList<>(); // 각 테스트 케이스도 갱신
        squareList.add(0); // idx 0초기화

        for(int tc=1; tc<=T;tc++){
            minCount = 0;
            // int N = Integer.parseInt(br.readLine());
            long N = sc.nextLong();
            // list에 있는 거듭제곱보다 큰 경우 -> N보다 클때까지 list에 추가
            while(squareList.get(squareList.size()-1) < N){
                int next = squareList.size();
                squareList.add(next*next);
            }
            //list순회하며 최소값 계산
            getCount(N);

            sb.append('#').append(tc).append(' ').append(minCount).append('\n');
        }
        System.out.print(sb);
    }

    // 이미 존재하는 리스트에서 최소 계산
    private static void getCount(long n){
        // 이진 탐색으로 찾아도 좋겠지만..일단 전체 탐색
        if(n == 2) return;

        int low = 1;
        int high = squareList.size()-1;
        int mid = (low + high)/2;
        while(low <= high){
            mid = (low + high) / 2;

            // n이 중간제곱과 같으면 정답
            if(n == squareList.get(mid)) break;
            // n이 중간제곱보다 작으면 좌측 탐색
            else if(n < squareList.get(mid)){
                high = mid-1;
            }
            // n이 중간제곱보다 크면 우측 탐색
            else{
                low = mid+1;
            }
        }

        minCount += squareList.get(mid) - n;
        minCount++;
        getCount(mid);
    }
}
