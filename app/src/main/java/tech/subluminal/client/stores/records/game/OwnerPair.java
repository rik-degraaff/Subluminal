package tech.subluminal.client.stores.records.game;


import tech.subluminal.shared.stores.records.Identifiable;

public class OwnerPair<E extends Identifiable> extends Identifiable {

  private String key;
  private E value;

  /**
   * Creates a new pair
   *
   * @param key The key for this pair
   * @param value The value to use for this pair
   */
  public OwnerPair(String key, E value) {
    super(null);
    this.key = key;
    this.value = value;
  }


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public E getValue() {
    return value;
  }

  public void setValue(E value) {
    this.value = value;
  }

  @Override
  public String getID() {
    return getValue().getID();
  }
}
