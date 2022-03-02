public class Instructor {
  private String name;
  
  public Instructor(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;

    if (obj instanceof Instructor) {
      Instructor other = (Instructor) obj;
      return this.name.equals(other.name);
    }

    return false;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
