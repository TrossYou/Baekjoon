package SWEA.D5_pro_13072;

class UserSolution {
    static final int MAX_NODE_NUM = 500005;
    static int nodeCnt;
    static int[] head, tail;

    static int[] nodeIns;
    static int[] next;

    static int[] soldierIdx;
    static int[] soldierTeam;

    public void init() {
        nodeCnt = 1;
        head = new int[36];
        tail = new int[36];
        nodeIns = new int[MAX_NODE_NUM];
        next = new int[MAX_NODE_NUM];

        soldierIdx = new int[100005];
        soldierTeam = new int[100005];
    }

    private void addNode(int team, int score, int mID) {
        int teamIdx = team * 6 + score;
        nodeIns[nodeCnt] = mID;
        next[nodeCnt] = 0;
        soldierIdx[mID] = nodeCnt;

        if (head[teamIdx] == 0) {
            head[teamIdx] = nodeCnt;
            tail[teamIdx] = nodeCnt;
        } else {
            next[tail[teamIdx]] = nodeCnt;
            tail[teamIdx] = nodeCnt;
        }
        nodeCnt++;
    }

    public void hire(int mID, int mTeam, int mScore) {
        soldierTeam[mID] = mTeam;
        addNode(mTeam, mScore, mID);
    }

    public void fire(int mID) {
        soldierIdx[mID] = 0;
    }

    public void updateSoldier(int mID, int mScore) {
        int mTeam = soldierTeam[mID];
        addNode(mTeam, mScore, mID);
    }

    public void updateTeam(int mTeam, int mChangeScore) {
        if (mChangeScore == 0)
            return;

        if (mChangeScore > 0) {
            for (int s = 4; s >= 1; s--) {
                int currIdx = mTeam * 6 + s;
                if (head[currIdx] == 0)
                    continue;

                int nxScore = s + mChangeScore;
                if (nxScore > 5)
                    nxScore = 5;
                int nxIdx = mTeam * 6 + nxScore;

                if (head[nxIdx] == 0) {
                    head[nxIdx] = head[currIdx];
                    tail[nxIdx] = tail[currIdx];
                } else {
                    next[tail[nxIdx]] = head[currIdx];
                    tail[nxIdx] = tail[currIdx];
                }

                head[currIdx] = 0;
                tail[currIdx] = 0;
            }
        } else {
            for (int s = 2; s <= 5; s++) {
                int currIdx = mTeam * 6 + s;
                if (head[currIdx] == 0)
                    continue;

                int nxScore = s + mChangeScore;
                if (nxScore < 1)
                    nxScore = 1;
                int nxIdx = mTeam * 6 + nxScore;

                if (head[nxIdx] == 0) {
                    head[nxIdx] = head[currIdx];
                    tail[nxIdx] = tail[currIdx];
                } else {
                    next[tail[nxIdx]] = head[currIdx];
                    tail[nxIdx] = tail[currIdx];
                }

                head[currIdx] = 0;
                tail[currIdx] = 0;
            }
        }
    }

    public int bestSoldier(int mTeam) {
        for (int s = 5; s >= 1; s--) {
            int teamIdx = mTeam * 6 + s;
            int curr = head[teamIdx];
            int maxId = 0;

            while (curr != 0) {
                int id = nodeIns[curr];
                if (soldierIdx[id] == curr) {
                    if (id > maxId) {
                        maxId = id;
                    }
                }
                curr = next[curr];
            }

            if (maxId != 0) {
                return maxId;
            }
        }
        return 0;
    }
}
