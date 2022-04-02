/**
 * CS2030S PE1 Question 1
 * AY20/21 Semester 2
 *
 * @author A0217651H
 */
abstract class Operation implements Expression {
  protected char operand;
  protected Expression exp1;
  protected Expression exp2;

  protected Operation(char operand, Expression exp1, Expression exp2) {
    this.operand = operand;
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  public static Operation of(char operand, Expression exp1, Expression exp2) {
    if (operand == '*') {
      return new IntegerMultiplicationOperation(operand, exp1, exp2); 
    } else if (operand == '+') {
      return new StringConcatenationOperation(operand, exp1, exp2);
    } else if (operand == '^') {
      return new BooleanXOROperation(operand, exp1, exp2);
    }
    return null;
  }
}
