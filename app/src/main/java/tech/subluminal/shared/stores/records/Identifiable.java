package tech.subluminal.shared.stores.records;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class Identifiable {

  private static final String ID = "id";
  private String id;

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

  protected void loadFromSON(SON son) throws SONConversionError {
    id = son.getString(ID)
        .orElseThrow(() -> SONRepresentable.error("Identifiable", ID));
  }

  protected SON asSON() {
    return new SON().put(id, ID);
  }
}
