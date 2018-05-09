package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyListReqTest {

  @Test
  public void testStringifyAndParsing() {
    LobbyListReq lobbyListReq = new LobbyListReq();
    LobbyListReq parsedLobbyListReq = null;

    try {
      parsedLobbyListReq = LobbyListReq.fromSON(SON.parse(lobbyListReq.asSON().asString()));
    } catch (SONParsingError e) {
      System.out.println("If you see this, you did something wrong");
    }

    assertNotNull(parsedLobbyListReq);
    System.out.println(lobbyListReq.asSON().asString());
  }
}
