import java.util.List;

/**
 * The Contact class marks a human contact between two {@link Person people}
 * and transmits viruses between them
 */
class Contact {

    private final Person first;
    private final Person second;
    private final double time;

    /**
     * Creates a contact between two people and spreads viruses
     * between them
     * @param first  The first person in the contact
     * @param second The second person in the contact
     * @param time   The time this contact was made
     */
    Contact(Person first, Person second, double time) {
        this.first = first;
        this.second = second;
        this.time = time;
        this.transmit();
    }

    /**
     * Transmits the virus between the two people
     */
    void transmit() {
        double random = RandomNumberGenerator.nextDouble();
        List<Virus> toSecond = this.first.transmit(random);
        List<Virus> toFirst = this.second.transmit(random);
        this.first.infectWith(toFirst, random);
        this.second.infectWith(toSecond, random);
    }

    /**
     * Checks if this contact involves a particular person after a particular
     * time
     * @param p    The person to check
     * @param time The time the contact needs to happen after
     * @return     true if this contact meets the criteria, false otherwise
     */
    boolean involves(Person p, double time) {
        return this.time >= time &&
            (this.first.equals(p) || this.second.equals(p));
    }

    /**
     * Retrieves the other person.
     * @param p The person the other person contacted
     * @return  The person that {@code p} contacted
     */
    Person other(Person p) {
        return p.equals(this.first) ? this.second : this.first;
    }

    /**
     * Gets the time of contact
     * @return the time of contact
     */
    double timeOfContact() {
        return this.time;
    }

}

