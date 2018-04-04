package tech.subluminal.shared.records.game;

public abstract class GameObject {

  private Coordinates coordinates;
  private String id;

  public GameObject(Coordinates coordinates, String id) {
    this.coordinates = coordinates;
    this.id = id;
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
   * @return the ID representing this game object.
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the ID to represent this game object.
   */
  public void setId(String id) {
    this.id = id;
  }
}
