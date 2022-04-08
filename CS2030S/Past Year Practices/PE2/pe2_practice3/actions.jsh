Function<List<Item>, List<Item>> takeSword = x -> {
  List<Item> newItems = new ArrayList<>();
  boolean swordExists = false;
  for (Item i : x) {
    if (i instanceof Sword) {
      swordExists = true;
      if (i.isTaken()) {
        System.out.println("--> You already have sword.");
        newItems.add(i);
      } else {
        newItems.add(i.take());
        System.out.println("--> You have taken sword.");
      }
    } else {
      newItems.add(i);
    }
  }
    
  if (!swordExists) {
    System.out.println("--> There is no sword.");
  }
  
  return newItems;
}

Function<List<Item>, List<Item>> killTroll = x -> {
  List<Item> newItems = new ArrayList<>();
  boolean haveSword = false;
  for (Item i : x) {
    if (i instanceof Sword && i.isTaken()) {
      haveSword = true;
    }
  }

  if (!haveSword) {
    System.out.println("--> You have no sword.");
    return x;
  }

  boolean trollExists = false;
  for (Item i : x) {
    if (i instanceof Troll) {
      trollExists = true;
      System.out.println("--> Troll is killed.");
    } else {
      newItems.add(i);
    }
  }

  if (!trollExists) {
    System.out.println("--> There is no troll.");
  }
  return newItems;
}

Function<List<Item>, List<Item>> dropSword = x -> {
  List<Item> newItems = new ArrayList<>();
  boolean swordExists = false;
  for (Item i : x) {
    if (i instanceof Sword) {
      swordExists = true;
      if (i.isTaken()) {
        System.out.println("--> You have dropped sword.");
        newItems.add(i.drop());
      } else {
        newItems.add(i);
      }
    } else {
      newItems.add(i);
    }
  }
    
  if (!swordExists) {
    System.out.println("--> There is no sword.");
  }
  
  return newItems;
}
