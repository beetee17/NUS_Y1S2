/**
 * Takes an item and return the item in a box.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Brandon (Group 12A)
 */
public class BoxIt<T> implements Transformer<T, Box<T>> {
  public BoxIt() { 
  }
  
  @Override 
  public Box<T> transform(T content) {
    return Box.of(content);
  }
}
