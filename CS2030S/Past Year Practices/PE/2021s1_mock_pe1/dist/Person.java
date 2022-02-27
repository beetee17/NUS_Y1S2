import java.util.*;

class Person {
  private ArrayList<Virus> viruses = new ArrayList<Virus>();
  private String name;
  private double shnEndTime = Integer.MIN_VALUE;

  public Person(String name) {
    this.name = name;
  }
  public boolean serveSHN(double shnEndTime) {
    if (shnEndTime > this.shnEndTime) {
      this.shnEndTime = shnEndTime;
      return true;
    }
    return false;
  }
  public double getSHN() {
    return this.shnEndTime;
  }

  public boolean onSHN(double currentTime) {
    return currentTime < shnEndTime;
  }

  public List<Virus> transmit(double random) {
    List<Virus> newViruses = new ArrayList<Virus>();
    for (Virus virus : this.viruses) {
      newViruses.add(virus.spread(random));
    }
    return newViruses;
  }

  public void infectWith(List<? extends Virus> listOfViruses, double random) {
    for (Virus virus : listOfViruses) {
      this.viruses.add(virus);
    }
  }

  public boolean test(String virusName) {
    for (Virus virus : this.viruses) {
      if (virus.test(virusName)) 
        return true;
    }
    return false;
  }

  public List<Virus> getViruses() {
    return this.viruses;
  }
  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return String.format("%s", this.name);
  }
}
