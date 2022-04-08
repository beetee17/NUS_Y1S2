import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

public class Main {
  public static Stream<Integer> twinPrimes(int n) {
    Stream<Integer> two_to_n = Stream.iterate(2, i -> i + 1).limit(n - 1);

    Predicate<Integer> isPrime = i -> {
      if (i == 0) {
        return false;
      } 

      for (int j = 2; j < i; j++) {
        if (i % j == 0) {
          return false;
        }
      }
      return true;
    };

    return two_to_n.filter(i -> isPrime.test(i) && (isPrime.test(i - 2) || isPrime.test(i + 2)));
  }

  public static String reverse(String str) {
    return Stream.of(str.split("")).reduce("", (c1, c2) -> c2 + c1);
  }

  public static long countRepeats(List<Integer> list) {
    return Stream.<Integer>iterate(0, i -> i + 1)
      .limit(list.size())
      .filter(i -> {
        if (i > 0 && list.get(i) == list.get(i-1)) {
          if (i < list.size() - 1 && list.get(i) != list.get(i+1)) {
            return true;
          }
        }
        return false;
      })
    .count();
  }

  public static UnaryOperator<List<Integer>> generateRule() {
    return new UnaryOperator<>() {
       int ALIVE = 1;
       int DEAD = 0;
      
      @Override
      public List<Integer> apply(List<Integer> list) {
      List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i) == DEAD) {
            if (i == 0 && list.get(i+1) == ALIVE) {
              result.add(ALIVE);
            } else if (i == list.size() - 1 && list.get(i - 1) == ALIVE) {
              result.add(ALIVE);
            } else if (i != 0 && i != list.size() - 1 && list.get(i + 1) == ALIVE ^ list.get(i - 1) == ALIVE) {
              result.add(ALIVE);
            } else {
              result.add(DEAD);
            }
          } else {
            result.add(DEAD);
          }
        }
        return result;
      }
    };
  }

  /*
   * Define the gameOfLife method that takes in a List<Integer> list as the starting population, the rule 
   * in the form UnaryOperator<List<Integer> and the integer number of generations n of the game. 
   * The method returns a Stream<String> of the game of life for n (> 0) generations where a list 
   * element 0 is represented as a blank space, and 1 is represented as an asterisk *.
   */
   static Stream<String> gameOfLife(List<Integer> list, UnaryOperator<List<Integer>> rule, int n) {
      Stream<List<Integer>> games = Stream.iterate(list, rule).limit(n);
      Function<List<Integer>, String> f = new Function<>() {
        @Override
        public String apply(List<Integer> list) {
          String s = "";
          for (int x : list) {
            s += x == 0 ? " " : "*";
          }
          return s;
        }
      };
      return games.map(f);
   }

   static double normalizedMean(Stream<Integer> stream) {
     int min = stream.min((a, b) -> a - b).get();
     int max = stream.max((a, b) -> a - b).get();
     Stream<Double> normalised = stream.map(i -> (double) (i - min) / (double) (max - min));
     return normalised.reduce(0.0, (a, b) -> a+b) / (double) stream.count();
   }
}
