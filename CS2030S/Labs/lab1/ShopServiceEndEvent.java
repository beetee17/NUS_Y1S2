/** 
 * @author Brandon (Group 12A)
 */
class ShopServiceEndEvent extends Event {

  private ShopCustomer customer;
  private ShopCounter counter;

  /**
   * Constructor for service end shop event.
   *
   * @param customer The customer associated with this
   * @param counter The counter associated with this event.
   */
  public ShopServiceEndEvent(ShopCustomer customer, 
      ShopCounter counter) {
    super(customer.getArrivalTime());
    this.customer = customer;
    this.counter = counter;
  }

  @Override 
  public String toString() {
    String str = String.format(": Customer %d service done (by Counter %d)", 
        this.customer.getID(), this.counter.getID());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.counter.setAvailabilityTo(true);
    return new Event[] { 
      new ShopDepartureEvent(this.customer),
    };
  }
} 
