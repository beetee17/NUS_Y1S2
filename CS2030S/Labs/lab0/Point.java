/**
 * CS2030S Lab 0: Point.java
 * Semester 2, 2021/22
 *
 * <p>The Point class encapsulates a point on a 2D plane.
 *
 * @author Brandon (Group 12A)
 */
class Point {

  private double x;
  private double y;

  /**
   * Constructor for a point.
   *
   * @param x The x coordinate of the point
   * @param y The y coordinate of the point
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
 
  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }
}

