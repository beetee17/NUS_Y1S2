public abstract class Car implements Comparable<Car> {
    private final String licensePlate;
    private final int minutesAway;

    public Car(String licensePlate, int minutesAway) {
        this.licensePlate = licensePlate;
        this.minutesAway = minutesAway;
    }

    public abstract boolean isValidService(Service service);

    private String pluralMinutes() {
        return this.minutesAway == 1 ? "min" : "mins";
    }

    @Override
    public int compareTo(Car otherCar) {
        return this.minutesAway - otherCar.minutesAway;
    }

    @Override
    public String toString() {
        return String.format(
            "%s (%d %s away)",
            this.licensePlate,
            this.minutesAway,
            this.pluralMinutes()
        );
    }
}

