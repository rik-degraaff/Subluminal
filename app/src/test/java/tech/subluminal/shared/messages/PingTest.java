package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class PingTest {

  private Ping ping;
  private String ID;

  @Before
  public void initialize() {
    this.ID = "1234";
    this.ping = new Ping(ID);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String pingMsg = ping.asSON().asString();
    Ping parsedPing = Ping.fromSON(SON.parse(pingMsg));
    String parsedID = parsedPing.getId();
    assertEquals(ping.getId(), parsedID);
    System.out.println(pingMsg);
  }

  @Test
  public void testSONConversionErrorThrowing() {
    boolean parsingSucceeded = true;
    String faultyPingMsg = "{\"ID\":s\"1234\"}"; // the ID key correctly should be "id" instead of "ID"
    try {
      Ping parsedPing = Ping.fromSON(SON.parse(faultyPingMsg));
      String ID = parsedPing.getId();
    } catch (SONParsingError | SONConversionError e) {
      parsingSucceeded = false;
    }
    assertFalse(parsingSucceeded);
  }

}
