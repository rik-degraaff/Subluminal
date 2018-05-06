package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Message for all Client which is send if a game ends.
 */
public class EndGameRes implements SONRepresentable {

  private static final String WINNER_KEY = "id";
  private static final String CLASS_NAME = EndGameRes.class.getSimpleName();
  private String winnerID;

  public EndGameRes(String winnerID) {
    this.winnerID = winnerID;
  }

  public String getWinnerID() {
    return winnerID;
  }

  /**
   * Returns a new EndGameRes, converted from its SON representation.
   */
  public static EndGameRes fromSON(SON son) throws SONConversionError {
    String winnerID = son.getString(WINNER_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, WINNER_KEY));

    return new EndGameRes(winnerID);
  }

  @Override
  public SON asSON() {
    return new SON().put(winnerID, WINNER_KEY);
  }
}
