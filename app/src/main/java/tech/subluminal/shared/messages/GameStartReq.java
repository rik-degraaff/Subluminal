package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

public class GameStartReq implements SONRepresentable {

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON();
  }

  public static GameStartReq fromSON(SON son) {
    return new GameStartReq();
  }
}
