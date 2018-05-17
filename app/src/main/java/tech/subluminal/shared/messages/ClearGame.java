package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the clear game message from the server to the client. This message, when converted
 * to SON and then to String, looks like this:
 * <pre>
 * {}
 * </pre>
 */
public class ClearGame implements SONRepresentable {

  /**
   * Creates a new ClearGame from its SON representation.
   *
   * @param son the SON representation of a ClearGame message.
   * @return the ClearGame message converted from its SON representation.
   */
  public static ClearGame fromSON(SON son) {
    return new ClearGame();
  }

  /**
   * Creates a SON object representing this object (empty).
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON();
  }
}
