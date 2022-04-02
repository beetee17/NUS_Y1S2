import java.util.List;

class Test5 {
  public static void main(String[] args) {
    CS2030STest i = new CS2030STest();
    i.expectCompile("map uses PECS",
        "Transformer<Object,Integer> f = x -> x.hashCode();\n"+
        "Trace<Number> t = Trace.<Number>of(23.6).map(f)", true);
    i.expectCompile("flatMap uses PECS",
        "Transformer<Object, SubTrace<Integer>> g = x -> SubTrace.of(x.hashCode());\n" +
        "Trace<Number> t = Trace.<Number>of(23.6).flatMap(g)", true);
  }
}
