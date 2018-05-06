package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Message gets send from a leaving client to the server.
 */
public class GameLeaveReq implements SONRepresentable {

  public static GameLeaveReq fromSON(SON son) throws SONConversionError{
    return new GameLeaveReq();
  }

  @Override
  public SON asSON() {
    return new SON();
  }
}
