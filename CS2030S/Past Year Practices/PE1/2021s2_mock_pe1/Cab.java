class Cab extends Car {

  public Cab(String license, int minutes){ 
    super("Cab", license, minutes);
  }

  @Override
  public boolean isValidService(Service svc) {
    return !(svc instanceof ShareARide);
  }
}
