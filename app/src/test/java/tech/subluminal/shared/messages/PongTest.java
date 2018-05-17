package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class PongTest {

  private Pong pong;
  private String ID;

  @Before
  public void initialize() {
    this.ID = "1234";
    this.pong = new Pong(ID);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String pongMsg = pong.asSON().asString();
    Pong parsedPong = Pong.fromSON(SON.parse(pongMsg));
    String parsedID = parsedPong.getId();
    assertEquals(pong.getId(), parsedID);
    System.out.println(pongMsg);
  }

  /**
   * Tests if the exception {@code SONConversionError} or {@code SONParsingError} is thrown when a
   * faulty String
   */
  @Test
  public void SONConversionErrorThrowing() {
    boolean parsingSucceeded = true;
    String faultyPongMsg = "{\"ID\":s\"1234\"}"; // the ID key correctly should be "id" instead of "ID"
    try {
      Pong parsedPong = Pong.fromSON(SON.parse(faultyPongMsg));
      String ID = parsedPong.getId();
      System.out.println(ID);
    } catch (SONParsingError | SONConversionError e) {
      parsingSucceeded = false;
    }
    assertFalse(parsingSucceeded);
  }

}
