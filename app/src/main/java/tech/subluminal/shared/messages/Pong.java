package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a pong (an answer to a ping) from client to server or vice versa. This message, when
 * converted to SON and then to string, might look like this:
 * <pre>
 * {
 *   "id":s"1234"
 * }
 * </pre>
 */
public class Pong implements SONRepresentable {

  private static final String ID_KEY = "id";
  private String id;

  /**
   * Creates a pong with a given id.
   *
   * @param id the id of the pong.
   */
  public Pong(String id) {
    this.id = id;
  }

  /**
   * Creates a ping from a SON object.
   *
   * @param son the SON object to be converted to a pong.
   * @return the created pong.
   * @throws SONConversionError when the conversion fails.
   */
  public static Pong fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Pong did not contain valid " + ID_KEY + "."));
    return new Pong(id);
  }

  /**
   * Returns the id of the pong message.
   *
   * @return the id of the pong message.
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