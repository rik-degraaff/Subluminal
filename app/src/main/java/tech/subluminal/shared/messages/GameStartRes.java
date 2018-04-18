package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the response of the server to a game start request from the client.
 */
public class GameStartRes implements SONRepresentable {

  /**
   * Returns a new game start respone object, converted from its SON representation.
   *
   * @param son the SON representation of a game start request.
   * @return a game start response object, converted from its SON representation.
   */
  public static GameStartRes fromSON(SON son) {
    return new GameStartRes();
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
