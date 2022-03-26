/**
 * A transformer with a parameter k that takes in an object x 
 * and outputs the last k digits of the hash value of x.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public class LastDigitsOfHashCode implements Transformer<Object, Integer> {
  private int numDigits;

  public LastDigitsOfHashCode(int k) {
    this.numDigits = (int) Math.pow(10, k);
  }

  @Override
  public Integer transform(Object obj) {
    return Math.abs(obj.hashCode() % this.numDigits);
  }
}

