class AlphaCoronavirus extends Virus {
  public AlphaCoronavirus(double mutationProbability) {
    super("Alpha Coronavirus", mutationProbability);
  }

  @Override
  public Virus spread(double random) {
    double p = this.getMP();
    if (random > p) 
      return new AlphaCoronavirus(0.9*p);
    else 
      return new SARS_CoV_2(p);
  }
  

}
