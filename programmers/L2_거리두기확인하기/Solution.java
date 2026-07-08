package L2_거리두기확인하기;

import java.util.ArrayList;
import java.util.List;

class Person {
  int r, c;

  public Person(int r, int c) {
    this.r = r;
    this.c = c;
  }
}

class Solution {
  List<Person> people;
  String[][] places;

  public int[] solution(String[][] places) {
    int[] answer = new int[5];
    this.places = places;

    for (int room = 0; room < 5; room++) {
      people = new ArrayList<>();
      for (int row = 0; row < 5; row++) {
        String str = places[room][row];
        for (int col = 0; col < 5; col++) {
          if (str.charAt(col) == 'P') { // 사람이라면 확인
            people.add(new Person(row, col));
          }
        }
      }

      // 맨헤튼 거리 확인
      int peopleNum = people.size();
      boolean isDone = false;
      for (int i = 0; i < peopleNum; i++) {
        Person p1 = people.get(i);
        for (int j = i + 1; j < peopleNum; j++) {
          Person p2 = people.get(j);
          if (!check(room, p1, p2))
            isDone = true;
          if (isDone)
            break;
        }
        if (isDone)
          break;
      }
      if (isDone)
        answer[room] = 0;
      else
        answer[room] = 1;

    }

    return answer;
  }

  public boolean check(int room, Person p1, Person p2) {
    int r1 = p1.r;
    int c1 = p1.c;
    int r2 = p2.r;
    int c2 = p2.c;
    int dis = Math.abs(r1 - r2) + Math.abs(c1 - c2);
    if (dis < 1)
      return false;
    else if (dis > 2)
      return true;
    else { // 거리가 2인 경우
      // 일직선
      if (r1 == r2 || c1 == c2) {
        if (places[room][(r1 + r2) / 2].charAt((c1 + c2) / 2) == 'X') // 파티션 있는 경우
          return true;
        else // 없으면 탈락
          return false;
      } else { // 대각선
        if (places[room][r1].charAt(c2) == 'X' && places[room][r2].charAt(c1) == 'X')
          return true;
        else
          return false;
      }
    }
  }
}
