package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a lobby list request from a client to the server. This message, when converted to SON
 * and then to string, might look like this:
 * <pre>
 * {}
 * </pre>
 */
public class LobbyListReq implements SONRepresentable {

  /**
   * Creates and returns a new lobby list request from its SON representation.
   *
   * @param son the SON representation of a lobby list request.
   * @return the lobby list request, converted from its SON representation.
   */
  public static LobbyListReq fromSON(SON son) {
    return new LobbyListReq();
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON();
  }
}
