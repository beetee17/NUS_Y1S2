abstract class Service {
  private String serviceType;

  public Service(String type) {
    this.serviceType = type;
  }

  abstract public int computeFare(Request req); 

  @Override
  public String toString() {
    return serviceType;
  }
}
