import java.util.List;
import java.util.ArrayList;

/**
 * A dummy subclass for Trace for Testing
 * CS2030S Mock PE 2
 * AY20/21 Semester 2
 * 
 * @author A0024482A
 */
public class Trace<T> {
  private T value;
  private List<T> history;

  protected Trace(T value, List<T> history) {
    this.value = value;
    this.history = history;
  }

  @SafeVarargs
  public static <T> Trace<T> of(T value, T... history) {
    List<T> temp = new ArrayList<>();
    for (T t : history) {
      temp.add(t);
    }
    return new Trace<>(value, temp);
  }

  public T get() {
    return this.value;
  }

  public List<T> history() {
    return this.history;
  }

  public Trace<T> back(int steps) {
    int len = this.history.size();
    int i = Math.max(0, len - steps);
    T newValue = this.history.get(i);
    List<T> newHistory = this.history.subList(0, i);

    return new Trace<>(newValue, newHistory);
  }

  public Trace<T> map(Transformer<? super T, ? extends T> f) {
    List<T> newHistory = new ArrayList<>();
    newHistory.addAll(this.history);
    newHistory.add(this.value);
    
    return new Trace<>(f.transform(this.value), newHistory);
  }

  public Trace<T> flatMap(Transformer<? super T, ? extends Trace<? extends T>> f) {
    List<T> newHistory = new ArrayList<>();
    newHistory.addAll(this.history);
    
    Trace<? extends T> newTrace = f.transform(this.value);
    newHistory.add(this.value);
    newHistory.addAll(newTrace.history);
    
    return new Trace<T>(newTrace.value, newHistory);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof Trace<?>)) {
      return false;
    }
    // semantic comparison here
    Trace<?> other = (Trace<?>) o;


    if (this.history.size() != other.history.size()) {
      return false;
    }

    for (int i = 0; i < this.history.size(); i++) {
      if (!this.history.get(i).equals(other.history.get(i))) {
        return false;
      }
    }

    if (this.value == null) {
      return this.value == other.value;
    }

    return this.value.equals(other.value); 
  }

    
}
