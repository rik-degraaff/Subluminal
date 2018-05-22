package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class GameLeaveResTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    GameLeaveRes gameLeaveRes = new GameLeaveRes();
    assertNotNull(GameLeaveRes.fromSON(SON.parse(gameLeaveRes.asSON().asString())));
    System.out.println(gameLeaveRes.asSON().asString());
  }
}
