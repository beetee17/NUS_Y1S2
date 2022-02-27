class Contact {
  private Person first;
  private Person second;
  private double time;

  public Contact(Person first, Person second, double time) {
    this.first = first;
    this.second = second;
    this.time = time;

    // transmit between both Persons
    double random = RandomNumberGenerator.nextDouble();

    first.infectWith(second.transmit(random), random);
    second.infectWith(first.transmit(random), random);
  }

  public Person getFirst() {
    return this.first;
  }

  public Person getSecond() {
    return this.second;
  }
  
  public boolean involves(Person p, double time) {
    return this.time >= time && (p.equals(this.first) || p.equals(this.second));
  }

  public Person other(Person p) {
    if (p.equals(this.first))
      return this.second;
    return this.first;
  }

  public double timeOfContact() {
    return this.time;
  }

}
