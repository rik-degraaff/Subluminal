package tech.subluminal.shared.messages;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

import static org.junit.Assert.assertNotNull;

public class GameStartReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    GameStartReq g = new GameStartReq();
    String gMsg = g.asSON().asString();

    GameStartReq parsedG = GameStartReq.fromSON(SON.parse(gMsg));
    assertNotNull(parsedG);
    System.out.println(gMsg);
  }

}
