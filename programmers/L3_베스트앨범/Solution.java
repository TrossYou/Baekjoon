package Programmers.L3_베스트앨범;

import java.util.*;

class Solution {
  public int[] solution(String[] genres, int[] plays) {
    Map<String, Integer> genreTotal = new HashMap<>();
    Map<String, List<int[]>> genreSongs = new HashMap<>();

    for (int i = 0; i < genres.length; i++) {
      String genre = genres[i];
      genreTotal.merge(genre, plays[i], Integer::sum);
      genreSongs.computeIfAbsent(genre, k -> new ArrayList<>())
          .add(new int[] { i, plays[i] });
    }

    List<String> sortedGenres = new ArrayList<>(genreTotal.keySet());
    sortedGenres.sort((a, b) -> genreTotal.get(b) - genreTotal.get(a));

    List<Integer> result = new ArrayList<>();

    for (String genre : sortedGenres) {
      List<int[]> songs = genreSongs.get(genre);

      songs.sort((a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);

      for (int i = 0; i < Math.min(2, songs.size()); i++) {
        result.add(songs.get(i)[0]);
      }
    }

    return result.stream().mapToInt(Integer::intValue).toArray();
  }
}
