import java.util.List;

class SubTrace<T> extends Trace<T> { 
  private SubTrace(T v, List<T> l) {
    super(v, l);
  }
  static <T> SubTrace<T> of(T v) {
    return new SubTrace<T>(v, List.of(v));
  }
}

