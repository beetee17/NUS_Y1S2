/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0000000X
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
  public <U> SourceList<U> map(Transformer<? super T, ? extends U> transformer) {
    SourceList<U> wish = this.getSecond().map(transformer);
    return new Pair<U>(transformer.transform(this.first), wish);
  }


  @Override
  public SourceList<T> filter(BooleanCondition<? super T> cond) {
    SourceList<T> wish = this.getSecond().filter(cond);
    if (cond.test(this.first)) {
      return new Pair<>(this.first, wish); 
    } else {
      return wish; 
    }
  }

  @Override
  public int length() {
    return 1 + this.getSecond().length();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { 
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj instanceof Pair) {
      Pair other = (Pair) obj;
      return this.first.equals(other.first) && this.getSecond().equals(other.getSecond());
    }
    return false;
  }
}
