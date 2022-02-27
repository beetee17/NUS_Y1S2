import java.util.ArrayList;
import java.util.Arrays;

public class FindKeysMinimumAttempts implements IFindKeys {
    @Override
    public int[] findKeys(int N, int k, ITreasureExtractor treasureExtractor) {

        ArrayList<Integer> keysFound = new ArrayList<Integer>();
        KeySearcher keySearcher = new KeySearcher(N, k, treasureExtractor);

        while (keysFound.size() < k) {
            int index = keySearcher.search(keysFound);
            keysFound.add(index);
        }

        int[] keys = new int[N];
        for (int i = 0; i < N; i++) {
            if (keysFound.contains(i)) {
                keys[i] = 1;
            } else {
                keys[i] = 0;
            }
        }
        return keys;
    }
}

