/**
 * The Virus class is an abstraction of immutable Virus objects
 */
abstract class Virus {

    final double probabilityOfMutating;
    private final String name;

    /**
     * Creates a new Virus
     * @param name                  The name of the virus
     * @param probabilityOfMutating The probability that this virus will
     *                              mutate
     */
    Virus(String name, double probabilityOfMutating) {
        this.name = name;
        this.probabilityOfMutating = probabilityOfMutating;
    }

    /**
     * Causes this virus to spread
     * @param random The random value which determines how this virus
     *               spreads
     * @return       The resulting virus.
     */
    abstract Virus spread(double random);

    /**
     * Tests if this virus is of type {@code name}
     * @param name The name of the virus to test
     * @return     true if this virus has the same name as {@code name}, false
     *             otherwise
     */
    boolean test(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return String.format("%s with %.3f probability of mutating",
                this.name,
                this.probabilityOfMutating);
    }

}

