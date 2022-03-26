/** 
 * @author Brandon (Group 12A)
 */
class ServiceEndEvent extends Event {

  private Shop shop;
  private ShopCustomer customer;
  private ShopCounter counter;

  /**
   * Constructor for service end shop event.
   *
   * @param shop     The shop associated with this event
   * @param customer The customer associated with this
   * @param counter  The counter associated with this event.
   */
  public ServiceEndEvent(Shop shop, ShopCustomer customer, 
      ShopCounter counter) {
    super(customer.getArrivalTime());
    this.shop = shop;
    this.customer = customer;
    this.counter = counter;
  }

  @Override 
  public String toString() {
    return String.format("%s: %s service done (by %s)", 
        super.toString(), this.customer, this.counter);
  }

  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    if (this.counter.queueEmpty()) {
      this.counter.setAvailabilityTo(true);
      // serve the next customer in the entrance queue
      ShopCustomer nextCustomer = this.shop.dequeue();
      if (nextCustomer == null) {  
        // No customers in the entranceQueue
        return new Event[] { 
          new DepartureEvent(this.customer)
        };
      } else {
        // The customer at the entrance queue should be served
        nextCustomer.setArrivalTimeTo(this.customer.getArrivalTime());
        return new Event[] { 
          new DepartureEvent(this.customer),
          new ServiceBeginEvent(this.shop, nextCustomer, this.counter)
        };
      }
    } else {
      // Serve the next customer in the counter's queue
      ShopCustomer nextCustomer = this.counter.dequeue();
      nextCustomer.setArrivalTimeTo(this.customer.getArrivalTime());
      
      // And enqueue the next customer at the entrance
      ShopCustomer customerAtEntrance = this.shop.dequeue();
      if (customerAtEntrance != null) {
        customerAtEntrance.setArrivalTimeTo(this.customer.getArrivalTime());
        return new Event[] {
          new DepartureEvent(this.customer),
          new ServiceBeginEvent(this.shop, nextCustomer, this.counter),
          new JoinCounterQueueEvent(customerAtEntrance, this.counter)
        };
      } else {
        // No customer at the entrance to enqueue
        return new Event[] { 
          new DepartureEvent(this.customer),
          new ServiceBeginEvent(this.shop, nextCustomer, this.counter)
        };
      }
    }
  }
} 
