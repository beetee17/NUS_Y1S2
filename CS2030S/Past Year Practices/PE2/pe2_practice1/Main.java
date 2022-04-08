import java.util.List;
import java.util.stream.Stream;
import java.util.function.Function;

public class Main {
  public static double simulate(int seed, int n) {
    Circle c = new Circle(new Point(0.0, 0.0), 1.0);

    double lo = -1.0;
    double hi = 1.0;

    Function<Integer, Double> range = x -> (hi - lo) * x / (Integer.MAX_VALUE - 1) + lo;


    Stream<Point> points = Rand.of(seed)
      .flatMap(x -> Rand.of(x).map(y -> new Point(range.apply(x), range.apply(y))).next())
      .stream()
      .flatMap(new Function<Point, Stream<Point>>() {
        int i = 0;

        @Override
        public Stream<Point> apply(Point t) {
          if (i++ % 2 == 0) {
            return Stream.of(t);
          }
          return Stream.empty();
        }
      })
    .limit(n);

   long inc = points.filter(p -> c.contains(p)).count();

   return (inc / (double) n) * 4;

  }
}
