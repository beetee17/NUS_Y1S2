public class Ping {
  private Token t;
  private int time;
  public Ping(Token t, int time) {
    this.t = t;
    this.time = time;
  }

  public boolean check(int time) {
    return this.time == time;
  }
  
  @Override
  public String toString() {
    return String.format("#%d@%d", this.t.getID(), this.time);
  }
}
