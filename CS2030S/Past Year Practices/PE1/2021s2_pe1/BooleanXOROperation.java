/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0217651H
 */
public class BooleanXOROperation extends Operation {
  public BooleanXOROperation(
      char operand, Expression exp1, Expression exp2) {
    super(operand, exp1, exp2); 
  }

  @Override
  public Object eval() throws InvalidOperandException {
    Object first = exp1.eval();
    Object second = exp2.eval();
    if (first instanceof Boolean && second instanceof Boolean) {
      return (Boolean) first ^ (Boolean) second;
    }
    throw new InvalidOperandException(operand);
  }
}
