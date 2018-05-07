package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyLeaveReqTest {

  @Test
  public void testStringifyAndParsing() {
    LobbyLeaveReq lobbyLeaveReq = new LobbyLeaveReq();
    LobbyLeaveReq parsedLobbyLeaveReq = null;

    try {
     parsedLobbyLeaveReq = LobbyLeaveReq.fromSON(SON.parse(lobbyLeaveReq.asSON().asString()));
    } catch (SONParsingError e) {
      System.out.println("If an error is thrown here, you need to check your code...");
    }

    assertNotNull(parsedLobbyLeaveReq);
    System.out.println(lobbyLeaveReq.asSON().asString());
  }
}
