import java.util.ArrayList;
import java.util.List;

/**
 * A MaskedPerson is a {@link Person} wearing a mask
 */
class MaskedPerson extends Person {

    /**
     * Creates a MaskedPerson
     * @param name The name of the person
     */
    MaskedPerson(String name) {
        super(name);
    }

    @Override
    List<Virus> transmit(double random) {
        List<Virus> toSpread = new ArrayList<>();
        if (random <= SimulationParameters.MASK_EFFECTIVENESS) {
            return toSpread;
        }
        return super.transmit(random);
    }

    @Override
    void infectWith(List<? extends Virus> listOfViruses, double random) {
        if (random <= SimulationParameters.MASK_EFFECTIVENESS) {
            return;
        }
        super.infectWith(listOfViruses, random);
    }

    @Override
    public String toString() {
        return super.toString() + " (masked)";
    }
}

