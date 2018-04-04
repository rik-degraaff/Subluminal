package tech.subluminal.shared.records.game;

public class Coordinates {
  private double x;
  private double y;

  public Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x coordinate.
   * @return the x coordinate.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets the x coordinate.
   * @param x the x coordinate to be set.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Gets the y coordinate.
   * @return the y coordinate.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the y coordinate.
   * @param y the y coordinate to be set.
   */
  public void setY(double y) {
    this.y = y;
  }
}
