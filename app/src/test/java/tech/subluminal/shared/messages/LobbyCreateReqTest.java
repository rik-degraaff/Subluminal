package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyCreateReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    LobbyCreateReq lobbyCreateReq = new LobbyCreateReq("greatest lobby ever");
    String lobbyName = lobbyCreateReq.getName();
    LobbyCreateReq parsedLobbyCreateMsg = null;
    String parsedLobbyName = null;
    String lobbyCreateReqMsg = lobbyCreateReq.asSON().asString();

    parsedLobbyCreateMsg = LobbyCreateReq.fromSON(SON.parse(lobbyCreateReqMsg));

    assertNotNull(parsedLobbyCreateMsg);
    assertNotNull(parsedLobbyCreateMsg.getName());
    parsedLobbyName = parsedLobbyCreateMsg.getName();
    assertEquals(lobbyName, parsedLobbyName);
    System.out.println(lobbyCreateReqMsg);

    // falsify the name key on purpose
    String faultyNameKey = "{\"NAME\":s\"greatest lobby ever\"}";
    parsedLobbyCreateMsg = null;
    parsedLobbyName = null;

    try {
      parsedLobbyCreateMsg = LobbyCreateReq.fromSON(SON.parse(faultyNameKey));
    } catch (SONParsingError | SONConversionError error) {
      parsedLobbyName = "conversion failed";
      parsedLobbyCreateMsg = new LobbyCreateReq(parsedLobbyName);
    }

    assertNotNull(parsedLobbyCreateMsg);
    assertNotNull(parsedLobbyName);
    assertEquals(parsedLobbyName, "conversion failed");
    assertEquals(parsedLobbyCreateMsg.getName(), parsedLobbyName);
  }
}