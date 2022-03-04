/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0244482A
 */
public class EmptyList<T> implements SourceList<T> {
  
  @Override
  public T getFirst() {
    return null;
  }

  @Override
  public SourceList<T> getSecond() {
    return null;
  }

  @Override
  public String toString() {
    return "EmptyList";
  }

  @Override
  public <U> EmptyList<U> map(Transformer<? super T, ? extends U> transformer) {
    return new EmptyList<U>();
  }

  @Override
  public EmptyList<T> filter(BooleanCondition<? super T> cond) {
    return this;
  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj || obj instanceof EmptyList) return true;
    return false;
  }
}
