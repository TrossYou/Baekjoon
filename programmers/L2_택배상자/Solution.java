package L2_택배상자;

import java.util.ArrayList;
import java.util.List;

class Solution {
  public int solution(int[] order) {
    int answer = 0;
    int curBox = 1;
    List<Integer> subBelt = new ArrayList<>();

    for (int i = 0; i < order.length; i++) {
      int targetBox = order[i];

      if (targetBox < curBox) {
        if (!subBelt.isEmpty() && subBelt.get(subBelt.size() - 1).equals(targetBox)) {
          answer++;
          subBelt.remove(subBelt.size() - 1);
          continue;
        } else {
          // 종료
          break;
        }
      } else {
        while (targetBox > curBox) {
          subBelt.add(curBox++);
        }

        if (targetBox == curBox) {
          curBox++;
          answer++;
          continue;
        } else {
          break;
        }
      }
    }

    return answer;
  }
}
