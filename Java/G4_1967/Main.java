package Java.G4_1967;

import java.util.*;
import java.io.*;

public class Main {
    static List<List<int[]>> nodes;
    static boolean[] isReady;
    static int[] maxV;
    static int n, ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        maxV = new int[n + 1];
        nodes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            nodes.add(new ArrayList<>());

        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            nodes.get(p).add(new int[] { c, w });
        }

        ans = 0;
        // 탐색하기
        play(1);
        System.out.print(ans);
    }

    static void play(int idx) {
        // 자식 모두 준비 되었는지 확인
        int firstValue = 0, secondValue = 0;
        for (int[] node : nodes.get(idx)) {
            int c = node[0];
            int w = node[1];

            if (maxV[c] == 0 && !nodes.get(c).isEmpty()) { // 리프노드가 아닌데, maxV가 0인 경우 - 아직 계산이 완료되지 않은 상황
                play(c);
            }

            int curValue = maxV[c] + w;
            if (firstValue < curValue) { // curValue > firstV > second
                if (firstValue != 0) // first에 이미 값이 있음 -> 이 값을 second에 저장
                    secondValue = firstValue;
                firstValue = curValue; // first에 현재값 저장
            } else if (secondValue < curValue) { // firstV < curVa < second
                secondValue = curValue; // second 갱신
            }
        }

        maxV[idx] = firstValue;
        ans = Math.max(ans, firstValue + secondValue);
    }
}
