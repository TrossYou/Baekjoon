package L2_타겟넘버;

class Solution {
<<<<<<< HEAD
  int finalA = 0;
  int finalT;

  public int solution(int[] numbers, int target) {
    finalT = target;
    int maxMask = (int) Math.pow(2, numbers.length);

    for (int mask = 0; mask < maxMask; mask++)
      calc(numbers, mask);

    return finalA;
  }

  public void calc(int[] numbers, int mask) {
    int sum = 0;
    for (int i = 0; i < numbers.length; i++) {
      if ((mask & (1 << i)) != 0)
        sum += numbers[i];
      else
        sum -= numbers[i];
    }
    if (sum == finalT)
      finalA++;
    return;
  }
=======
  boolean[] ops;
  int[] nums;
  int len, targetV, ans;

  public int solution(int[] numbers, int target) {
    len = numbers.length;
    ops = new boolean[len]; // true: +, false: -
    nums = numbers;
    targetV = target;

    for (int mask = 0; mask < (1 << len); mask++) {
      calc(mask); // 비트마스크 활용
    }

    return ans;
  }

  // public void dfs(int depth) {
  // if (depth == len) {
  // return;
  // }

  // ops[depth] = true;
  // calc();

  // dfs(depth + 1);

  // ops[depth] = false;
  // calc();
  // }

  public void calc(int mask) { // 계산 중
    int res = nums[0];
    for (int i = 0; i < len; i++) {
      if ((mask & (1 << i)) != 0) {
        res += nums[i];
      } else {
        res -= nums[i];
      }
    }

    if (res == targetV)
      ans++;
  }

>>>>>>> origin/main
}
