public class JustRide implements Service {
    private static final int RATE = 22;
    private static final int SURCHARGE = 500;
    private static final int BOOKING_FEE = 0;
    private static final boolean IS_COST_SPLIT = false;

    public JustRide() { }

    @Override
    public int computeFare(Request request) {
        return request.computeFare(RATE, SURCHARGE, BOOKING_FEE, IS_COST_SPLIT);
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}

