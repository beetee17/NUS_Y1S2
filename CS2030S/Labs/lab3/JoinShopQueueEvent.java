class JoinShopQueueEvent extends Event {
  private ShopCustomer customer;
  private Shop shop;

  public JoinShopQueueEvent(ShopCustomer customer, Shop shop) {
    super(customer.getArrivalTime());
    this.customer = customer;
    this.shop = shop;
  }

  @Override
  public Event[] simulate() {
    this.shop.enqueue(this.customer);
    return new Event[] { };
  }

  @Override 
  public String toString() {
    return String.format("%s: %s joined shop queue %s", 
        super.toString(), this.customer, this.shop.queueStatus());
  }
}

