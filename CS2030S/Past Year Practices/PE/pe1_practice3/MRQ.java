public class MRQ extends Question implements LockedQuestion {
  private String[] options;
  private int[] answers;
  private boolean[] guesses;

  public MRQ(String question, String[] options, int[] answers) {
    super(question);
    this.options = options;
    this.answers = answers;
    this.guesses = new boolean[options.length];
    for (int i = 0; i < options.length; i++) {
      this.guesses[i] = false;
    }
  }

  @Override
  public MRQ answer(int guess) {
    MRQ res = new MRQ(super.getQuestion(), this.options, this.answers);
    for (int i = 0; i < this.guesses.length; i++) {
      res.guesses[i] = this.guesses[i];
    }
    res.guesses[guess-1] = !res.guesses[guess-1];
    return res;
  }
  @Override
  public LockedQuestion lock() {
    return (LockedQuestion) this;
  }
  

  @Override
  public String toString() {
    String options = "";
    for (int i = 0; i < this.options.length; i++) {
      options += String.format("[%s:%s]", i+1, this.options[i]);
    }

    String guess = "";
    for (int i = 0; i < this.guesses.length; i++) {
      if (this.guesses[i]) {
        guess += " " + (i+1);
      }
    }

    return String.format("%s %s; Your answer: [%s ]", super.toString(), options, guess);
  }

  @Override
  public int mark() {
    for (int answer : this.answers) {
      if (!this.guesses[answer-1]) {
        return 0;
      }
    }
    return 1;
  }
}

