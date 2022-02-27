public class TakeACab implements Service {
    private static final int RATE = 33;
    private static final int SURCHARGE = 0;
    private static final int BOOKING_FEE = 200;
    private static final boolean IS_COST_SPLIT = false;

    public TakeACab() { }

    @Override
    public int computeFare(Request request) {
        return request.computeFare(RATE, SURCHARGE, BOOKING_FEE, IS_COST_SPLIT);
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}

