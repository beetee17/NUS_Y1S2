public class Token {

  private int id;
  private Ping[] pings = new Ping[20];

  public Token(int id) {
    this.id = id;
  }

  public Token(Token t) {
    this.id = t.id;
    this.pings = t.pings;
  }

  public boolean hasNoPings() {
    return getPingCount() == 0;
  }

  public int getPingCount() {
    int count = 0;
    for (Ping p : this.pings) 
      if (p != null) count++; 
    return count;

  }

  public Token checkPings(int time) {
    Token newToken = new Token(this.id);
    int n = this.pings.length;
    Ping[] pings = new Ping[n];
    for (int i = 0 ; i < n; i++) {
      Ping p = this.pings[i];
      if (p != null && p.check(time))
        newToken.pings[i] = p;
    }
    return newToken;
  }

  public String getPrefix() {
    return String.format("Token #%d:", this.id);
  }

  @Override
  public String toString() {
    if (this.hasNoPings()) {
      return String.format("%s none", this.getPrefix());
    } else {
      String str =  this.getPrefix();
      for (Ping p : this.pings) {
        if (p != null) str += " " + p;
      }
      return str;
    }
  }

  public int getID() {
    return this.id;
  }

  public Token ping(Token other, int time) {
    this.pings[other.id - 1] = new Ping(other, time);
    return this;
  }
}

