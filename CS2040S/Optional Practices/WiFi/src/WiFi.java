import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {

        double begin = 0;
        double end = Integer.MAX_VALUE;

        while (begin <= end) {
            double mid = begin + (end-begin) / 2;
            if (coverable(houses, numOfAccessPoints, mid)) {
                end = mid - 0.5;
            } else {
                begin = mid + 0.5;
            }
        }
        return begin;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        Arrays.sort(houses);

        double currCoverage = 0;
        int numUsed = 0;
        for (int i = 0; i < houses.length; i++) {
            if (currCoverage < houses[i]) {
                numUsed += 1;
                currCoverage = houses[i] + 2*distance;
            }
        }

        return numUsed <= numOfAccessPoints;
    }
    public static void main(String[] args) {
        int[] houses = {1, 3, 10, 12, 15, 120, 180, 200, 250};
        int numAccessPoints = 1;
        System.out.println("ANSWER: " + computeDistance(houses, numAccessPoints));
    }
}
