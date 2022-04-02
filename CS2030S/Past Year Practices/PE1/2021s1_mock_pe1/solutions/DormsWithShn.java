/**
 * DormsWithShn is the same as DORMS, but with SHN implemented.
 */
public class DormsWithShn extends Dorms {

    /**
     * Initialises DORMS with SHN policy
     * @param verbose Whether the program is run in verbose mode
     */
    DormsWithShn(boolean verbose) {
        super(verbose);
    }

    @Override
    void handleSickPerson(Person person, double time) {
        super.handleSickPerson(person, time);
        if (!person.test(SimulationParameters.TARGET_VIRUS)) {
            return;
        }
        person.serveSHN(time + (SimulationParameters.SHN_DURATION * 2));
        for (Contact c : this.queryContacts(person, time)) {
            Person target = c.other(person);
            double shnEndTime = c.timeOfContact() +
                SimulationParameters.SHN_DURATION;
            if (target.serveSHN(shnEndTime)) {
                logServeSHN(target, shnEndTime);
            }
        }
    }

    private void logServeSHN(Person person, double shnEndTime) {
        log(String.format("%s has been served a SHN that ends at %.3f", 
                    person, shnEndTime));
    }

}
