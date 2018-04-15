package tech.subluminal.shared.stores.records.game;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Identifiable;

public abstract class GameObject extends Identifiable {

  private static final String CLASS_NAME = GameObject.class.getSimpleName();
  private static final String COORDINATES_KEY = "coordinates";
  private static final String IDENTIFIABLE_KEY = "identifiable";

  private Coordinates coordinates;

  public GameObject(Coordinates coordinates, String id) {
    super(id);
    this.coordinates = coordinates;
  }

  /**
   * @return the position of the game object on the map.
   */
  public Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * @param coordinates the position of the game object on the map.
   */
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * Calculates the Euclidian distance between this object and another game object.
   *
   * @param other the other object to calculate the distance to.
   * @return the distance calculated with Pythagorean theorem.
   */
  public double getDistanceFrom(GameObject other) {
    return Math.sqrt(Math.pow(coordinates.getX() - other.getCoordinates().getX(), 2)
        + Math.pow(coordinates.getY() - other.getCoordinates().getY(), 2));
  }

  protected void loadFromSON(SON son) throws SONConversionError {
    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));
    super.loadFromSON(identifiable);

    SON coords = son.getObject(COORDINATES_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, COORDINATES_KEY));

    this.coordinates = Coordinates.fromSON(coords);
  }

  protected SON asSON() {
    return new SON()
        .put(super.asSON(), IDENTIFIABLE_KEY)
        .put(coordinates.asSON(), COORDINATES_KEY);
  }

}
