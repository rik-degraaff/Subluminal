package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class YouLoseTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    YouLose youLose = new YouLose();
    YouLose parsedYouLose = YouLose.fromSON(SON.parse(youLose.asSON().asString()));
    assertNotNull(parsedYouLose);
    System.out.println(youLose.asSON().asString());
  }
}
