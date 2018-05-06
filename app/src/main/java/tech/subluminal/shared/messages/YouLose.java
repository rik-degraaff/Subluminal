package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * A message that is sent from the server to the client when the client loses in a game.
 */
public class YouLose implements SONRepresentable {

  /**
   * Creates a new YouLose message from its SON representation.
   *
   * @param son the SON representation of a YouLose message.
   * @return the YouLose message converted from its SON representation.
   */
  public static YouLose fromSON(SON son) {
    return new YouLose();
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
