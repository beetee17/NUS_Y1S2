class Request {
  private int rideDistance;
  private int numPassengers;
  private int time;

  public Request(int rideDistance, int numPassengers, int time) {
    this.rideDistance = rideDistance;
    this.numPassengers = numPassengers;
    this.time = time;
  }

  public int getDistance() {
    return this.rideDistance;
  }

  public int getPassengers() {
    return this.numPassengers;
  }

  public int getTime() {
    return this.time;
  }
}
