/**
 * The Transformer interface that can transform a type T
 * to type U.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public interface Transformer<T, U> {
  public U transform(T arg);
}


