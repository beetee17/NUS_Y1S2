/** 
 * @author Brandon (Group 12A)
 */
class ShopCounter {
  private boolean available;
  private int id;

  public ShopCounter(int id) {
    this.available = true;
    this.id = id;
  }
  
  public int getID() {
    return this.id;
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailabilityTo(boolean newValue) {
    this.available = newValue;
  }
  
  /**
   * Returns the next available counter
   * Returns null if all counters are unavailable
   *
   * @param counters The array of counters to perform the search
   */
  public static ShopCounter getNextAvailable(ShopCounter[] counters) {
    for (int i = 0; i < counters.length; i++) {
      if (counters[i].isAvailable()) {
        return counters[i];
      }
    }
    return null;
  }
}
