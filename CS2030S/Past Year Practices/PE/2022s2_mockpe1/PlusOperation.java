public class PlusOperation extends Operation {

  public PlusOperation(Expression op1, Expression op2) {
    super(op1, op2);
  }

  @Override
  public String eval() {
    if (!(super.getFirst() instanceof String && super.getSecond() instanceof String)) {
      throw new InvalidOperandException('+');
    }
    return (String) super.getFirst() + "" + (String) super.getSecond();
  }
}

