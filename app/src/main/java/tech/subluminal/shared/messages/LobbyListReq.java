package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

public class LobbyListReq implements SONRepresentable {

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
