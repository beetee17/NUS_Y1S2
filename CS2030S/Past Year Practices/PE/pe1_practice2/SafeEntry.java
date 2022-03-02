public class SafeEntry extends Token {
  DataStore store = new DataStore();
  public SafeEntry(int id, int time) {
    super(id);
    super.ping(new Token(id), time);
  }
  
  public SafeEntry(int id) {
    super(id);
  }

  @Override
  public SafeEntry checkPings(int time) {
    int pings = super.checkPings(time).getPingCount();
    return pings == 0 ? new SafeEntry(super.getID()) : this;
  }

  @Override
  public String getPrefix() {
    return super.hasNoPings() ? String.format("SafeEntry #%d:", super.getID()) : "SafeEntry";
  }
}
