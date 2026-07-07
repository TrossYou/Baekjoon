package L2_타겟넘버;

class Solution {
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

}
