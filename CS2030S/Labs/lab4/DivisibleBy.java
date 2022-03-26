/**
 * A boolean condition with an integer parameter y that can be 
 * apply to another integer x.  Returns true if x is divisible 
 * by y, false otherwise.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public class DivisibleBy implements BooleanCondition<Integer> {
  private int y;

  public DivisibleBy(int y) {
    this.y = y;
  }

  @Override
  public boolean test(Integer x) {
    return x % y == 0;
  }
}

