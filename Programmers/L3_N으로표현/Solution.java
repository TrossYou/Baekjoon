import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public int solution(int N, int number) {
        List<Set<Integer>> dp = new ArrayList<>();

        dp.add(new HashSet<>()); // dp[0]

        int n = N;
        for (int i = 1; i <= 8; i++) {
            dp.add(new HashSet<>());
            dp.get(i).add(n);
            n = n * 10 + N;
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= i; j++) {
                for (Integer num1 : dp.get(j)) {
                    for (Integer num2 : dp.get(i - j)) {
                        dp.get(i).add(num1 + num2);
                        dp.get(i).add(num1 - num2);
                        dp.get(i).add(num1 * num2);
                        if (num2 != 0)
                            dp.get(i).add(num1 / num2);
                    }
                }
            }
            if (dp.get(i).contains(number))
                return i;
        }
        return -1;
    }
}
