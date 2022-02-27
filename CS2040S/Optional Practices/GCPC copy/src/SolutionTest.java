import java.util.Arrays;

import static org.junit.Assert.*;

public class SolutionTest {

    @org.junit.Test
    public void update() {
        Solution soln = new Solution(3);
        assertEquals(2, soln.update(2, 7));
        assertEquals(3, soln.update(3, 5));
        assertEquals(2, soln.update(1, 6));
        assertEquals(1, soln.update(1, 9));
    }

//    @org.junit.Test
//    public void test() {
//        int NUM_TESTS = 100;
//        for (int i = 0; i < NUM_TESTS; i++) {
//            System.out.println("TEST " + i);
//            int n = 5;
//            Solution solution = new Solution(n);
//            for (int j = 0; j < (int) (Math.random() * 10); j++) {
//
//                int team = (int) (Math.random() * n) + 1;
//                int newPenalty = (int) (Math.random() * 50);
//
//                int answer = solution.naiveUpdate(team, newPenalty);
//                int output = solution.update(team, newPenalty);
//                try {
//                    assertEquals(answer, output);
//                } catch (AssertionError e) {
//                    System.out.println(e);
//                    TreePrinter.print(solution.tree.root);
//                    System.out.println("TEAMS: " + Arrays.toString(solution.teams));
//                    System.out.println("ARRAY: " + solution.array);
//                    return;
//                }
//
//            }
//        }
//    }
}