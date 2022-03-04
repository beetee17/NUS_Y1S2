public class TFQ extends MCQ {

  public TFQ(String question, String answer) {
    super(question, new String[] {"True", "False"}, encode(answer));
  }

  private static String decode(int ans) {
    return ans == 1 ? "True" : ans == 2 ? "False" : "?";
  }

  private static int encode(String ans) {
    return ans.equals("True") ? 1 : ans.equals("False") ? 2 : 0;
  }

  @Override
  public TFQ answer(int guess) {
    TFQ res = new TFQ(super.getQuestion(), decode(this.getAnswer()));
    res.setGuess(guess);
    return res;
  }

  public TFQ answer(String guess) {
    TFQ res = new TFQ(super.getQuestion(), decode(this.getAnswer()));
    res.setGuess(encode(guess));
    return res;
  }
}


