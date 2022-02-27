class ShareARide extends Service {
  public ShareARide() {
    super("ShareARide");
  }

  private static final int SURCHARGE = 500;
  private static final int BASE_RATE = 50;

  private boolean hasSurcharge(int time) {
    return time >= 600 && time <= 900;
  }

  @Override
  public int computeFare(Request req) {
    int base = BASE_RATE * req.getDistance();
    int paid = Math.floorDiv(base, req.getPassengers());
    if (hasSurcharge(req.getTime()))
      return paid + SURCHARGE;
    return paid;
  }
}
