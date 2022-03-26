/**
 * A boolean condition with parameter x that can be applied to
 * a string.  Returns true if the string is longer than x; false
 * otherwise.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public class LongerThan implements BooleanCondition<String> {
  int len;

  public LongerThan(int len) {
    this.len = len;
  }

  @Override
  public boolean test(String s) {
    return s.length() > this.len;
  }
}

