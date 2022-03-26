/** 
 * @author Brandon (Group 12A)
 */
class ShopCustomer {
  private static int numCustomers = 0;
  private int id;
  private double arrivalTime;
  private double serviceTime;

  /**
   * Constructor for ShopCustomer.
   *
   * @param id A unique identifier for the customer object
   * @param arrivalTime Time when this customer arrives at the event
   * @param serviceTime How long it takes to service this customer
   */
  public ShopCustomer(double arrivalTime, double serviceTime) {
    this.id = numCustomers++;
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
  }

  public int getID() {
    return this.id;
  }

  public double getArrivalTime() {
    return this.arrivalTime;
  }

  public double getServiceTime() {
    return this.serviceTime;
  }

  // If history of customer arrival times is important,
  // we can consider using an array to store the timestamps
  public void setArrivalTimeTo(double newValue) {
    this.arrivalTime = newValue;
  }

  @Override
  public String toString() {
    return String.format("C%d", this.id);
  }
}
