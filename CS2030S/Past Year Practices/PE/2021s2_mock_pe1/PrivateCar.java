class PrivateCar extends Car {

  public PrivateCar(String license, int minutes){ 
    super("PrivateCar", license, minutes);
  }

  @Override
  public boolean isValidService(Service svc) {
    return !(svc instanceof TakeACab);
  }
}
