import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * The Disease Outbreak Response Management System (DORMS) is a system
 * for tracking and managing disease outbreaks in Singapore.
 */
public class Dorms {

    /**
     * Currently, DORMS is managing the SARS-CoV-2 outbreak that causes
     * COVID-19.
     */
    private static final String TARGET_VIRUS = "SARS-CoV-2";
    private static final String LOG_STRING = "%%s %s %%.3f";
    private static final String CHECK_IN = "visits %s at time";
    private static final String CHECK_OUT = "leaves %s at time";
    private static final String TESTS_POSITIVE = "tests positive for %s " +
        "at time";
    private static final String TESTS_NEGATIVE = "test negative for %s " +
        "at time";
    private static final String CONTACT = "met %s at time";

    private final TraceTogether traceTogether = new TraceTogether();
    private final SafeEntry safeEntry = new SafeEntry();
    private final PersonDatabase personDatabase = new PersonDatabase();
    private final LocationDatabase locationDatabase = new LocationDatabase();
    private final boolean verbose;
    /**
     * Initialises DORMS
     * @param verbose Initialises DORMS in verbose mode, i.e. log messages
     *                will be printed in verbose mode.
     */
    public Dorms(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Indicates to DORMS that someone has checked in to a location
     * @param personName    The person's name
     * @param isWearingMask true if that person is wearing a mask
     * @param locationName  The name of the location
     * @param time          The time the person checks in
     */
    public final void checkIn(String personName,
            boolean isWearingMask,
            String locationName,
            double time) {
        Person person = personDatabase.getPerson(personName, isWearingMask);
        if (person.onSHN(time)) {
            return;
        }
        Location location = locationDatabase.getLocation(locationName);
        safeEntry.checkIn(person, location, time);
        logCheckIn(person, location, time);
    }

    /**
     * Indicates to DORMS that someone has checked out of a location
     * @param personName    The person's name
     * @param locationName  The location's name
     * @param time          The time the person checked out
     */
    public final void checkOut(String personName,
            String locationName,
            double time) {
        Person person = personDatabase.getPerson(personName);
        if (person.onSHN(time)) {
            return;
        }
        Location location = locationDatabase.getLocation(locationName);
        safeEntry.checkOut(person, location);
        logCheckOut(person, location, time);
    }

    /**
     * Indicates to DORMS that two people had made contact
     * @param personA           The first person's name
     * @param isAWearingMask    true if the first person is wearing a mask
     * @param personB           The second person's name
     * @param isBWearingMask    true if the second person is wearing a mask
     * @param time              The time of contact
     */
    public final void contact(String personA,
            boolean isAWearingMask,
            String personB,
            boolean isBWearingMask,
            double time) {
        if (personA.equals(personB)) {
            return;
        }
        Person a = personDatabase.getPerson(personA, isAWearingMask);
        Person b = personDatabase.getPerson(personB, isBWearingMask);
        if (a.onSHN(time) || b.onSHN(time)) {
            return;
        }
        traceTogether.contact(a, b, time);
        logContact(a, b, time);
    }
   
    /**
     * Indicates to DORMS that someone has presented with symptoms of the
     * target virus
     * @param personName The person's name
     * @param time       The time this happened
     */
    public final void presentSymptoms(String personName,
            double time) {
        Person person = personDatabase.getPerson(personName);
        handleSickPerson(person, time);
    }

    void handleSickPerson(Person person, double time) {
        if (!person.test(TARGET_VIRUS)) {
            logNegativeTest(person, time);
            return;
        }
        logPositiveTest(person, time);
    }

    final List<? extends Contact> queryContacts(Person person, double time) {
        List<Contact> listOfContacts = new ArrayList<>();
        listOfContacts.addAll(safeEntry.retrieveContacts(person, time));
        listOfContacts.addAll(traceTogether.retrieveContacts(person, time));
        Collections.sort(listOfContacts, (x, y) -> Double.compare(x.timeOfContact(), y.timeOfContact()));
        return listOfContacts;
    }

    public final void printStatistics() {
        int numOfInfected = 0;
        List<? extends Person> listOfAllPeople = this.personDatabase.getAllPeople();
        for (Person p : listOfAllPeople) {
            if (p.test(TARGET_VIRUS)) {
                numOfInfected++;
            }
        }
        System.out.println("===== STATISTICS =====");
        System.out.println("Infected population: " + numOfInfected);
        System.out.println("Total Population: " + listOfAllPeople.size());
    }

    final void log(String s) {
        if (this.verbose) {
            System.out.println(s);
        }
    }

    private final void logCheckIn(Person p, Location l, double time) {
        log(String.format(buildLog(CHECK_IN), p, l, time));
    }

    private final void logCheckOut(Person p, Location l, double time) {
        log(String.format(buildLog(CHECK_OUT), p, l, time));
    }

    private final void logNegativeTest(Person person, double time) {
        log(String.format(buildLog(TESTS_NEGATIVE), 
                    person, TARGET_VIRUS, time));
    }

    private final void logPositiveTest(Person person, double time) {
        log(String.format(buildLog(TESTS_POSITIVE),
                    person, TARGET_VIRUS, time));
    }

    private final void logContact(Person a, Person b, double time) {
        log(String.format(buildLog(CONTACT), a, b, time));
    }

    private final String buildLog(String s) {
        return String.format(LOG_STRING, s);
    }

}

