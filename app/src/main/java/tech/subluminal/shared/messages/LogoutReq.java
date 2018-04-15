package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the logout request from the client to the server.
 */
public class LogoutReq implements SONRepresentable {

  public static LogoutReq fromSON(SON son) {
    return new LogoutReq();
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
