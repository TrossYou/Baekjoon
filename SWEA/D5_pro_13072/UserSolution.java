package SWEA.D5_pro_13072;

import java.util.*;

class UserSolution {
  class Soldier {
    int mID, mScore;

    public Soldier(int mID, int mScore) {
      this.mID = mID;
      this.mScore = mScore;
    }
  }

  class Team {
    int mTeam; // 팀 번호
    List<Soldier> soldiers; // 군사 목록
    int bestScore, bestSoldierId;

    Team(int mTeam) {
      this.mTeam = mTeam;
      soldiers = new ArrayList<>();
      bestScore = 0;
      bestSoldierId = 0;
    }
  }

  Team[] teams;
  int[] teamInfo; // i번 병사의 팀 번호

  public void init() {
    teams = new Team[6]; // 팀은 1~5
    for (int i = 1; i <= 5; i++) {
      teams[i] = new Team(i); // 이게 필요한가..?
    }
    teamInfo = new int[100001]; // i번 병사의 팀 번호
  }

  public void hire(int mID, int mTeam, int mScore) {
    Team targetT = teams[mTeam];
    targetT.soldiers.add(new Soldier(mID, mScore)); // 추가

    // best병사 갱신
    if (targetT.bestScore < mScore) {
      targetT.bestScore = mScore;
      targetT.bestSoldierId = mID;
    } else if (targetT.bestScore == mScore && targetT.bestSoldierId < mID) {
      targetT.bestSoldierId = mID;
    }

    // 각 병사 별 팀 저장
    teamInfo[mID] = mTeam;
  }

  public void fire(int mID) {
    int mTeam = teamInfo[mID];
    Team targetT = teams[mTeam];
    for (Soldier s : targetT.soldiers) {
      if (s.mID == mID) {
        targetT.soldiers.remove(s);

        // 만약 bestScore병사였다면,, 갱신
        if (targetT.bestSoldierId == mID) {
          int newBestScore = 0;
          int newBestSoldierId = 0;
          for (Soldier newS : targetT.soldiers) {
            if (newBestScore < newS.mScore) {
              newBestScore = newS.mScore;
              newBestSoldierId = newS.mID;
            } else if (newBestScore == newS.mScore && newBestSoldierId < newS.mScore) {
              newBestSoldierId = newS.mID;
            }
          }
          targetT.bestScore = newBestScore;
          targetT.bestSoldierId = newBestSoldierId;
        }

        break;
      }
    }
  }

  public void updateSoldier(int mID, int mScore) {
    int mTeam = teamInfo[mID];
    Team targetT = teams[mTeam];
    for (Soldier s : targetT.soldiers) {
      if (s.mID == mID) {
        s.mScore = mScore;
        break;
      }
    }
  }

  public void updateTeam(int mTeam, int mChangeScore) {
    Team targetT = teams[mTeam];

    for (Soldier s : targetT.soldiers) {
      s.mScore += mChangeScore;
      if (s.mScore > 5)
        s.mScore = 5;
      else if (s.mScore < 1)
        s.mScore = 1;
    }
  }

  public int bestSoldier(int mTeam) {
    return teams[mTeam].bestSoldierId;
  }
}
