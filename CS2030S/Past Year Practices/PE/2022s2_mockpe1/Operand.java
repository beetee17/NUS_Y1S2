/**
 * CS2030S PE1 Question 1
 * AY20/21 Semester 2
 *
 * @author A0244482A
 */
public class Operand implements Expression {
  Object op;
  public Operand(Object op) {
    this.op = op;
  }

  @Override
  public Object eval() {
    return this.op;
  }
}
