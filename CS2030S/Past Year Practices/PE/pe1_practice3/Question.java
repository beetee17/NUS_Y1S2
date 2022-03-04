public abstract class Question {
  private String question;
  public abstract Question answer(int guess);

  public Question(String question) {
    this.question = question;
  }

  public String getQuestion() {
    return this.question;
  }

  public abstract LockedQuestion lock(); 

  @Override
  public String toString() {
    return String.format("%s", this.question);
  }
}
