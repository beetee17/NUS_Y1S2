/**
 * This class implements a shop.
 * The shop manages the logistics of customer movement/ event assignment.
 * It has a queue of customers and an array of counters.
 *
 * @author Brandon (Group 12A) 
 * @version CS2030S AY21/22 Semester 2
 */ 
class Shop {
  /**
   * The queue of customers
   */
  private Queue<ShopCustomer> entranceQueue;

  /** 
   * The counters in the shop. 
   */
  private Array<ShopCounter> counters;

  public Shop(Queue<ShopCustomer> entranceQueue, Array<ShopCounter> counters) {
    this.entranceQueue = entranceQueue;
    this.counters = counters;
  }

  /**
   * Returns the next available counter
   * Returns null if all counters are unavailable
   *
   * @param counters The array of counters to perform the search
   */
  public ShopCounter getNextAvailableCounter() {
    for (int i = 0; i < counters.length(); i++) {
      ShopCounter counter = this.counters.get(i);
      if (counter.isAvailable()) {
        return counter;
      }
    }
    return null;
  }

  public ShopCounter getCounterWithShortestQueue() {
    return this.counters.min();
  }
  
  /**
   * Returns a string describing the current status of the queue
   */
  public String queueStatus() {
    return this.entranceQueue.toString();
  }

  /**
   * Adds a customer to the back of the queue.
   * Returns true if the enqueue was successful
   * (i.e. the queue was not full)
   */
  public boolean enqueue(ShopCustomer customer) {
    return this.entranceQueue.enq(customer);
  }

  /**
   * Returns and removes the first customer from the queue
   * Returns null if the queue was empty
   */
  public ShopCustomer dequeue() {
    return this.entranceQueue.deq();
  }

  /**
   * Returns true if the queue is full
   */
  public boolean queueFull() {
    return this.entranceQueue.isFull();
  }
}
