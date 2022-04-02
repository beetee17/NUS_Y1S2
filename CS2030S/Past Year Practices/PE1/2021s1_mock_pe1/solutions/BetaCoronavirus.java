/**
 * The BetaCoronavirus is a type of coronavirus that infects humans 
 */
class BetaCoronavirus extends Virus {

    /**
     * Creates a BetaCoronavirus
     */
    BetaCoronavirus() {
        super("Beta Coronavirus", 0);
    }

    @Override
    Virus spread(double random) {
        return new BetaCoronavirus();
    }

}
