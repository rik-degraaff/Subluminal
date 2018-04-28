package tech.subluminal.shared.messages;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the response of the server to a game start request from the client.
 */
public class GameStartRes implements SONRepresentable {

  private static final String PLAYER_COLORS_KEY = "playerColors";
  private static final String CLASS_NAME = GameStartRes.class.getSimpleName();
  private static final String ID_KEY = "id";
  private static final String COLOR_KEY = "color";
  private static final String RED_KEY = "red";
  private static final String BLUE_KEY = "blue";
  private static final String GREEN_KEY = "green";

  Map<String, Color> playerColor;

  public GameStartRes(Map<String, Color> playerColor) {
    this.playerColor = playerColor;
  }

  public Map<String, Color> getPlayerColor() {
    return playerColor;
  }

  /**
   * Returns a new game start respone object, converted from its SON representation.
   *
   * @param son the SON representation of a game start request.
   * @return a game start response object, converted from its SON representation.
   */
  public static GameStartRes fromSON(SON son) throws SONConversionError {
    Map<String, Color> playerColor = new HashMap<>();
    SONList entries = son.getList(PLAYER_COLORS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, PLAYER_COLORS_KEY));
    for (SON entry : entries.objects()) {
      String id = entry.getString(ID_KEY)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ID_KEY));
      double red = entry.getDouble(COLOR_KEY, RED_KEY)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, RED_KEY));
      double green = entry.getDouble(COLOR_KEY, GREEN_KEY)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GREEN_KEY));
      double blue = entry.getDouble(COLOR_KEY, BLUE_KEY)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, BLUE_KEY));
      playerColor.put(id, new Color(red, green, blue, 1));
    }
    return new GameStartRes(playerColor);
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    SONList list = new SONList();
    playerColor.forEach((id, color) -> {
      list.add(new SON()
          .put(id, ID_KEY)
          .put(color.getRed(), COLOR_KEY, RED_KEY)
          .put(color.getGreen(), COLOR_KEY, GREEN_KEY)
          .put(color.getBlue(), COLOR_KEY, BLUE_KEY));
    });
    return new SON().put(list, PLAYER_COLORS_KEY);
  }
}
