import java.util.LinkedList;
import java.util.List;

/**
 * The Location class represents a location
 */
class Location {

    private final String name;
    private final List<Person> listOfPersonsInLocation = new LinkedList<>();

    /**
     * Creates a location
     * @param name the name of the location
     */
    Location(String name) {
        this.name = name;
    }

    /**
     * Gets all the occupants in the location
     * @return all the occupants in the location
     */
    List<? extends Person> getOccupants() {
        return List.copyOf(this.listOfPersonsInLocation);
    }

    /**
     * Accepts a person into this location
     * @param person The person to accept
     */
    void accept(Person person) {
        this.listOfPersonsInLocation.add(person);
    }

    /**
     * Removes a person from this location
     * @param person The person to remove
     */
    void remove(Person person) {
        this.listOfPersonsInLocation.remove(person);
    }

    @Override
    public String toString() {
        return this.name;
    }

}

