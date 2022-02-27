import java.util.*;

class Solution {

    private static class Team implements Comparable<Team> {
        long numSolved;
        long totalPenalty;
        int id;
        public Team(long numSolved, long totalPenalty, int id) {
            this.numSolved = numSolved;
            this.totalPenalty = totalPenalty;
            this.id = id;
        }
        @Override
        public String toString() {
            return String.format("%s (%s, %s)", this.id, this.numSolved, this.totalPenalty);
        }

        @Override
        public int compareTo(Team other) {
            Long numSolved = this.numSolved;
            Long totalPenalty = this.totalPenalty;
            if (numSolved == other.numSolved) {
                int tieBreak = -(totalPenalty.compareTo(other.totalPenalty));
                if (tieBreak == 0) {
                    Integer id = this.id;
                    return -(id.compareTo(other.id));
                } else {
                    return tieBreak;
                }
            }
            return numSolved.compareTo(other.numSolved);
        }
        public void update(int newPenalty) {
            this.numSolved += 1;
            this.totalPenalty += newPenalty;
        }
    }

    public HashSet<Integer> betterTeams;
    public Team[] allTeams;

    public Solution(int numTeams) {
        this.betterTeams = new HashSet<Integer>();
        this.allTeams = new Team[numTeams];

        for(int i = 0; i < numTeams; i++)
            this.allTeams[i] = new Team(0, 0, i + 1);
    }

    public int update(int team, int newPenalty) {

        allTeams[team - 1].update(newPenalty);

        if (team - 1 == 0) {
            HashSet<Integer> toRemove = new HashSet<Integer>();

            for (int i : betterTeams)
                if (allTeams[0].compareTo(allTeams[i]) >= 0) toRemove.add(i);

            for (int j : toRemove)
                betterTeams.remove(j);
        }

        if (allTeams[team - 1].compareTo(allTeams[0]) > 0)
            betterTeams.add(team - 1);

        return betterTeams.size() + 1;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Solution solution = new Solution(n);
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int team = sc.nextInt(), penalty = sc.nextInt();
            System.out.println(solution.update(team, penalty));
        }
    }
}
