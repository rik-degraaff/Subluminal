package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the logout request from the client to the server.
 */
public class LogoutReq implements SONRepresentable {

  /**
   * Creates a new LogoutReq from its SON representation.
   *
   * @param son the SON representation of a LogoutReq.
   * @return the LogoutReq converted from its SON representation.
   */
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
