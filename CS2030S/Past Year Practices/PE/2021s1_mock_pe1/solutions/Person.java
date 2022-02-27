import java.util.ArrayList;
import java.util.List;

/**
 * Represents the regular human
 */
class Person {

    private final String name;
    private final List<Virus> listOfViruses = new ArrayList<>();
    private double shnEndTime = -1;

    /**
     * Creates a person
     * @param name The name of the person
     */
    Person(String name) {
        this.name = name;
    }

    /**
     * Transmits some viruses out of this person
     * @param random The random value to generate the outcome
     *               of disease transmission
     * @return       the viruses to transmit
     */
    List<Virus> transmit(double random) {
        List<Virus> toSpread = new ArrayList<>();
        for (Virus v : this.listOfViruses) {
            toSpread.add(v.spread(random));
        }
        return toSpread;
    }

    /**
     * Infects this person with a list of viruses
     * @param listOfViruses The list of viruses to infect this person with
     * @param random        the random value to generate the outcome of
     *                      infection
     */
    void infectWith(List<? extends Virus> listOfViruses, double random) {
        for (Virus v : listOfViruses) {
            this.listOfViruses.add(v);
        }
    }

    /**
     * Tests this human for a virus
     * @param virusName The name of the virus
     * @return true if this person is infected with the target virus
     */
    boolean test(String virusName) {
        for (Virus v : listOfViruses) {
            if (v.test(virusName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets the name of the Person
     * @return the name of this person
     */
    String getName() {
        return this.name;
    }

    /**
     * Serves this person with a Stay Home Notice
     * @param shnEndTime the end time of the stay home notice
     * @return if the person has been served a stay home notice 
     */
    boolean serveSHN(double shnEndTime) {
        if (shnEndTime <= this.shnEndTime) {
            return false;
        }
        this.shnEndTime = shnEndTime;
        return true;
    }

    /**
     * Checks if this person is on Stay Home Notice
     * @param currentTime the time to check
     * @return true if at {@code currentTime}, the person is still
     *          on Stay Home Notice
     */
    boolean onSHN(double currentTime) {
        return currentTime < this.shnEndTime;
    }
}

