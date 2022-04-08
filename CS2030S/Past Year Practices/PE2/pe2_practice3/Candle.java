import java.util.List;

public class Candle implements Item {
  private static final List<String> STATES = 
      List.of("Candle flickers.", "Candle is getting shorter.", "Candle is about to burn out.", "Candle has burned out.");

  private final int state;
  private final boolean taken;

  public Candle() {
    this.state = 0;
    this.taken = false;
  }

  private Candle(int state, boolean taken) {
    this.state = state;
    this.taken = taken;
  }

  @Override
  public Candle tick() {
    return new Candle(Math.min(STATES.size() - 1, this.state + 1), this.taken);
  }

  @Override
  public Candle take() {
    return new Candle(this.state, true);
  }

  @Override
  public Candle drop() {
    return new Candle(this.state, false);
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
