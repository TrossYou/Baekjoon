package SWEA.N_2477;

import java.io.*;
import java.util.*;

public class Solution {
    static int T, N, M, K, A, B;
    static int[] ak, bk, tk;
    static List<int[]>[] aList, bList; // 각 창구의 사람
    static Queue<int[]> aWaitingLine; // 접수 창구 대기 줄
    static PriorityQueue<int[]> bWaitingLine; // 정비 창구 대기 줄

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 접수 창구
            M = Integer.parseInt(st.nextToken()); // 정비 창구
            K = Integer.parseInt(st.nextToken()); // 사람 수
            A = Integer.parseInt(st.nextToken()); // 목표 접수 창구
            B = Integer.parseInt(st.nextToken()); // 목표 정비 창구

            ak = new int[N + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++)
                ak[i] = Integer.parseInt(st.nextToken());
            bk = new int[M + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++)
                bk[i] = Integer.parseInt(st.nextToken());
            tk = new int[K + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= K; i++)
                tk[i] = Integer.parseInt(st.nextToken());

            aList = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++)
                aList[i] = new ArrayList<>();
            bList = new ArrayList[M + 1];
            for (int i = 1; i <= M; i++)
                bList[i] = new ArrayList<>();

            aWaitingLine = new LinkedList<>();
            bWaitingLine = new PriorityQueue<>(
                    Comparator.comparingInt((int[] p) -> p[1]) // 1번 비교 기준: 도착 시간
                            .thenComparingInt((int[] p) -> p[2])); // 2번 비교 기준: 접수 창고 번호

            // K명 모두 접수 대기로 추가
            for (int i = 1; i <= K; i++) {
                aWaitingLine.add(new int[] { i, tk[i] });
            }

            // 접수 인원 모두 계산 후 정비 창구대기라인으로 이동
            while (!aWaitingLine.isEmpty()) {
                int[] currMan = aWaitingLine.poll();
                int currIdx = currMan[0];
                int currTime = currMan[1];
                boolean canGo = false;

                // N개의 창구를 돌며 가능한 창구가 있다면 들어가기
                for (int recep = 1; recep <= N; recep++) {
                    if (aList[recep].isEmpty()) { // bList가 비어 있는 경우 그냥 추가
                        canGo = true;
                        aList[recep].add(new int[] { currIdx, currTime + ak[recep] });
                        break;
                    } else {
                        int[] lastMan = aList[recep].get(aList[recep].size() - 1);
                        int lastIdx = lastMan[0];
                        int lastTime = lastMan[1];

                        // 들어갈 수 있다면 들어가기
                        if (lastTime <= currTime) {
                            canGo = true; // 들어갔다는 표시 -> 다음 고객 처리
                            // 이전 사람 정비창구로 넘기기
                            bWaitingLine.add(new int[] { lastIdx, lastTime, recep }); // recep창구에서 접수한 사용자

                            // 현재 사람 창구에 배치
                            aList[recep].add(new int[] { currIdx, currTime + ak[recep] });
                            break;
                        }
                    }
                }
                if (canGo)
                    continue; // 들어갔다면, 다음 사람

                // 가능한 창구가 없다면, 가장 시간 짧은 사람 밀어내기
                int minT = Integer.MAX_VALUE; // 남은 창구 중 가장 시간이 짧게 남은 시간
                int minIdx = 0; // 마지막 사람 번호
                int minRecepIdx = 0; // 창구 중 가장 시간이 짧게 남은 창구 인덱스
                for (int recep = 1; recep <= N; recep++) {
                    int[] lastMan = aList[recep].get(aList[recep].size() - 1);
                    int lastIdx = lastMan[0];
                    int lastTime = lastMan[1];
                    if (lastTime < minT) {
                        minRecepIdx = recep;
                        minT = lastTime;
                        minIdx = lastIdx;
                    }
                }

                // minRepairIdx사람 정비 창구로 밀어내기
                bWaitingLine.add(new int[] { minIdx, minT, minRecepIdx });
                // 여기에 현재 사람 추가
                aList[minRecepIdx].add(new int[] { currIdx, minT + ak[minRecepIdx] });
            }

            // 접수 창고 남은 사람들 모두 b대기줄에 추가
            for (int recep = 1; recep <= N; recep++) {
                int[] lastMan = aList[recep].get(aList[recep].size() - 1);
                bWaitingLine.add(new int[] { lastMan[0], lastMan[1], recep });
            }

            // 정비 창구 처리
            while (!bWaitingLine.isEmpty()) {
                int[] currMan = bWaitingLine.poll();
                int currIdx = currMan[0];
                int currTime = currMan[1];
                boolean canGo = false;

                // M개의 창구를 돌며 가능한 창구가 있다면 들어가기
                for (int repair = 1; repair <= M; repair++) {
                    if (bList[repair].isEmpty()) {
                        canGo = true;
                        bList[repair].add(new int[] { currIdx, currTime + bk[repair] });
                        break;
                    } else {
                        int[] lastMan = bList[repair].get(bList[repair].size() - 1);
                        int lastTime = lastMan[1];

                        // 들어갈 수 있다면 들어가기
                        if (lastTime <= currTime) {
                            canGo = true; // 들어갔다는 표시 -> 다음 고객 처리
                            // 현재 사람 창구에 배치
                            bList[repair].add(new int[] { currIdx, currTime + bk[repair] });
                            break;
                        }
                    }
                }
                if (canGo)
                    continue; // 들어갔다면, 다음 사람

                // 가능한 창구가 없다면, 가장 시간 짧은 사람 밀어내기
                int minT = Integer.MAX_VALUE; // 남은 창구 중 가장 시간이 짧게 남은 시간
                int minRepairIdx = 0; // 창구 중 가장 시간이 짧게 남은 창구 인덱스
                for (int repair = 1; repair <= M; repair++) {
                    int[] lastMan = bList[repair].get(bList[repair].size() - 1);
                    int lastTime = lastMan[1];
                    if (lastTime < minT) {
                        minRepairIdx = repair;
                        minT = lastTime;
                    }
                }

                // 여기에 현재 사람 추가
                bList[minRepairIdx].add(new int[] { currIdx, minT + bk[minRepairIdx] });
            }

            // A와 B에 겹치는 사람 확인
            boolean[] isContained = new boolean[K + 1]; // K번째 사람이 A에 해당하는 사람 표시
            for (int[] p : aList[A]) {
                isContained[p[0]] = true;
            }

            int ans = 0;
            for (int[] p : bList[B]) {
                if (isContained[p[0]])
                    ans += p[0];
            }

            sb.append('#').append(tc).append(' ').append(ans == 0 ? -1 : ans).append('\n');
        }
        System.out.print(sb);
    }
}
