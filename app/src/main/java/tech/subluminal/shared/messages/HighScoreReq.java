package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a request message for the highscore. A HighScoreReq message converted to SON and then to string looks like this:
 * <pre>
 * {
 * }
 * </pre>
 */
public class HighScoreReq implements SONRepresentable {

  public static HighScoreReq fromSON(SON son) throws SONConversionError {
    return new HighScoreReq();
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
