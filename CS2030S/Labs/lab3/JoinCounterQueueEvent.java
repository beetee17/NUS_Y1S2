class JoinCounterQueueEvent extends Event {
  private ShopCustomer customer;
  private ShopCounter counter;

  public JoinCounterQueueEvent(ShopCustomer customer, ShopCounter counter) {
    super(customer.getArrivalTime());
    this.customer = customer;
    this.counter = counter;
  }

  @Override
  public Event[] simulate() {
    this.counter.enqueue(this.customer);
    return new Event[] { };
  }

  @Override 
  public String toString() {
    return String.format("%s: %s joined counter queue (at %s)", 
        super.toString(), this.customer, this.counter); 
  }
}
