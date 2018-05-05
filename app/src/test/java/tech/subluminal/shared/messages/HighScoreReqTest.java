package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class HighScoreReqTest {

  @Test
  public void testStringifyAndParsing() {
    HighScoreReq h = new HighScoreReq();
    String hMsg = h.asSON().asString();
    HighScoreReq parsedH = null;

    try {
      parsedH = HighScoreReq.fromSON(SON.parse(hMsg));
    } catch (SONParsingError | SONConversionError e) {
      e.printStackTrace();
    }
    assertNotNull(parsedH);
    System.out.println(hMsg);

  }

}
