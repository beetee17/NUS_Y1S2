/**
 * CS2030S PE1 Question 1
 * AY20/21 Semester 2
 *
 * @author A0244482A
 */
public class InvalidOperandException extends RuntimeException {
  char op;

  public InvalidOperandException(char op) {
    super("");
    this.op = op;
  }

  @Override
  public String getMessage() {
    return String.format("ERROR: Invalid operand for operator %s", this.op);
  }
}


