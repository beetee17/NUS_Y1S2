/** 
 * @author Brandon (Group 12A)
 */
class ShopArrivalEvent extends Event {
  
  private ShopCustomer customer;
  private double serviceTime;
  private ShopCounter[] counters;

  /**
   * Constructor for arrival shop event.
   *
   * @param customer The customer associated with this
   *                   event.
   * @param counters The counters associated with this event
   */
  public ShopArrivalEvent(ShopCustomer customer, 
      ShopCounter[] counters) {
    super(customer.getArrivalTime());
    this.customer = customer;
    this.counters = counters;
  }

  @Override 
  public String toString() {
    String str = String.format(": Customer %d arrives", 
                               this.customer.getID());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    // The current event is an arrival event.  
    // Find the first available counter.
    ShopCounter nextAvailableCounter = ShopCounter.getNextAvailable(counters);

    if (nextAvailableCounter == null) {
      // If no such counter can be found, the customer
      // should depart.
      return new Event[] { 
        new ShopDepartureEvent(this.customer)
      };
    } else {
      // Else, the customer should go the the first 
      // available counter and get served.
      return new Event[] { 
        new ShopServiceBeginEvent(this.customer, 
            nextAvailableCounter)
      };
    }
  }
} 
