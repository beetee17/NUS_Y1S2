/** 
 * @author Brandon (Group 12A)
 */
class DepartureEvent extends Event {

  private ShopCustomer customer;

  /**
   * Constructor for a departure shop event.
   *
   * @param customer The customer associated with this
   *                   event.
   */
  public DepartureEvent(ShopCustomer customer) {
    super(customer.getArrivalTime());
    this.customer = customer;
  }

  @Override 
  public String toString() {
    return String.format("%s: %s departed", super.toString(), this.customer);
  }

  @Override
  public Event[] simulate() {
    // There is nothing to do for a departure event
    return new Event[] { };
  }
} 

