import java.util.*;

class Solution {

  private List<List<String>> cand;
  private Set<Set<String>> res;

  public int solution(String[] user_id, String[] banned_id) {
    cand = new ArrayList<>();
    res = new HashSet<>();

    for (String banned : banned_id) {
      List<String> matched = new ArrayList<>();
      for (String user : user_id) {
        if (isMatch(user, banned)) {
          matched.add(user);
        }
      }
      cand.add(matched);
    }

    dfs(0, new HashSet<>());

    return res.size();
  }

  private boolean isMatch(String userId, String bannedId) {
    if (userId.length() != bannedId.length())
      return false;

    for (int i = 0; i < bannedId.length(); i++) {
      if (bannedId.charAt(i) != '*' && bannedId.charAt(i) != userId.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  private void dfs(int dep, Set<String> chosen) {
    if (dep == cand.size()) {
      res.add(new HashSet<>(chosen));
      return;
    }

    for (String candidate : cand.get(dep)) {
      if (!chosen.contains(candidate)) {
        chosen.add(candidate);
        dfs(dep + 1, chosen);
        chosen.remove(candidate);
      }
    }
  }
}
