/**
 * CS2030S PE1 Question 1
 * AY20/21 Semester 2
 *
 * @author A0217651H
 */
public class InvalidOperandException extends RuntimeException {
  public InvalidOperandException(char symbol) {
    super(String.format("ERROR: Invalid operand for operator %c", symbol));
  }
}
