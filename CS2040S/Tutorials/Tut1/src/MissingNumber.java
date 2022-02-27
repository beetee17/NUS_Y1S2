public class MissingNumber {
    /**
     * Given a sorted array of n-1 unique eleents in the range [1,n], find the missing element
     * @param arr
     * @return
     */
    public static int naiveSearch(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i+1) {
                return i+1;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] arr) {
        int begin = 0;
        int end = arr.length - 1;

        while (begin < end) {
            int mid = begin + Math.floorDiv(end-begin, 2);
            if (arr[mid] == mid + 1) {
                // all numbers up to mid + 1 is in arr
                //  we only need to look in the right half
                begin = mid + 1;
            } else {
                // all numbers after mid is in arr
                // we only need to look in the left half
                end = mid;
            }
        }
        return begin + 1;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 4, 5, 6, 7, 8, 9};
        System.out.println("The missing number is: " + naiveSearch(arr));
        System.out.println("The missing number is: " + binarySearch(arr));
    }
}
