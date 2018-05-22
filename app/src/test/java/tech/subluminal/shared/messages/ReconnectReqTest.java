package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class ReconnectReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    ReconnectReq reconnectReq = new ReconnectReq("Bob", "his mom's basement");
    ReconnectReq parsedReconnectReq = ReconnectReq.fromSON(SON.parse(reconnectReq.asSON().asString()));
    assertEquals(reconnectReq.getID(), parsedReconnectReq.getID());
    assertEquals(reconnectReq.getUsername(), parsedReconnectReq.getUsername());
    System.out.println(reconnectReq.asSON().asString());
  }
}
