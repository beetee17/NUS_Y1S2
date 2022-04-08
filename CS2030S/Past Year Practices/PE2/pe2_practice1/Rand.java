import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Rand<T> {
  private final int value;
  private final Function<Integer, ? extends T> f;

  private Rand(int value, Function<Integer, ? extends T> f) {
    this.value = value;
    this.f = f;
  }

  public static Rand<Integer> of(int seed) {
    return new Rand<>(seed, x -> x);
  }

  public T get() {
    return f.apply(this.value);
  }

  public Rand<T> next() {
    return new Rand<>(new Random(this.value).nextInt(Integer.MAX_VALUE), this.f);
  }

  public Stream<T> stream() {
    return Stream.iterate(this, r -> r.next()).map(r -> r.get());
  }

  public static <T> Stream<T> randRange(int seed, Function<Integer, ? extends T> f) {
    return Rand.of(seed).stream().map(x -> f.apply(x));
  }

  public <T> Rand<T> map(Function<Integer, ? extends T> f) {
    return new Rand<>(this.value, f);
  }

  public <T> Rand<T> flatMap(Function<Integer, ? extends Rand<? extends T>> f) {
    return new Rand<>(this.value, x -> f.apply(x).get());
  }


}
