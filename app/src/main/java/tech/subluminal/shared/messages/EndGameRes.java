package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Message for all Client which is send if a game ends.
 */
public class EndGameRes implements SONRepresentable {

  private static final String WINNER_KEY = "winner";
  private static final String GAME_KEY = "game";
  private static final String CLASS_NAME = EndGameRes.class.getSimpleName();
  private String winnerID;
  private String gameID;

  public EndGameRes(String gameID, String winnerID) {
    this.winnerID = winnerID;
    this.gameID = gameID;
  }

  public String getWinnerID() {
    return winnerID;
  }

  public String getGameID() {
    return gameID;
  }

  /**
   * Returns a new EndGameRes, converted from its SON representation.
   */
  public static EndGameRes fromSON(SON son) throws SONConversionError {
    String winnerID = son.getString(WINNER_KEY).orElse(null);
    String gameID = son.getString(GAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GAME_KEY));

    return new EndGameRes(gameID,winnerID);
  }

  @Override
  public SON asSON() {
    SON son = new SON()
        .put(gameID, GAME_KEY);
    if(winnerID != null){
      son.put(winnerID, WINNER_KEY);
    }

    return son;
  }
}
