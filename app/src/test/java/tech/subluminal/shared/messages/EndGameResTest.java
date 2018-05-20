package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class EndGameResTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    String gameID = "4054";
    String winnerID = "4053";
    EndGameRes endGameRes = new EndGameRes(gameID, winnerID);
    EndGameRes parsedEndGameRes = EndGameRes.fromSON(SON.parse(endGameRes.asSON().asString()));

    assertNotNull(parsedEndGameRes);
    assertEquals(gameID, parsedEndGameRes.getGameID());
    assertEquals(winnerID, parsedEndGameRes.getWinnerID());
    System.out.println(endGameRes.asSON().asString());
  }
}
