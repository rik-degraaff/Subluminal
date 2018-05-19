package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class StartTutorialReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    StartTutorialReq startTutorialReq = new StartTutorialReq();
    assertNotNull(StartTutorialReq.fromSON(SON.parse(startTutorialReq.asSON().asString())));
    System.out.println(startTutorialReq.asSON().asString());
  }
}
