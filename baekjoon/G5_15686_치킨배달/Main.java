package Java.G5_15686;

import java.util.*;
import java.io.*;

public class Main {
    static List<int[]> houseList, chickenList;
    static int N, M, ans, chickenCnt, houseCnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        ans = Integer.MAX_VALUE;

        houseList = new ArrayList<>(); // 집 리스트
        chickenList = new ArrayList<>(); // 치킨집 리스트

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int v = Integer.parseInt(st.nextToken());
                if (v == 0)
                    continue;
                if (v == 1) { // 집은 집리스트에 추가
                    houseList.add(new int[] { i, j });
                } else if (v == 2) { // 치킨집 리스트에 추가
                    chickenList.add(new int[] { i, j });
                }
            }
        }

        chickenCnt = chickenList.size();
        houseCnt = houseList.size();

        // 치킨집 조합 생성해서 검사
        makeChickenSet(0, 0, new boolean[chickenCnt]);

        System.out.println(ans);
    }

    static int getDistance(int houseIdx, int chickenIdx) {
        int[] house = houseList.get(houseIdx);
        int[] chicken = chickenList.get(chickenIdx);

        return Math.abs(house[0] - chicken[0]) + Math.abs(house[1] - chicken[1]);
    }

    static void makeChickenSet(int idx, int cnt, boolean[] isContained) {
        if (cnt == M) {
            calcTotal(isContained); // 거리 계산하는 메서드
            return;
        }

        if (idx >= chickenCnt) // idx가 넘어도 종료
            return;

        // idx 추가하기.
        isContained[idx] = true;
        makeChickenSet(idx + 1, cnt + 1, isContained);

        // idx 추가 안하기.
        isContained[idx] = false;
        makeChickenSet(idx + 1, cnt, isContained);
    }

    static void calcTotal(boolean[] isContained) {
        int sum = 0;
        for (int i = 0; i < houseCnt; i++) {
            int dis = Integer.MAX_VALUE;
            for (int j = 0; j < chickenCnt; j++) {
                if (isContained[j]) {
                    dis = Math.min(dis, getDistance(i, j));
                }
            }
            sum += dis;
        }
        ans = Math.min(ans, sum); // 정답 갱신
    }

}
