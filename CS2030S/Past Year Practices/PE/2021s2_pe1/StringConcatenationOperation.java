/**
 * CS2030S PE1 Question 2
 * AY20/21 Semester 2
 *
 * @author A0217651H
 */
class StringConcatenationOperation extends Operation {
  public StringConcatenationOperation(
      char operand, Expression exp1, Expression exp2) {
    super(operand, exp1, exp2); 
  }

  @Override
  public Object eval() throws InvalidOperandException {
    Object first = exp1.eval();
    Object second = exp2.eval();
    if (first instanceof String && second instanceof String) {
      return (String) first + (String) second;
    }
    throw new InvalidOperandException(operand);
  }
}
