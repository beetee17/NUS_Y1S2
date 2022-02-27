class TakeACab extends Service{

  public TakeACab() {
    super("TakeACab");
  }

  private static final int BOOKING_FEE = 200;
 
  @Override
  public int computeFare(Request req) {
    return BOOKING_FEE + 33 * req.getDistance();
  }
}
