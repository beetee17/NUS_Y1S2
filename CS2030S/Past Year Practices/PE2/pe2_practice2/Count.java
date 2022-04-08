import java.util.function.Function; 

public class Count<T> extends Aggregate<Integer, T> {

  private Count(T t, Integer count) {
    super(count, t);
  }

  public static <T> Count<T> of(T t) {
    return new Count<>(t, 1);
  }

  @Override
  public Count<T> map(Function<Integer, Integer> f, T t) {
    return new Count<>(t, f.apply(this.getSeed()));
  }

  public Count<T> map(T t) {
    return this.map(x -> x + 1, t);
  }

  @Override
  public String toString() {
    return String.format("(%d, %s)", this.getSeed(), this.getT());
  }
}
