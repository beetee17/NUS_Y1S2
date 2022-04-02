import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * The Disease Outbreak Response Management System (DORMS) is a system
 * for tracking and managing disease outbreaks in Singapore.
 */
public class DormsWithShn extends Dorms {

  /**
   * Initialises DORMS
   * @param verbose Initialises DORMS in verbose mode, i.e. log messages
   *                will be printed in verbose mode.
   */
  public DormsWithShn(boolean verbose) {
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
        logSHN(target);
      }
    }
  }

  private final void logSHN(Person person) {
    super.log(String.format("%s has been served a SHN that ends at %.3f", person, person.getSHN()));
  }
}

