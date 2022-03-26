/** 
 * @author Brandon (Group 12A)
 */
class ShopCounter implements Comparable<ShopCounter> {
  private static int numCounters = 0;
  private boolean available;
  private int id;
  private Queue<ShopCustomer> queue;

  public ShopCounter(int maxQueueSize) {
    this.available = true;
    this.id = numCounters++;
    this.queue = new Queue<ShopCustomer>(maxQueueSize);
  }
  
  public int getID() {
    return this.id;
  }

  public boolean queueEmpty() {
    return this.queue.isEmpty();
  }

  public boolean queueFull() {
    return this.queue.isFull();
  }

  public String queueStatus() {
    return this.queue.toString();
  }

  public boolean enqueue(ShopCustomer customer) {
    return this.queue.enq(customer);
  }

  public ShopCustomer dequeue() {
    return this.queue.deq();
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailabilityTo(boolean newValue) {
    this.available = newValue;
  }
  
  @Override
  public int compareTo(ShopCounter other) {
    Integer len = this.queue.length();
    Integer otherLen = other.queue.length();
    return len.compareTo(otherLen);
  }

  @Override
  public String toString() {
    return String.format("S%d %s", this.id, this.queueStatus());
  }
}
