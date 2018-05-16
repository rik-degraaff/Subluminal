package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyListReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    LobbyListReq lobbyListReq = new LobbyListReq();
    LobbyListReq parsedLobbyListReq = null;

    parsedLobbyListReq = LobbyListReq.fromSON(SON.parse(lobbyListReq.asSON().asString()));

    assertNotNull(parsedLobbyListReq);
    System.out.println(lobbyListReq.asSON().asString());
  }
}
