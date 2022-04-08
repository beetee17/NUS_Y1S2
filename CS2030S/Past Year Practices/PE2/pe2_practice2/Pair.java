public class Pair<S, T> {
  private final S first;
  private final T second;

  private Pair(S first, T second) {
    this.first = first;
    this.second = second;
  }

  public static <S, T> Pair<S, T> of(S first, T second) {
    return new Pair<>(first, second);
  }

  public S first() {
    return this.first;
  }

  public T second() {
    return this.second;
  }
}
