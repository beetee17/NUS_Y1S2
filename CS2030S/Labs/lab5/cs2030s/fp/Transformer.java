package cs2030s.fp;

/**
 * CS2030S Lab 5
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public interface Transformer<T, U> {
  public U transform(T arg);
}
