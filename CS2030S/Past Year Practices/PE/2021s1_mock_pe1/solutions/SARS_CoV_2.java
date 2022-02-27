/**
 * The SARS-CoV-2 virus is a coronavirus that causes COVID-19
 */
class SARS_CoV_2 extends Virus {

    /**
     * Creates the virus
     * @param probabilityOfMutating the viruses probability of
     *                              mutating
     */
    SARS_CoV_2(double probabilityOfMutating) {
        super("SARS-CoV-2", probabilityOfMutating);
    }

    @Override
    Virus spread(double random) {
        if (random <= probabilityOfMutating) {
            return new BetaCoronavirus();            
        }
        return new SARS_CoV_2(probabilityOfMutating *
                SimulationParameters.VIRUS_MUTATION_PROBABILITY_REDUCTION);
    }

}

