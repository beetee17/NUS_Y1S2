Person illio = new Person("Illio");
Person phillmont = new Person("Phillmont")
Person nicole = new Person("Nicole")
Person shuming = new Person("Shuming")
illio.infectWith(List.of(new AlphaCoronavirus(1)), 0);
phillmont.infectWith(List.of(new AlphaCoronavirus(0.5)), 0);
nicole.infectWith(List.of(new SARS_CoV_2(0.5), new AlphaCoronavirus(0.4)), 0);
shuming.infectWith(List.of(new AlphaCoronavirus(0.1)), 0);
Arrays.toString(illio.transmit(1).toArray())
Arrays.toString(illio.transmit(1).toArray())
Arrays.toString(phillmont.transmit(0.51).toArray())
Arrays.toString(phillmont.transmit(0.5).toArray())
Arrays.toString(nicole.transmit(0.51).toArray())
Arrays.toString(nicole.transmit(0.5).toArray())
Arrays.toString(nicole.transmit(0.4).toArray())
List<? extends Virus> l = illio.transmit(1)
shuming.infectWith(l, 1);
Arrays.toString(shuming.transmit(1).toArray())
Arrays.toString(shuming.transmit(0).toArray())
illio.test("Alpha Coronavirus")
illio.test("SARS-CoV-2")
illio.test("Beta Coronavirus")
phillmont.test("Alpha Coronavirus")
nicole.test("SARS-CoV-2")
shuming.test("Beta Coronavirus")
phillmont.getName()
/exit
