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
    this.counter.setAvailabilityTo(true);

    ShopCustomer nextCustomer = this.shop.dequeue();
    if (nextCustomer == null) {  
      return new Event[] { 
        new DepartureEvent(this.customer)
      };
    } else {
      nextCustomer.setArrivalTimeTo(this.customer.getArrivalTime());
      return new Event[] { 
        new DepartureEvent(this.customer),
        new ServiceBeginEvent(this.shop, nextCustomer, this.counter)
      };
    }
  }
} 
