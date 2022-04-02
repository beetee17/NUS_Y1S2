public class Request {
    private final int rideDistance;
    private final int numPassengers;
    private final int requestTime;

    public Request(int rideDistance, int numPassengers, int requestTime) {
        this.rideDistance = rideDistance;
        this.numPassengers = numPassengers;
        this.requestTime = requestTime;
    }

    public int computeFare(
            int rate, int surcharge, int bookingFee, boolean isCostSplit) {
        int totalFare = this.rideDistance * rate + bookingFee;

        if (this.isPeakHour()) totalFare += surcharge;

        return isCostSplit
            ? totalFare / this.numPassengers
            : totalFare;
    }

    private boolean isPeakHour() {
        return requestTime >= 600 && requestTime <= 900;
    }
}

