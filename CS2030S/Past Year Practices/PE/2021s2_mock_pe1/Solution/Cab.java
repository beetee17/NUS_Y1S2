public class Cab extends Car {

    public Cab(String licensePlate, int minutesAway) {
        super(licensePlate, minutesAway);
    }

    @Override
    public boolean isValidService(Service service) {
        if (service instanceof ShareARide) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Cab " + super.toString();
    }
}

