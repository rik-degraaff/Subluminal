package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the lobby leave request from the client to the server.
 */

public class LobbyLeaveRes implements SONRepresentable {

  public static LobbyLeaveRes fromSON(SON son) {
    return new LobbyLeaveRes();
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation (empty).
   */
  @Override
  public SON asSON() {
    return new SON();
  }
}
