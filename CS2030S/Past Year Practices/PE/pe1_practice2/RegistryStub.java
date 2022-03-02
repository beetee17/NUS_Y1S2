public class RegistryStub extends Registry {
  private String stub;

  public RegistryStub(String s) {
    super(s);
    this.stub = s;
  }
  public RegistryStub() {
    super("");
    this.stub = "";
  }
  
  @Override
  public String toString() {
    return String.format("[%s]", this.stub);
  }
}
