/**
 * The AlphaCoronavirus is a type of coronavirus that
 * infects humans.
 */
class AlphaCoronavirus extends Virus {

    /**
     * Creates an AlphaCoronavirus
     * @param probabilityOfMutating The probability that this alpha
     *                              coronavirus will mutate
     */
    AlphaCoronavirus(double probabilityOfMutating) {
        super("Alpha Coronavirus", probabilityOfMutating);
    }

    @Override
    Virus spread(double random) {
        if (random <= this.probabilityOfMutating) {
            return new SARS_CoV_2(this.probabilityOfMutating);
        }
        return new AlphaCoronavirus(this.probabilityOfMutating *
                SimulationParameters.VIRUS_MUTATION_PROBABILITY_REDUCTION);
    }

}

