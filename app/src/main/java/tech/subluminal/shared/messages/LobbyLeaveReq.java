package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the lobby leave request from the client to the server. This message, converted to SON
 * and then to string, might look like this:
 * <pre>
 * {
 * }
 * </pre>
 */
public class LobbyLeaveReq implements SONRepresentable {

  /**
   * Creates and returns a new lobby leave request from its {@link SON} representation.
   *
   * @param son the {@link SON} representation of a lobby leave request.
   * @return a new lobby leave request, converted from its {@link SON} representation.
   */
  public static LobbyLeaveReq fromSON(SON son) {
    return new LobbyLeaveReq();
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
