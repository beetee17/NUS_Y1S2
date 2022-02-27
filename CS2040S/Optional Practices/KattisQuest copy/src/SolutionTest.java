import java.util.Arrays;

import static org.junit.Assert.*;

public class SolutionTest {

    @org.junit.Test
    public void test() {
        int NUM_TESTS = 10000;
        Solution solution = new Solution();
        for (int i = 0; i < NUM_TESTS; i++) {
            System.out.println("TEST " + i);
            int n = (int) (Math.random() * 10);
            for (int j = 0; j < n; j++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 5) {
                    long remainingEnergy = (long) (Math.random() * 100);
                    long answer = solution.naiveQuery(remainingEnergy);
                    long output = solution.query(remainingEnergy);
                    assertEquals(answer, output);
                } else {
                    long energy = (long) (Math.random() * 100);
                    long value = (long) (Math.random() * 200);
                    solution.add(energy, value);
                }
            }
        }
    }
}