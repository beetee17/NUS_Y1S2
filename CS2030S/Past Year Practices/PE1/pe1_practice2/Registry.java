public class Registry {
  private DataStore store = new DataStore();
  private String id;
  private Token[] tokens = new Token[20];

  public Registry(String id) {
    this.id = id;
  }

  public Registry(String id, Token[] tokens) {
    this.id = id;
    for (Token t : tokens) {
      this.tokens[this.numTokens()] = t;
    }
  }

  public Registry add(Token t) {
    Token[] newTokens = new Token[20];
    for (int i = 0; i < this.numTokens(); i++)
      newTokens[i] = this.tokens[i];
    newTokens[this.numTokens()] = t;
    return new Registry(this.id, newTokens);
  }

  public void store(String s) {
    store.write(s);
  }

  public int numTokens() {
    int count = 0;
    for (Token t : this.tokens)
      if (t != null) count++;
    return count;
  }

  public void alert(int time) {
    for (Token t : this.tokens)
      if (t != null) 
        this.store.write(t.checkPings(time).toString());
  }

  @Override
  public String toString() {
    String str = String.format("[%s]: %d tokens registered", this.id, this.numTokens());

    for (Token t : this.tokens) {
      if (t != null)
        str += "\n" + t.toString();
    }

    return str;
  }
}
