abstract class Car {
  private final String carType;
  private final String licensePlate;
  private int minutesTillAvailable;

  public Car(String type, String license, int minutes){ 
    this.carType = type;
    this.licensePlate = license;
    this.minutesTillAvailable = minutes;
  }

  abstract public boolean isValidService(Service svc);

  public int getMins() {
    return this.minutesTillAvailable;
  }

  @Override
  public String toString() {
    if (this.minutesTillAvailable == 1) 
      return String.format("%s %s (%d min away)", this.carType, this.licensePlate, this.minutesTillAvailable);
    
    return String.format("%s %s (%d mins away)", this.carType, this.licensePlate, this.minutesTillAvailable);
  }
}
