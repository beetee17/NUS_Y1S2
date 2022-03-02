public class RegisteredToken extends Token {

  private Registry r;

  public RegisteredToken(Token t, Registry r) {
    super(t);
    this.r = r;
  }

  public void contact() {
    r.store("Test contact");
  }
}
