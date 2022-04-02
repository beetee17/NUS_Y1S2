public class OffByOneGrader implements Grader {
  private int answer;
  public OffByOneGrader(int answer) {
    this.answer = answer;
  }

  @Override
  public int grade(int guess) {
    int off = Math.abs(this.answer - guess);
    return off == 0 ? 2 : off == 1 ? 1 : 0;
  }
}
