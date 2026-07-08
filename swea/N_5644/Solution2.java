import java.io.*;
import java.util.*;

public class Solution2 {
    // BC 정보를 담을 단순 클래스 (메서드 제거로 가볍게)
    static class BC {
        int x, y, c, p;
        public BC(int x, int y, int c, int p) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.p = p;
        }
    }

    static int M, A;
    static int[] pathA, pathB; // ArrayList 대신 배열 사용
    static BC[] bcArray;
    static int ax, ay, bx, by;

    // 0:중지, 1:상, 2:우, 3:하, 4:좌
    static int[] dx = {0, 0, 1, 0, -1};
    static int[] dy = {0, -1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            pathA = new int[M];
            pathB = new int[M];

            // 사용자 A 이동 정보
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathA[i] = Integer.parseInt(st.nextToken());
            }

            // 사용자 B 이동 정보
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathB[i] = Integer.parseInt(st.nextToken());
            }

            // BC 정보
            bcArray = new BC[A];
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                bcArray[i] = new BC(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())
                );
            }

            // 초기 위치 설정
            ax = 1; ay = 1;
            bx = 10; by = 10;

            // 0. 초기 위치에서의 충전량 계산
            int totalSum = getCharge();

            // 1. 시간 흐름에 따른 이동 및 충전
            for (int i = 0; i < M; i++) {
                // A 이동
                ax += dx[pathA[i]];
                ay += dy[pathA[i]];
                // B 이동
                bx += dx[pathB[i]];
                by += dy[pathB[i]];

                // 현재 위치에서 최대 충전량 계산 후 누적
                totalSum += getCharge();
            }

            sb.append("#").append(tc).append(" ").append(totalSum).append("\n");
        }
        System.out.println(sb);
    }

    // [최적화 핵심] 현재 위치(ax,ay / bx,by)에서 얻을 수 있는 최대 충전량 반환
    static int getCharge() {
        int max = 0;

        // A가 선택할 BC(i)와 B가 선택할 BC(j)의 모든 조합을 확인 (완전 탐색)
        // A(BC개수)가 최대 8이므로 8*8 = 64번 반복 -> 매우 빠름
        for (int i = 0; i < A; i++) {
            for (int j = 0; j < A; j++) {
                int sum = 0;
                
                // A가 i번째 BC 범위에 있는지 확인 후 충전량 더하기
                boolean aCheck = check(ax, ay, bcArray[i]);
                if(aCheck) sum += bcArray[i].p;

                // B가 j번째 BC 범위에 있는지 확인 후 충전량 더하기
                boolean bCheck = check(bx, by, bcArray[j]);
                if(bCheck) sum += bcArray[j].p;

                // [충돌 처리] 만약 A와 B가 같은 BC를 선택했고, 둘 다 접속 가능하다면
                // 위에서 P가 두 번 더해졌으므로(2P), 하나를 빼줘야 함 (P)
                if (i == j && aCheck && bCheck) {
                    sum /= 2; // 혹은 sum -= bcArray[i].p;
                }

                // 최댓값 갱신
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // 거리 체크 함수
    static boolean check(int x, int y, BC bc) {
        return Math.abs(bc.x - x) + Math.abs(bc.y - y) <= bc.c;
    }
}