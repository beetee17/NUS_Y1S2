class SARS_CoV_2 extends Virus {
  public SARS_CoV_2(double mutationProbability) {
    super("SARS-CoV-2", mutationProbability);
  }

  @Override
  public Virus spread(double random) {
    double p = this.getMP();
    if (random > p) 
      return new SARS_CoV_2(0.9*p);
    else 
      return new BetaCoronavirus();
  }
}
