package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class ClearGameTest {

  @Test
  public void testStringifyAndParsing() throws SONConversionError, SONParsingError {
    ClearGame clearGame = new ClearGame();
    ClearGame parsedClearGame = ClearGame.fromSON(SON.parse(clearGame.asSON().asString()));
    assertNotNull(parsedClearGame);
    System.out.println(clearGame.asSON().asString());
  }
}
