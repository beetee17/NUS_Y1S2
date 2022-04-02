public class PrivateCar extends Car {

    public PrivateCar(String licensePlate, int minutesAway) {
        super(licensePlate, minutesAway);
    }

    @Override
    public boolean isValidService(Service service) {
        if (service instanceof TakeACab) return false;

        return true;
    }

    @Override
    public String toString() {
        return "PrivateCar " + super.toString();
    }
}

