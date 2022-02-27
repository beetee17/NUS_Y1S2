import java.util.ArrayList;

public class KeySearcher {
    private int N;
    private int k;
    private ITreasureExtractor treasureExtractor;

    public KeySearcher(int N, int k, ITreasureExtractor treasureExtractor) {
        this.N = N;
        this.k = k;
        this.treasureExtractor = treasureExtractor;
    }

    public int search(ArrayList<Integer> keysFound) {
        int begin = 0;
        int end;
        if (keysFound.isEmpty()) {
            end = N - 1;
        } else {
            end = keysFound.get(keysFound.size() - 1);
        }

        while (begin < end) {

            int mid = begin + Math.floorDiv(end - begin, 2);
            int[] keys = new int[N];
            int numKeys = 0;

            for (int i = 0; i < N; i++) {
                if (i <= mid || keysFound.contains(i)) {
                    keys[i] = 1;
                    numKeys++;
                } else {
                    keys[i] = 0;
                }
            }

            if (numKeys < k) {
                begin = mid + 1;
                continue;
            }

            if (treasureExtractor.tryUnlockChest(keys)) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
