class Booking implements Comparable<Booking> {
  private Car car;
  private Service svc;
  private Request req;
  
  public Booking(Car car, Service svc, Request req) throws IllegalArgumentException {
    this.car = car;
    this.svc = svc;
    this.req = req;

    if (!this.car.isValidService(svc)) {
      throw new IllegalArgumentException(this.car + " does not provide the " + this.svc + " service.");
    }
  }

  @Override
  public int compareTo(Booking other) {
    Integer fare = this.svc.computeFare(req);
    Integer otherFare = other.svc.computeFare(other.req);

    if (fare.compareTo(otherFare) == 0) {
      Integer waitingTime = this.car.getMins();
      Integer otherTime = other.car.getMins();
      return waitingTime.compareTo(otherTime);
    }
    return fare.compareTo(otherFare);
  }
}
