package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a ping from client to server or vice versa.
 */
public class Ping implements SONRepresentable {

  private static final String ID_KEY = "id";
  private String id;

  /**
   * Creates a ping with a given id.
   *
   * @param id the id of the ping.
   */
  public Ping(String id) {
    this.id = id;
  }

  /**
   * Creates a ping from a SON object.
   *
   * @param son the SON object to be converted to a ping.
   * @return the created ping.
   * @throws SONConversionError when the conversion fails.
   */
  public static Ping fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Ping did not contain valid " + ID_KEY + "."));
    return new Ping(id);
  }

  /**
   * Returns the id of the ping message.
   *
   * @return the id of the ping message.
   */
  public String getId() {
    return id;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON().put(id, ID_KEY);
  }
}
