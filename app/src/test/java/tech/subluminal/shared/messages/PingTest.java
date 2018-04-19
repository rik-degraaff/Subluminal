package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;

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
  public void testParsing() {
    String pingMsg = ping.asSON().asString();
    try {
      Ping parsedPing = Ping.fromSON(SON.parse(pingMsg));
      String parsedID = parsedPing.getId();
      assertEquals(ping.getId(), parsedID);
    } catch (SONParsingError | SONConversionError e) {
      e.printStackTrace();
    }
    System.out.println(pingMsg);
  }

}
