package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class SpectateGameReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    SpectateGameReq spectateGameReq = new SpectateGameReq("8000");
    SpectateGameReq parsedSpectateGameReq = SpectateGameReq.fromSON(SON.parse(spectateGameReq.asSON().asString()));
    assertEquals(spectateGameReq.getID(), parsedSpectateGameReq.getID());
    System.out.println(spectateGameReq.asSON().asString());
  }
}
