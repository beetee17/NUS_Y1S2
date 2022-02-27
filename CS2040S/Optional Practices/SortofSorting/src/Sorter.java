import java.util.Arrays;

class Sorter {

    public static void sortStrings(String[] arr) {
        if (arr.length <= 1)
            return;

        int start = 0;
        int end = arr.length;
        int mid = start + Math.floorDiv(end - start, 2);

        String[] left = new String[mid];
        String[] right = new String[end-mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[i+mid];
        }

        sortStrings(left);
        sortStrings(right);

        String[] sorted = merge(left, right);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = sorted[i];
        }
    }

    public static String[] merge(String[] left, String[] right) {
        String[] temp = new String[left.length + right.length];
        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = 0; i < temp.length; i++) {
            if (leftIndex >= left.length) {
                temp[i] = right[rightIndex];
                rightIndex++;
            } else if (rightIndex >= right.length) {
                temp[i] = left[leftIndex];
                leftIndex++;
            } else if (isGreaterThan(left[leftIndex], right[rightIndex])) {
                temp[i] = right[rightIndex];
                rightIndex++;
            } else {
                temp[i] = left[leftIndex];
                leftIndex++;
            }
        }

        return temp;

    }
    public static boolean isGreaterThan(String str1, String str2) {
        //your implementation here
        if (str1.charAt(0) == str2.charAt(0)) {
            return str1.charAt(1) > str2.charAt(1);
        }
        return str1.charAt(0) > str2.charAt(0);
    }
}
