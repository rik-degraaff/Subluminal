package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a game start request from a client to the server. A GameStartReq message converted to
 * SON and then to string looks like this:
 * <pre>
 * {
 * }
 * </pre>
 */
public class GameStartReq implements SONRepresentable {

  /**
   * Returns a new game start request, converted from its SON representation
   */
  public static GameStartReq fromSON(SON son) {
    return new GameStartReq();
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
