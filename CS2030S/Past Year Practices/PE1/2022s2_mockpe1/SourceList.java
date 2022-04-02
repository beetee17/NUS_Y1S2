/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0244482A
 */
interface SourceList<T> {
  T getFirst();

  SourceList<T> getSecond();
  // Write your code here
  
  public int length();

  public SourceList<T> filter(BooleanCondition<? super T> cond);

  public <U> SourceList<U> map(Transformer<? super T, ? extends U> transformer);

}
