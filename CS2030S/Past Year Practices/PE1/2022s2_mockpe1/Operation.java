/**
 * CS2030S PE1 Question 1
 * AY20/21 Semester 2
 *
 * @author A0244482A
 */
public abstract class Operation implements Expression {
  Expression op1;
  Expression op2;

  protected Operation(Expression op1, Expression op2) {
    this.op1 = op1;
    this.op2 = op2;
  }

  public Object getFirst() {
    return this.op1.eval();
  }

  public Object getSecond() {
    return this.op2.eval();
  }

  public static Operation of(char operation, Expression op1, Expression op2) throws InvalidOperandException {
      if (operation == '*') {
        return new MultiplyOperation(op1,  op2); 

      } else if (operation == '+') {
        return new PlusOperation( op1,  op2);

      } else if (operation == '^') {
        return new BoolOperation( op1,  op2);

      } else {
        return null;
      }

  }

}

