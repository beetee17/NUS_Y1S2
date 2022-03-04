public class MCQ extends Question implements LockedQuestion {
  private int answer;
  String[] options;
  private int guess = 0;

  public MCQ(String question, String[] options, int answer) {
    super(question);
    this.options = options;
    this.answer = answer;
  }

  public int getAnswer() {
    return this.answer;
  }

  public void setGuess(int guess) {
    this.guess = guess;
  }

  @Override
  public MCQ answer(int guess) {
    MCQ res = new MCQ(this.getQuestion(), this.options, this.answer);
    res.setGuess(guess);
    return res;
  }

  @Override
  public String toString() {
    String options = "";
    for (int i = 0; i < this.options.length; i++) {
      options += String.format("[%s:%s]", i+1, this.options[i]);
    }

    String guess; 
    if (this.guess == 0) {
      guess = "?";
    } else {
      guess = String.format("%s:%s", this.guess, this.options[this.guess-1]);
    }
    return String.format("%s %s; Your answer: [ %s ]", super.toString(), options, guess);
  }


  @Override
  public LockedQuestion lock() {
    return (LockedQuestion) this;
  }
  
  @Override
  public int mark() {
    return this.answer == this.guess ? 1 : 0;
  }
}

