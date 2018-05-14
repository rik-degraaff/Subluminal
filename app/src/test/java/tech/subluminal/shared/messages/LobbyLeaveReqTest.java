package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyLeaveReqTest {

  @Test
  public void testStringifyAndParsing() throws SONConversionError, SONParsingError {
    LobbyLeaveReq lobbyLeaveReq = new LobbyLeaveReq();
    LobbyLeaveReq parsedLobbyLeaveReq = null;

    parsedLobbyLeaveReq = LobbyLeaveReq.fromSON(SON.parse(lobbyLeaveReq.asSON().asString()));

    assertNotNull(parsedLobbyLeaveReq);
    System.out.println(lobbyLeaveReq.asSON().asString());
  }
}
