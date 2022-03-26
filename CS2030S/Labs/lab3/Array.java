/**
 * The Generic Array for CS2030S 
 *
 * @author Brandon (Group 12A)
 * @version CS2030S AY21/22 Semester 2
 */
class Array<T extends Comparable<T>> { 
  private T[] array;

  public Array(int size) {
    // Note: We can suppress warnings here as array is guaranteed to be of type T[]
    @SuppressWarnings("unchecked")
    T[] tmp = (T[]) new Comparable[size];
    this.array = tmp;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public int length() {
    return this.array.length;
  }

  public T min() {
    T currMin = null;
    for (T item : array) {
      if (currMin == null) { 
        currMin = item;
      } else if (item.compareTo(currMin) == -1) {
        // Due to the way counter ids are assigned, this 
        // coincidentally breaks ties with the smallest id
        // i.e. it does not override currMin if they are equal
        currMin = item;
      }
    }
    return currMin;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
