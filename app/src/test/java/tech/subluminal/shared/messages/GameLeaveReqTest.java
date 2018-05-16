package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class GameLeaveReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    GameLeaveReq gameLeaveReq = new GameLeaveReq();
    GameLeaveReq parsedGameLeaveRequest = GameLeaveReq.fromSON(SON.parse(gameLeaveReq.asSON().asString()));
    assertNotNull(parsedGameLeaveRequest);
    System.out.println(gameLeaveReq.asSON().asString());
  }
}
