package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONParsing;
import tech.subluminal.shared.son.SONParsingError;

public class GameStartResTest {

  @Test
  public void testStringifyAndParsing() {
    GameStartRes g = new GameStartRes();
    String gMsg = g.asSON().asString();
    GameStartRes parsedG = null;

    try {
      parsedG = GameStartRes.fromSON(SON.parse(gMsg));
    } catch (SONParsingError e) {
      e.printStackTrace();
    }
    assertNotNull(parsedG);
    System.out.println(gMsg);
  }

}
