package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a game start request from a client to the server.
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
