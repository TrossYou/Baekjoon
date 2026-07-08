import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static final int CARD_NUM = 9;
    static int gWin;
    static int[] gCard;
    static int[] iCard;
    static boolean[] isUsed;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            gCard = new int[CARD_NUM];
            iCard = new int[CARD_NUM];
            isUsed = new boolean[CARD_NUM * 2 + 1];
            gWin = 0;
            
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < CARD_NUM; i++) {
                gCard[i] = Integer.parseInt(st.nextToken());
                isUsed[gCard[i]] = true;
            }
            
            int idx = 0;
            for (int i = 1; i <= CARD_NUM * 2; i++) {
                if (!isUsed[i]) {
                    iCard[idx++] = i;
                }
            }

            dfs(0, 0, 0, 0);
            int lose = 362880 - gWin;
            sb.append("#").append(tc).append(" ").append(gWin).append(" ").append(lose).append("\n");
        }
        System.out.print(sb);
    }    

    static void dfs(int round, int bitMask, int gScore, int iScore) {
        if (round == CARD_NUM) {
            if (gScore > iScore) {
                gWin++;
            }
            return;
        }

        for (int i = 0; i < CARD_NUM; i++) {
            if ((bitMask & (1 << i)) != 0) {
                continue;
            }

            int gy = gCard[round];
            int in = iCard[i];
            int score = gy + in;

            if (gy > in) {
                dfs(round + 1, bitMask | (1 << i), gScore + score, iScore);
            } else {
                dfs(round + 1, bitMask | (1 << i), gScore, iScore + score);
            }
        }
    }
}
