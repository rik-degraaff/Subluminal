package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the start tutorial request from the client to the server. This message, when converted
 * to SON and then to String, looks like this:
 * <pre>
 * {}
 * </pre>
 */
public class StartTutorialReq implements SONRepresentable {

  /**
   * Creates a new StartTutorialReq from its SON representation.
   *
   * @param son the SON representation of a StartTutorialReq.
   * @return the StartTutorialReq converted from its SON representation.
   */
  public static StartTutorialReq fromSON(SON son) {
    return new StartTutorialReq();
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
