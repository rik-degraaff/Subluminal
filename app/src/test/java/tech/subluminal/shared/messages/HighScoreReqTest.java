package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class HighScoreReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    HighScoreReq h = new HighScoreReq();
    String hMsg = h.asSON().asString();
    HighScoreReq parsedH = null;

    parsedH = HighScoreReq.fromSON(SON.parse(hMsg));
    assertNotNull(parsedH);
    System.out.println(hMsg);

  }

}
