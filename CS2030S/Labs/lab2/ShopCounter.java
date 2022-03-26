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
  

  @Override
  public String toString() {
    return String.format("S%d", this.id);
  }
}
