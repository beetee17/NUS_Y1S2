public class MultiplyOperation extends Operation {

  public MultiplyOperation(Expression op1, Expression op2) throws InvalidOperandException {
    super(op1, op2);
  }

  @Override
  public Integer eval() {

    if (!(super.getFirst() instanceof Integer && super.getSecond() instanceof Integer)) {
      throw new InvalidOperandException('*');
    }
    return (Integer) super.getFirst()  * (Integer) super.getSecond();
  }
}

