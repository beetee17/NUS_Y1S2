import java.util.List;
import java.util.ArrayList;

/**
 * <p>SafeEntry is a national digital check-in system that logs individuals
 * visiting hotspots, workplaces of essential services, as well as selected
 * public venues to prevent and control the transmission of disease through
 * activities such as contact tracing and identification of disease clusters.
 * Source: safeentry.gov.sg
 *
 * <p>This class is not the real safeentry platform
 */
class SafeEntry {

    private final List<Contact> listOfContacts = new ArrayList<>();

    /**
     * Initialises SafeEntry
     */
    SafeEntry() {}

    /**
     * Checks a person in to the location
     * @param person   The person checking in
     * @param location The location the person is checking into
     * @param time     The time of check in
     */
    void checkIn(Person person, Location location, double time) {
        List<? extends Person> personsInLocation = location.getOccupants();
        for (Person p : personsInLocation) {
            listOfContacts.add(new Contact(person, p, time));
        }
        location.accept(person);
    }

    /**
     * Checks a person out of a location
     * @param person    The person checking out
     * @param location  The location the person is checking out of
     */
    void checkOut(Person person, Location location) {
        location.remove(person);
    }

    /**
     * Queries contacts involving some person up to a certain point in
     * history as detailed in {@link SimulationParameters}
     * @param p     The person to query
     * @param time  The time of querying
     * @return      The list of contacts the person has made, some period
     *              before the query was made
     */
    List<? extends Contact> retrieveContacts(Person p, double time) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : listOfContacts) {
            if (c.involves(p, time - SimulationParameters.TRACING_PERIOD)) {
                result.add(c);
            }
        }
        return result;
    }

}
