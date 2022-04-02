abstract class Virus {
  private double mutationProbability;;
  private String name;

  public Virus(String name, double mutationProbability) {
    this.name = name;
    this.mutationProbability = mutationProbability;
  }
  public String getName() {
    return this.name;
  }
  public double getMP() {
    return this.mutationProbability;
  }

  abstract Virus spread(double random);

  public boolean test(String name) {
    return this.name == name;
  }
  
  @Override
  public String toString() {
    return String.format("%s with %.3f probability of mutating", this.getName(), this.getMP());
  }
}
