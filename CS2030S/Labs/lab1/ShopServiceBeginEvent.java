/** 
 * @author Brandon (Group 12A)
 */
class ShopServiceBeginEvent extends Event {

  private ShopCustomer customer;
  private ShopCounter counter;

  /**
   * Constructor for service begin shop event.
   *
   * @param customer The customer associated with this
   *                   event.
   * @param counter The counter associated with this event
   */
  public ShopServiceBeginEvent(ShopCustomer customer, 
      ShopCounter counter) {
    super(customer.getArrivalTime());
    this.customer = customer;
    this.counter = counter;
  }

  @Override 
  public String toString() {
    String str = String.format(": Customer %d service begin (by Counter %d)", 
        this.customer.getID(), this.counter.getID()); 
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    // The current event is a service-begin event.
    // Mark the counter is unavailable, then schedule
    // a service-end event at the current time + service time.
    this.counter.setAvailabilityTo(false);
    double endTime = this.getTime() + this.customer.getServiceTime();
    this.customer.setArrivalTimeTo(endTime);
    return new Event[] { 
      new ShopServiceEndEvent(this.customer, 
          this.counter)
    };
  }
} 
