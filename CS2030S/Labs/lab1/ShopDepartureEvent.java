/** 
 * @author Brandon (Group 12A)
 */
class ShopDepartureEvent extends Event {

  private ShopCustomer customer;

  /**
   * Constructor for a departure shop event.
   *
   * @param customer The customer associated with this
   *                   event.
   */
  public ShopDepartureEvent(ShopCustomer customer) {
    super(customer.getArrivalTime());
    this.customer = customer;
  }

  @Override 
  public String toString() {
    String str = String.format(": Customer %d departed",
                               this.customer.getID());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    // There is nothing to do for a departure event
    return new Event[] { };
  }
} 

