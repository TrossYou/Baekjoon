package SWEA.D5_pro_14613;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserSolution {
    class Arr {
        String arrName; // 배열 이름
        int parent; // 부모 배열 arrLists 인덱스
        short dummyIdx; // 본인이 부모인 경우에만 Dummy인덱스값
        boolean isDeepCopy; // 깊은 복사인지 저정
        Map<Integer, Short> changes; // key: 인덱스, short:변경된 값 -> 깊은 복사인 경우, 변경된 값만 저장

        public Arr(String arrName, int parent, short dummyIdx, boolean isDeepCopy) {
            this.arrName = arrName;
            this.parent = parent;
            this.dummyIdx = dummyIdx;
            this.isDeepCopy = isDeepCopy;
            if (isDeepCopy)
                changes = new HashMap<>();
        }
    }

    static List<Arr> arrLists; // Arr객체 리스트 -> 최대 5050 // 10(makeList) + 5,000(copyList)회

    static int dummyIdx;
    static Map<String, Integer> nameToIdx; // 배열이 arrLists의 몇 번 인덱스에 있는지 저장
    static short[][] listDummy; // 10개의 배열을 미리 정의해두기 -> [10][200020];
    static int[] listLength; // 배열의 길이 <- listDummy에서 이미 2000020으로 정의해서 따로 길이 관리
    static List<Integer>[] deepCopyChildren;

    private String charToString(char[] arr) {
        int len = 0;
        while (len < arr.length && arr[len] != '\0')
            len++;
        return new String(arr, 0, len);
    }

    public void init() {
        arrLists = new ArrayList<>(); // Arr를 저장할 리스트 (makeList + copyList)
        nameToIdx = new HashMap<>();

        dummyIdx = 0; // 지금까지 더미에 저장된 list 수 (makeList 수)
        listDummy = new short[10][200020]; // makeList로 생성된 배열의 값이 저장될 배열
        listLength = new int[10]; // listDummy 리스트의 길이

        deepCopyChildren = new List[10]; // 각 더미에 연결된 deepCopy Arr의 인덱스 저장
        for (int i = 0; i < 10; i++)
            deepCopyChildren[i] = new ArrayList<>();
    }

    public void makeList(char mName[], int mLength, int mListValue[]) {
        String name = charToString(mName);
        for (int i = 0; i < mLength; i++) {
            listDummy[dummyIdx][i] = (short) mListValue[i]; // 값 복사
        }
        listLength[dummyIdx] = mLength;

        int listIdx = arrLists.size();
        nameToIdx.put(name, listIdx); // 이름 - list인덱스
        arrLists.add(new Arr(name, listIdx, (short) dummyIdx, false));

        dummyIdx++;
    }

    public void copyList(char mDest[], char mSrc[], boolean mCopy) {
        String srcName = charToString(mSrc);
        String destName = charToString(mDest);

        int parentListIdx = nameToIdx.get(srcName); // parent의 list 인덱스
        Arr parentArr = arrLists.get(parentListIdx);
        short dummyIdx = arrLists.get(parentListIdx).dummyIdx; // 자식의 더미인덱스는 부모의 더미 인덱스

        int childIdx = arrLists.size();

        int rootIdx = parentListIdx;

        if (mCopy) {
            // 루트 찾기
            Arr cur = parentArr;
            int curIdx = parentListIdx;
            while (curIdx != cur.parent) {
                curIdx = cur.parent;
                cur = arrLists.get(curIdx);
            }
            rootIdx = curIdx; // makeList 배열의 인덱스
        }

        Arr newArr = new Arr(destName, parentListIdx, dummyIdx, mCopy);

        if (mCopy) {
            deepCopyChildren[dummyIdx].add(childIdx);

            // 부모 체인을 순회하며 changess 병합
            Arr cur = parentArr;
            int curIdx = parentListIdx;
            while (curIdx != cur.parent) { // 루트 전까지
                if (cur.isDeepCopy && cur.changes != null) {
                    for (Map.Entry<Integer, Short> e : cur.changes.entrySet()) {
                        if (!newArr.changes.containsKey(e.getKey())) {
                            newArr.changes.put(e.getKey(), e.getValue());
                        }
                    }
                }
                curIdx = cur.parent;
                cur = arrLists.get(curIdx);
            }
        }

        nameToIdx.put(destName, childIdx);
        arrLists.add(newArr);
    }

    public void updateElement(char mName[], int mIndex, int mValue) {
        String name = charToString(mName);
        int listsIdx = nameToIdx.get(name); // arrLists에서 name의 인덱스
        Arr arr = arrLists.get(listsIdx); // arr 객체 가져오기

        int parentListIdx = arr.parent;
        if (listsIdx == parentListIdx || !arr.isDeepCopy) { // 부모가 자신이거나, shallowCopy인 경우 dummy자체를 수정
            short dummyIdx = arr.dummyIdx;
            short originValue = listDummy[dummyIdx][mIndex]; // 원본값
            listDummy[dummyIdx][mIndex] = (short) mValue;

            // 더미의 딸린 자식들 changes 업데이트
            for (int childIdx : deepCopyChildren[dummyIdx]) {
                Arr child = arrLists.get(childIdx);
                if (!child.changes.containsKey(mIndex))
                    child.changes.put(mIndex, originValue);
            }

        } else { // 부모가 있고, deepCopy인 경우 -> changes에 추가
            arr.changes.put(mIndex, (short) mValue);
        }
    }

    public int element(char mName[], int mIndex) {
        String name = charToString(mName);
        int listIdx = nameToIdx.get(name);
        Arr arr = arrLists.get(listIdx);

        int parentListIdx = arr.parent;
        while (listIdx != parentListIdx && arr.isDeepCopy) { // 부모가 있는데, deepCopy인 경우
            if (arr.changes.get(mIndex) != null) // changes에 있다면 반환
                return arr.changes.get(mIndex);
            listIdx = parentListIdx; // 없다면 부모로 올라감
            arr = arrLists.get(listIdx); // 부모의 객체 할당
            parentListIdx = arr.parent; // 조부모 할당
        }
        // 부모가 더 이상 없거나, swallowCopy인 경우,,,그냥 더미의 인덱스값
        return listDummy[arr.dummyIdx][mIndex];
    }
}