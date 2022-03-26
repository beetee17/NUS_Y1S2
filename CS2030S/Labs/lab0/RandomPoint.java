import java.util.Random;

/**
 * CS2030S Lab 0: RandomPoint.java
 * Semester 2, 2021/22
 *
 * <p> RandomPoint is a subclass of Point 
 * that represents a randomly generated point
 *
 * @author: Brandon (Group 12A) 
 */
class RandomPoint extends Point {

  private static int seed = 1;
  private static Random rng = new Random(seed); 
 
  /**
   * Constructor for a random point.  
   * 
   * @param minX Minimum possible x value 
   * @param maxX Maximum possible x value 
   * @param minY Minimum possible y value 
   * @param maxY Maximum possible y value 
   */
  public RandomPoint(double minX, double maxX, double minY, double maxY) {
    super(getRandDoubleWithinRange(minX, maxX),
          getRandDoubleWithinRange(minY, maxY));
  }
  
  /**
   * Returns a random double between a specified range with the current Random instance.
   *
   * @param min Lower bound for random value
   * @param max Upper bound for random value
   * @return double that in [min, max]
   */
  private static double getRandDoubleWithinRange(double min, double max) {
    return min + (RandomPoint.rng.nextDouble() * (max - min)); 
  }

  public static void setSeed(int seed) {
    RandomPoint.seed = seed;
    RandomPoint.rng = new Random(seed);
  }
}
