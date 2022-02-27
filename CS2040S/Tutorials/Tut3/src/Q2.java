import java.util.ArrayList;
import java.util.Arrays;

public class Q2 {

    public static boolean hasDuplicates(int[] arr) {
        int[] sortedArr = Arrays.stream(arr).sorted().toArray();
        for (int i = 0; i < sortedArr.length - 1; i++) {
            if (sortedArr[i] == sortedArr[i+1]) {
                return true;
            }
        }
        return false;
    }

    public static int[] removeDuplicatesFrom(int[] arr) {
        int[] sortedArr = Arrays.stream(arr).sorted().toArray();
        ArrayList<Integer> res = new ArrayList<Integer>();

        int currInt = sortedArr[0];
        res.add(currInt);
        for (int i = 1; i < sortedArr.length; i++) {
            if (sortedArr[i] != currInt) {
                currInt = sortedArr[i];
                res.add(currInt);
            }
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    public static int[] getDistinct(int[] A, int[] B) {
        // Assumes no duplicates
        int[] sortedA = Arrays.stream(A).sorted().toArray();
        int[] sortedB = Arrays.stream(B).sorted().toArray();

        // MERGE STEP
        ArrayList<Integer> C = new ArrayList<Integer>();
        int i = 0;
        int j = 0;

        while (i < sortedA.length && j < sortedB.length) {
            int a = sortedA[i];
            int b = sortedB[j];
            if (a < b) {
              C.add(a);
              i++;
            } else if (b < a) {
                C.add(b);
                j++;
            } else {
                // Do not add
                i++;
                j++;
            }
        }

        while (i < sortedA.length) {
            C.add(sortedA[i]);
            i++;
        }

        while (j < sortedB.length) {
            C.add(sortedB[j]);
            j++;
        }

        return C.stream().mapToInt(x -> x).toArray();
    }

    public static int[] sumToTarget(int target, int[] A) {
        int[] sortedA = Arrays.stream(A).sorted().toArray();
        int start = 0;
        int end = sortedA.length - 1;

        while (start < end) {
            int x = sortedA[start];
            int y = sortedA[end];

            if (x + y < target) {
                start++;
            } else if (x + y > target) {
                end--;
            } else {
                return new int[] { x, y};
            }
        }
        return new int[] { };
    }

    public static void main(String[] args) {
        int[] A = new int[] { 1, 2, 4, 6, 8, 10};
        int[] B = new int[] { 1, 3, 4, 6, 8, 11};
//        System.out.println(hasDuplicates(A));
//        System.out.println(Arrays.toString(removeDuplicatesFrom(A)));
//        System.out.println(Arrays.toString(getDistinct(A, B)));
        System.out.println(Arrays.toString(sumToTarget(5, A)));
    }
}
