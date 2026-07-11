package L2_타겟넘버;

class Solution {
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
}
