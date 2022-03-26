/** 
 * @author Brandon (Group 12A)
 */
class ArrivalEvent extends Event {
  
  private Shop shop;
  private ShopCustomer customer;

  /**
   * Constructor for arrival shop event.
   * @param shop     The shop associated with this event occurs
   * @param customer The customer associated with this
   *                   event.
   */
  public ArrivalEvent(Shop shop, ShopCustomer customer) {
    super(customer.getArrivalTime());
    this.shop = shop;
    this.customer = customer;
  }

  @Override 
  public String toString() {
    return String.format("%s: %s arrived %s", 
        super.toString(), this.customer, this.shop.queueStatus());
  }

  @Override
  public Event[] simulate() {
    // The current event is an arrival event.  
    // Find the first available counter.
    ShopCounter nextAvailableCounter = this.shop.getNextAvailableCounter();

    if (nextAvailableCounter == null) {
      // If no such counter can be found, the customer
      // should try joining the counter with the shortest queue.
      ShopCounter shortestQueue = this.shop.getCounterWithShortestQueue();

      if (shortestQueue.queueFull()) {
        // Now the customer should try joining the entrance queue.
        if (this.shop.queueFull()) {
          // if the entrance queue is also full, the customer should depart
          return new Event[] { new DepartureEvent(this.customer) };
        } else {
          return new Event[] { new JoinShopQueueEvent(this.customer, this.shop) };
        }
      } else {
        // The customer should join `shortestQueue`
        return new Event[] { new JoinCounterQueueEvent(this.customer, shortestQueue) };
      }
    } else {
      // Else, the customer should go the the first 
      // available counter and get served.
      return new Event[] { 
        new ServiceBeginEvent(this.shop, this.customer, nextAvailableCounter)
      };
    }
  }
} 
