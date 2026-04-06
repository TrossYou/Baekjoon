package SWEA.N_2383;

import java.util.*;
import java.io.*;

public class Solution {
    static int T, N, pIdx, sIdx; // 테스트케이스 수, 맵 크기, 사람 인덱스, 계단 인덱스
    static boolean[] isDone; // 계단을 탔는지 여부
    static int[][] people; // 사람의 정보 ([r, c]) - 10개
    static int[][] distance; // 사람에서부터 계단까지 거리 ([0번계단 거리, 1번계단 거리])
    static int[][] stairs; // 계단의 정보 ([r, c, 시간]) - 2개
    static Queue<int[]>[] stairQ; // 계단 내부 ([사람인덱스, 종료 시간]) - 2개

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            pIdx = 0;
            sIdx = 0;
            isDone = new boolean[10];
            people = new int[10][2];
            distance = new int[10][2];
            stairs = new int[2][3];
            stairQ = new Queue[2];
            for (int i = 0; i < 2; i++) {
                stairQ[i] = new LinkedList<>();
            }

            StringTokenizer st;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    int v = Integer.parseInt(st.nextToken());
                    if (v == 1) { // 사람 정보 저장
                        people[pIdx++] = new int[] { i, j }; // 사람 정보 저장
                    } else if (v >= 2) { // 계단 정보 저장
                        stairs[sIdx++] = new int[] { i, j, v }; // 계단 정보 저장
                    }
                }
            }

            // 계단까지 거리 계산
            int sR1 = stairs[0][0]; // 0번계단 좌표
            int sC1 = stairs[0][1];
            int sR2 = stairs[1][0]; // 1번계단 좌표
            int sC2 = stairs[1][1];

            for (int i = 0; i < pIdx; i++) {
                int pR = people[i][0]; // 사람의 좌표
                int pC = people[i][1];
                // 0번 계단과 거리
                distance[i][0] = Math.abs(pR - sR1) + Math.abs(pC - sC1);

                // 1번 계단과 거리
                distance[i][1] = Math.abs(pR - sR2) + Math.abs(pC - sC2);
            }

            int leftCnt = pIdx; // 남은 사람 수
            int t = 1; // 소요 시간
            while (leftCnt > 0) {
                // 계단에서 종료 사람 우선 탈출
                while (!stairQ[0].isEmpty()) {
                    int[] p = stairQ[0].peek();
                    if (p[1] > t) // 만약 탈출 시간 안되면 종료 -> 더 늦게 들어온 사람들도 탈출 불가
                        break;
                    stairQ[0].poll();
                    leftCnt--;
                }
                while (!stairQ[1].isEmpty()) {
                    int[] p = stairQ[1].peek();
                    if (p[1] > t)
                        break;
                    stairQ[1].poll();
                    leftCnt--;
                }

                // 계단까지의 거리가 t보다 작은 경우, 해당 계단 진입 시도
                // 0번 계단 t이내에 진입할 수 있는 사람 탐색
                for (int i = 0; i < pIdx; i++) {
                    if (isDone[i])
                        continue;
                    // 0번 계단
                    if (distance[i][0] <= t && stairQ[0].size() < 3) { // 만약 사람이 아직 탈출 못했고, t이내에 0번 계단에
                                                                       // 진입할 수 있는 경우
                        // 계단 진입
                        stairQ[0].add(new int[] { i, t + stairs[0][2] }); // 계단에서 종료 시간은 진입시간 + 계단 소요 시간
                        isDone[i] = true;
                    }
                    // 1번 계단
                    if (distance[i][1] <= t && stairQ[1].size() < 3) { // 만약 사람이 아직 탈출 못했고, t이내에 1번 계단에
                                                                       // 진입할 수 있는 경우
                        // 계단 진입
                        stairQ[1].add(new int[] { i, t + stairs[1][2] }); // 계단에서 종료 시간은 진입시간 + 계단 소요 시간
                        isDone[i] = true;
                    }
                }
                t++;
            }

            sb.append('#').append(tc).append(' ').append(t).append('\n');
        }
        System.out.print(sb);
    }
}
