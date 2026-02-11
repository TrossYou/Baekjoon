import java.io.*;
import java.util.*;

public class Main {
    // 부품의 이름, 가격, 성능을 담는 클래스
    static class Part implements Comparable<Part>{
        String name;
        int price,quality;

        public Part(String name, int price, int quality) {
            this.name = name;
            this.price = price;
            this.quality = quality;
        }

        // quality 기준으로 정렬 (TreeSet용)
        @Override
        public int compareTo(Part other){
            return Integer.compare(this.quality, other.quality);
        }
    }

    private static Map<String, TreeSet<Part>> partTypeMap; // type을 key로 하는 Part 리스트 맵
    private static int n, b;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        while(T-- > 0){
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            partTypeMap = new HashMap<>();

            // 입력 처리
            for(int i=0; i<n; i++){
                st = new StringTokenizer(br.readLine());
                String type = st.nextToken();
                String name = st.nextToken();
                int price = Integer.parseInt(st.nextToken());
                int quality = Integer.parseInt(st.nextToken());

                partTypeMap.computeIfAbsent(type, k -> new TreeSet<>()).add(new Part(name, price, quality));
            }

            // 키를 순회하며, 부품 종류가 1개인 type은 미리 구매
            int maxQ = 1_000_000_000; // 품질 상한선
            int rest = b; // 남은 가격

            Iterator<String> keys = partTypeMap.keySet().iterator();
            while (keys.hasNext()) {
                String type = keys.next();
                if(partTypeMap.get(type).size() == 1) {
                    Part p = partTypeMap.get(type).first();
                    maxQ = Math.min(maxQ, p.quality);
                    rest -= p.price; 
                    keys.remove();
                }
            }

            // [이분 탐색] 
            int ans = 0;
            int low = 0;
            int high = maxQ; // 1개짜리 부품 중 최저 품질

            while(low <= high){
                int mid = (low + high) / 2;

                if(isPossible(mid, rest)){
                    ans = mid; // 가능하니까 정답 후보
                    low = mid + 1; // 더 높게 가능한지 탐색
                } else{
                    high = mid - 1; // 불가능하므로 더 낮게 탐색
                }
            }
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    // limQ이상 품지 부품으로 구매 가능한가?
    static boolean isPossible(int limQ, int rest){
        // 모든 타입을 순회하면서, limQ보다 크게 가능한지 확인
        for (String type : partTypeMap.keySet()) {
            // 해당 타입의 트리셋
            TreeSet<Part> parts = partTypeMap.get(type);

            // limQ보다 이상의 품질 가진 집합 
            Part dummy = new Part("", 0, limQ); // 비교를 위한 임시 부품
            SortedSet<Part> validParts = parts.tailSet(dummy); // limQ보다 큰 퀄리티 부품 셋 -> sortedSet과 treeSet?

            // 조건을 만족하는 부품이 없다면 -> 불가능
            if(validParts.isEmpty()) return false;

            // validPats 순회하며 최소 가격 찾기
            int minPrice = Integer.MAX_VALUE;
            for(Part p: validParts){
                minPrice = Math.min(minPrice, p.price);
            }

            rest -= minPrice;
            if(rest < 0) return false; // 예산 초과 미리 반환
        }
        return true;
    }
}
