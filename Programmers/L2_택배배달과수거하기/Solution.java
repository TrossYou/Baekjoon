package Programmers.L2_택배배달과수거하기;

class Solution {
  public long solution(int cap, int n, int[] deliveries, int[] pickups) {
    long answer = 0;

    int deliverIdx = deliveries.length - 1; // 아직 배송 안된 마지막 인덱스
    int pickupIdx = pickups.length - 1; // 아직 수거 안된 마지막 인덱스
    int deliverNum = 0; // 들고 있는 택배 수
    int pickupNum = 0; // 들고 있는 빈상자 수

    while (deliverIdx != 0 || pickupIdx != 0) {
      if (deliveries[deliverIdx] == 0) { // 만약 배달 완료 되었다면
        deliverIdx--; // 앞집이 필요함
        if (pickups[pickupIdx] == 0) { // 수거도 필요 없다면
          pickupIdx--;
        } else {
          if (pickups[pickupIdx] <= cap - (deliverNum + pickupNum)) { // cap 여분이 있는 경우
            pickupNum += pickups[pickupIdx];
            pickups[pickupIdx--] = 0;
          } else { // cap 가득 참
            pickups[pickupIdx] -= cap - (deliverNum + pickupNum);
            // 초기화 = 시작점으로 복귀
            deliverNum = 0;
            pickupNum = 0;
            // 어디로 가지..
          }
        }
      } else {

      }
    }

    return answer;
  }
}
