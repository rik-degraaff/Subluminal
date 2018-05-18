package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Message that is sent from a server to a leaving client. This message, when converted to SON and
 * then to string, looks like this:
 * <pre>
 * {}
 * </pre>
 */
public class GameLeaveRes implements SONRepresentable {

  public static GameLeaveRes fromSON(SON son) throws SONConversionError {
    return new GameLeaveRes();
  }

  @Override
  public SON asSON() {
    return new SON();
  }
}
