/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0217651H
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
  public int length() {
    return 0;
  }

  @Override
  public SourceList<T> filter(BooleanCondition<? super T> cond) {
    return new EmptyList<>();
  }

  @Override
  public <U> SourceList<U> map(
      Transformer<? super T, ? extends U> transformer) {
    return new EmptyList<>();    
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o instanceof EmptyList<?>) {
      return true;
    }
    return false;
  } 
}
