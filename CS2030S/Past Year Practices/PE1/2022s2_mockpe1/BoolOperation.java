public class BoolOperation extends Operation {

  public BoolOperation(Expression op1, Expression op2) {
      super(op1, op2);
  }

  @Override
  public Boolean eval() {
    if (!(super.getFirst() instanceof Boolean && super.getSecond() instanceof Boolean)) {
      throw new InvalidOperandException('^');
    } else {
    }
    return (Boolean) super.getFirst() ^ (Boolean) super.getSecond();
  }
}

