public class FillInBlank extends Question implements LockedQuestion {
  private Grader grader;
  private int guess = 0;

  public FillInBlank(String question, Grader grader) {
    super(question);
    this.grader = grader;
  }

  @Override
  public FillInBlank answer(int guess) {
    FillInBlank res = new FillInBlank(this.getQuestion(), this.grader);
    res.guess = guess;
    return res;
  }

  @Override
  public String toString() {
    return String.format("%s; Your answer: %s", super.toString(), this.guess);
  }

  @Override
  public LockedQuestion lock() {
    return (LockedQuestion) this;
  }

  @Override
  public int mark() {
    return this.grader.grade(this.guess);
  }
}
