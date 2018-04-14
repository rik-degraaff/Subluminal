package tech.subluminal.shared.stores.records.game;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class Coordinates implements SONRepresentable {

  private static final String X_KEY = "x";
  private static final String Y_KEY = "y";
  private double x;
  private double y;

  public Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Coordinates fromSON(SON son) throws SONConversionError {
    double x = son.getDouble(X_KEY)
        .orElseThrow(() -> SONRepresentable.error("Coordinates", X_KEY));

    double y = son.getDouble(Y_KEY)
        .orElseThrow(() -> SONRepresentable.error("Coordinates", Y_KEY));

    return new Coordinates(x, y);
  }

  /**
   * Gets the x coordinate.
   *
   * @return the x coordinate.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets the x coordinate.
   *
   * @param x the x coordinate to be set.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Gets the y coordinate.
   *
   * @return the y coordinate.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the y coordinate.
   *
   * @param y the y coordinate to be set.
   */
  public void setY(double y) {
    this.y = y;
  }

  public SON asSON() {
    return new SON()
        .put(x, X_KEY)
        .put(y, Y_KEY);
  }
}
