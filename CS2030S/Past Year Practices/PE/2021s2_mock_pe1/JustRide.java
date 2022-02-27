class JustRide extends Service {
  public JustRide() {
    super("JustRide");
  }

  private static final int SURCHARGE = 500;

  private boolean hasSurcharge(int time) {
    return time >= 600 && time <= 900;
  }

  @Override
  public int computeFare(Request req) {
    if (hasSurcharge(req.getTime()))
        return 22 * req.getDistance() + SURCHARGE;
    return 22 * req.getDistance();
  }
}
