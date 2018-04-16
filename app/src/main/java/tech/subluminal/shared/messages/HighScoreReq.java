package tech.subluminal.shared.messages;

import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class HighScoreReq implements SONRepresentable {

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON();
  }

  public static HighScoreReq fromSON(SON son) throws SONConversionError {
    return new HighScoreReq();
  }
}
