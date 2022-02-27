import java.util.*;

class Location {
  private String name;
  private List<Person> occupants = new ArrayList<Person>();
  
  public Location(String name) {
    this.name = name;
  }

  public List<Person> getOccupants() {
    return this.occupants;
  }
  
  public void accept(Person person) {
    this.occupants.add(person);
  }

  public void remove(Person person) {
    for (int i = 0; i < this.occupants.size(); i++) {
      Person p = this.occupants.get(i);
      if (p.equals(person)) 
        this.occupants.remove(i);
    }
  }

  @Override
  public String toString() {
    return this.name;
  }

  public String getName() {
    return this.name;
  }

}
