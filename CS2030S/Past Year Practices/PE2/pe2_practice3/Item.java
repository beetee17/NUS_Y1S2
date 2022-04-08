import java.util.List;

public interface Item {
  public Item tick();
  public Item take();
  public Item drop();
  public boolean isTaken();
}
