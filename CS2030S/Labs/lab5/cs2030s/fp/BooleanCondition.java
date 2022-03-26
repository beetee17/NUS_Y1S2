package cs2030s.fp;

/**
 * A conditional statement that returns either true of false.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public interface BooleanCondition<T> {
  public boolean test(T arg);
}
