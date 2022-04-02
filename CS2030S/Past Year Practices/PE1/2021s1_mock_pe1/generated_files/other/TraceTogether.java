import java.util.List;
import java.util.ArrayList;

/**
 * <p>The TraceTogether Programme is a programme to enhance Singapore’s 
 * contact tracing efforts, in the fight against COVID-19. It comprises the
 * TraceTogether App and TraceTogether Token. The App was released on 20
 * March, while the Token was rolled out on 28 June. Both the App and Token
 * work by using Bluetooth signals to record other nearby TraceTogether
 * devices.
 * Source: support.tracetogether.gov.sg
 * <p>This class is meant to act as a simulated tracetogether
 * system, and is not the real TraceTogether platform.
 */
class TraceTogether {

    private final List<Contact> listOfContacts = new ArrayList<>();

    /**
     * Initialises TraceTogether
     */
    TraceTogether() {}

    /**
     * Indicate that two people have made contact
     * @param personA The first person in the contact
     * @param personB The second person in the contact
     * @param time    The time of contact
     */
    void contact(Person personA, Person personB, double time) {
        listOfContacts.add(new Contact(personA, personB, time));
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

