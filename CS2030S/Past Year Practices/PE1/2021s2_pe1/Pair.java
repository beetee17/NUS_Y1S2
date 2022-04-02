/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0217651H
 */
public class Pair<T> implements SourceList<T> {
  private T first;
  private SourceList<T> second;

  public Pair(T first, SourceList<T> second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public T getFirst() {
    return this.first;
  }

  @Override
  public SourceList<T> getSecond() {
    return this.second;
  }

  @Override
  public String toString() {
    return this.first + ", " + this.second;
  }

  @Override
  public int length() {
    return 1 + second.length(); 
  }

  @Override
  public SourceList<T> filter(BooleanCondition<? super T> cond) {
    return cond.test(first) 
        ? new Pair<>(first, second.filter(cond))
        : second.filter(cond);
  }

  @Override
  public <U> SourceList<U> map(
      Transformer<? super T, ? extends U> transformer) {
    return new Pair<>(transformer.transform(first),
        second.map(transformer));   
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    } 

    if (o instanceof Pair<?>) {
      Pair<?> other = (Pair<?>) o;
      return first.equals(other.first) && second.equals(other.second);
    }
    return false;
  } 
}
