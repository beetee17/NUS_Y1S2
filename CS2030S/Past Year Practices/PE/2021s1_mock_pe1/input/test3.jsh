RandomNumberGenerator.reset()
RandomNumberGenerator.nextDouble()
RandomNumberGenerator.reset()
Person daekoon = new Person("Daekoon")
Person junda = new Person("Jun Da")
Person ziyang = new Person("Zi Yang")
daekoon.infectWith(List.of(new AlphaCoronavirus(0.99)), 1);
Contact c = new Contact(daekoon, junda, 1);
daekoon.transmit(1)
daekoon.transmit(0)
junda.transmit(1)
junda.transmit(0)
c.involves(daekoon, 1)
c.involves(daekoon, 1.1)
c.involves(ziyang, 0)
c.other(daekoon)
c.other(junda)
c.timeOfContact()
RandomNumberGenerator.reset()
Person junda = new Person("Jun Da")
Person ziyang = new Person("Zi Yang")
junda.infectWith(List.of(new SARS_CoV_2(0.5)), 1);
Contact d = new Contact(ziyang, junda, 1);
ziyang.transmit(1)
ziyang.transmit(0)
junda.transmit(1)
junda.transmit(0)
d.timeOfContact()
/exit
