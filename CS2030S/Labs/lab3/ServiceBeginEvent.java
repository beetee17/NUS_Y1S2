/** 
 * @author Brandon (Group 12A)
 */
class ServiceBeginEvent extends Event {

  private Shop shop;
  private ShopCustomer customer;
  private ShopCounter counter;

  /**
   * Constructor for service begin shop event.
   *
   * @param shop     The shop associated with this event
   * @param customer The customer associated with this
   *                   event.
   * @param counter  The counter associated with this event
   */
  public ServiceBeginEvent(Shop shop, ShopCustomer customer, 
      ShopCounter counter) {
    super(customer.getArrivalTime());
    this.shop = shop;
    this.customer = customer;
    this.counter = counter;
  }

  @Override 
  public String toString() {
    return String.format("%s: %s service begin (by %s)", 
        super.toString(), this.customer, this.counter);
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
      new ServiceEndEvent(this.shop, this.customer, this.counter)
    };
  }
} 
