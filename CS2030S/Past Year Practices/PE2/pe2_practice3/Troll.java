import java.util.List;

public class Troll implements Item {
  private static final List<String> STATES = 
      List.of("Troll lurks in the shadows.", "Troll is getting hungry.", "Troll is VERY hungry.", 
      "Troll is SUPER HUNGRY and is about to ATTACK!", "Troll attacks!");

  private final int state;
  private final boolean taken;

  public Troll() {
    this.state = 0;
    this.taken = false;
  }

  private Troll(int state, boolean taken) {
    this.state = state;
    this.taken = taken;
  }

  @Override
  public Troll tick() {
    return new Troll(Math.min(STATES.size() - 1, this.state + 1), this.taken);
  }

  @Override
  public Troll take() {
    return new Troll(this.state, true);
  }

  @Override
  public Troll drop() {
    return new Troll(this.state, false);
  }

  @Override
  public boolean isTaken() {
    return this.taken;
  }

  @Override
  public String toString() {
    return STATES.get(this.state);
  }
}
