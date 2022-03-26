/** 
 * @author Brandon (Group 12A)
 */
class ShopCustomer {

  private int id;
  private double arrivalTime;
  private double serviceTime;

  /**
   * Constructor for ShopCustomer.
   *
   * @param id A unique identifier for the customer object
   * @param arrivalTime Time where this customer arrives at the event
   * @param serviceTime How long it takes to service this customer
   */
  public ShopCustomer(int id, double arrivalTime, double serviceTime) {
    this.id = id;
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
}
