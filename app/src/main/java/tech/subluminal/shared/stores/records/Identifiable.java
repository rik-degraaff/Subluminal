package tech.subluminal.shared.stores.records;

public class Identifiable {

  protected String id;

  public Identifiable(String id) {
    this.id = id;
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
