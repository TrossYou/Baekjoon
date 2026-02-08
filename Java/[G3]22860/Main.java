import java.util.*;
import java.io.*;

public class Main {
  static Map<String, List<Folder>> childFolderMap; // 하위 폴더 리스트 목록
  static Map<String, Folder> folderMap; // Folder 객체들 저장

  static class Folder {
    String name; // name: 폴더 이름
    int fileCount; // fileCount: 파일 개수(merge계산 시, 자식 파일 수도 반영됨)
    HashSet<String> files; // files: 파일 이름 set
    boolean isMerged; // query에서 이미 files가 계산 되었는지 여부

    Folder(String name) {
      this.name = name;
      files = new HashSet<>();
    }

    void addFile(String name) {
      files.add(name); // O(1)
      fileCount++;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    StringTokenizer st;

    st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());

    childFolderMap = new HashMap<>(); // 폴더 구조 저장
    folderMap = new HashMap<>(); // 폴더 객체 저장
    folderMap.put("main", new Folder("main"));

    // 1. 입력 받기(폴더 구조, 파일 세팅)
    for (int i = 0; i < N + M; i++) {
      st = new StringTokenizer(br.readLine());
      String P = st.nextToken();
      String F = st.nextToken();
      int C = Integer.parseInt(st.nextToken());

      // 부모 폴더 없다면 생성
      folderMap.computeIfAbsent(P, Folder::new);
      childFolderMap.computeIfAbsent(P, k -> new ArrayList<>());

      // F가 폴더일 때
      if (C == 1) {
        // 자식 폴더 없다면 생성
        Folder fInst = folderMap.computeIfAbsent(F, Folder::new);
        childFolderMap.computeIfAbsent(F, k -> new ArrayList<>());

        // P에 F 추가
        childFolderMap.get(P).add(fInst);
      }
      // F가 폴더가 아닐 때
      else {
        folderMap.get(P).addFile(F);
      }
    }

    int Q = Integer.parseInt(br.readLine());
    for (int i = 0; i < Q; i++) {
      String[] arr = br.readLine().split("/");
      String targetName = arr[arr.length - 1]; // targetName: 마지막 폴더 이름

      // 하위 폴더 찾아 files 합성하기
      mergeChildFolder(targetName);
      // 계산된 후, 목적 폴더 접근
      Folder targetFolder = folderMap.get(targetName);
      sb.append(targetFolder.files.size()).append(" ").append(targetFolder.fileCount).append("\n");
    }

    System.out.print(sb);
  }

  static void mergeChildFolder(String targetName) {
    // targetName 하위 파일
    Folder folder = folderMap.get(targetName);
    if (folder.isMerged)
      return; // 이미 계산 끝났으면 종료

    // 하위 폴더 불러오기
    List<Folder> children = childFolderMap.get(targetName);
    if (children.isEmpty())
      return;

    // 말단 폴더인지 확인 -> 애초에 호출 시에도 검사
    for (Folder child : children) {
      mergeChildFolder(child.name);
      folder.files.addAll(child.files); // 병합
      folder.fileCount += child.fileCount;
    }
    folder.isMerged = true;
  }
}
