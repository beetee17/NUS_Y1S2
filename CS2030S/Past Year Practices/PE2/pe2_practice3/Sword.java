import java.util.List;

public class Sword implements Item {
  private static final List<String> STATES = 
      List.of("Sword is shimmering.");

  private final int state;
  private final boolean taken;

  public Sword() {
    this.state = 0;
    this.taken = false;
  }

  private Sword(int state, boolean taken) {
    this.state = state;
    this.taken = taken;
  }

  @Override
  public Sword tick() {
    return new Sword(Math.min(STATES.size() - 1, this.state + 1), this.taken);
  }

  @Override
  public Sword take() {
    return new Sword(this.state, true);
  }

  @Override
  public Sword drop() {
    return new Sword(this.state, false);
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
