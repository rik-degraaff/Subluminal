package tech.subluminal.shared.stores.records.game;

import tech.subluminal.shared.stores.records.Identifiable;

public abstract class GameObject extends Identifiable {

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

}
