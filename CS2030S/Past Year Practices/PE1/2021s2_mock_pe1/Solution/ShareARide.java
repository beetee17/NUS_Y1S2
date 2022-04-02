public class ShareARide implements Service {
    private static final int RATE = 50;
    private static final int SURCHARGE = 500;
    private static final int BOOKING_FEE = 0;
    private static final boolean IS_COST_SPLIT = true;

    public ShareARide() { }

    @Override
    public int computeFare(Request request) {
        return request.computeFare(RATE, SURCHARGE, BOOKING_FEE, IS_COST_SPLIT);
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}

