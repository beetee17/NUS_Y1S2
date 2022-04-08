import java.util.ArrayList;
import java.util.function.Function;
import java.util.List;

public class Room {
  private final String name;
  private final List<Item> items;
  private final Room prev;

  public Room(String name) {
    this.name = name;
    this.items = new ArrayList<>();
    this.prev = null;
  }

  public Room(String name, List<Item> items) {
    this.name = name;
    this.items = items;
    this.prev = null;
  }

  public Room(String name, List<Item> items, Room prev) {
    this.name = name;
    this.items = items;
    this.prev = prev;
  }

  public Room add(Item item) {
    List<Item> newItems = new ArrayList<>();
    newItems.addAll(this.items);
    newItems.add(item);

    return new Room(this.name, newItems, this.prev);
  }

  public Room tick() {
    List<Item> newItems = new ArrayList<>();
    for (Item item : this.items) {
      newItems.add(item.tick());
    }

    return new Room(this.name, newItems, this.prev);
  }

  public Room tick(Function<List<Item>, List<Item>> f) {
    return new Room(this.name, f.apply(this.items), this.prev).tick();
  }

  public Room go(Function<List<Item>, Room> f) {
    List<Item> takenItems = new ArrayList<>();
    for (Item item : this.items) {
      if (item.isTaken()) {
        takenItems.add(item);
      }
    }

    Room temp = f.apply(this.items);
    takenItems.addAll(temp.items);

    return new Room(temp.name, takenItems, this);
  }

  public Room back() {
    List<Item> takenItems = this.prev.tick().items;
    for (Item item : this.items) {
      if (item.isTaken()) {
        takenItems.add(item);
      }
    }
    
    return new Room(this.prev.name, takenItems, this.prev.prev);
  }

  @Override
  public String toString() {
    String s = String.format("@%s", this.name);
    for (Item item : this.items) {
      s += "\n" + item.toString();
    }
    return s;
  }
}
