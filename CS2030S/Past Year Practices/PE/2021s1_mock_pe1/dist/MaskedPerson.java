import java.util.*;
class MaskedPerson extends Person {
  public MaskedPerson(String name) {
    super(name);
  }

  @Override
  public List<Virus> transmit(double random) {
    if (random > SimulationParameters.MASK_EFFECTIVENESS) {
      return super.transmit(random);
    }
    return new ArrayList<Virus>(); 
  }


  @Override
  public void infectWith(List<? extends Virus> listOfViruses, double random) {
    if (random > SimulationParameters.MASK_EFFECTIVENESS) {
      super.infectWith(listOfViruses, random);
    }
  }

  @Override
  public String toString() {
    return String.format("%s (masked)", super.toString());
  }
}
