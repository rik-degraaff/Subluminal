package tech.subluminal.shared.messages;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class GameStartResTest {

  @Test
  public void stringifyAndParseGameStartRes() throws SONParsingError, SONConversionError {
    Map<String, Color> playerColors = new HashMap<>();
    playerColors.put("1234", new Color(Math.random(), Math.random(), Math.random(), 1));
    playerColors.put("1235", new Color(Math.random(), Math.random(), Math.random(), 1));
    playerColors.put("3234", new Color(Math.random(), Math.random(), Math.random(), 1));
    GameStartRes res = new GameStartRes("112314", playerColors);

    Map<String, Color> parsed = GameStartRes.fromSON(SON.parse(res.asSON().asString()))
        .getPlayerColor();
    playerColors.forEach((id, color) -> {
      assertNotNull(parsed.get(id));
      assertEquals(color.getRed(), parsed.get(id).getRed(), 0.00000001);
      assertEquals(color.getGreen(), parsed.get(id).getGreen(), 0.00000001);
      assertEquals(color.getBlue(), parsed.get(id).getBlue(), 0.00000001);
    });

    System.out.println(res.asSON().asString());
  }

}
