import java.util.function.Function;

public class Aggregate<S, T> {
  private final S seed;
  private final T t;

  private Aggregate() {
    this.seed = null;
    this.t = null;
  } 

  protected Aggregate(S seed, T t) {
    this.seed = seed;
    this.t = t;
  }

  public static <S, T> Aggregate<S, T> seed(S seed) {
    return new Aggregate<>(seed, null);
  }

  public static <S, T> Aggregate<S, T> of(Function<S, Pair<S, T>> f) {
    return new ValidAggregate<S, T>(f);
  }

  public S getSeed() {
    return this.seed;
  }

  public T getT() {
    return this.t;
  }

  public Aggregate<S, T> map(Function<S, S> f, T t) {
    return new Aggregate<>(f.apply(this.seed), t);
  }

  public Aggregate<S, T> map(Function<S, Pair<S, T>> f) {
    Pair<S, T> result = f.apply(this.seed);
    return new Aggregate<>(result.first(), result.second());
  }

  public <U> Aggregate<S, U> flatMap(Function<T, Aggregate<S, U>> f) {
    Aggregate<S, U> agg = f.apply(this.t);
    Pair<S, U> result = agg.apply(this.seed);
    return new Aggregate<>(result.first(), result.second());
  }

  public Pair<S, T> apply(S s) {
    // Should not be called. Only called in context of flatMap, which
    // only accepts ValidAggregate
    return null;
  }

  @Override
  public String toString() {
    if (this.t == null) {
      return String.format("(%s)", this.seed);
    } else {
      return String.format("(%s, %s)", this.seed, this.t);
    }
  }

  private static final class ValidAggregate<S, T> extends Aggregate<S, T> {
    private final Function<S, Pair<S, T>> f;

    private ValidAggregate(Function<S, Pair<S, T>> f) {
      super();
      this.f = f;
    }

    @Override
    public Pair<S, T> apply(S s) {
      return this.f.apply(s);
    }

    @Override
    public Aggregate<S, T> map(Function<S, S> f, T t) {
      return new InvalidAggregate<>();
    }

    @Override
    public Aggregate<S, T> map(Function<S, Pair<S, T>> f) {
      return new InvalidAggregate<>();
    }

    @Override
    public <U> Aggregate<S, U> flatMap(Function<T, Aggregate<S, U>> f) {
      return new InvalidAggregate<>();
    }

    @Override
    public String toString() {
      return "Aggregate";
    }
  }

  private static final class InvalidAggregate<S, T> extends Aggregate<S, T> {

    @Override
    public Aggregate<S, T> map(Function<S, S> f, T t) {
      return this;
    }

    @Override
    public Aggregate<S, T> map(Function<S, Pair<S, T>> f) {
      return this;
    }

    @Override
    public <U> Aggregate<S, U> flatMap(Function<T, Aggregate<S, U>> f) {
      return new InvalidAggregate<>();
    }

    @Override
    public String toString() {
      return "Invalid Aggregate";
    }
  }
}
