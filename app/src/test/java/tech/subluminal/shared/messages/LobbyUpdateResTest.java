package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.LobbySettings;

public class LobbyUpdateResTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    LobbyUpdateRes lobbyUpdateRes = new LobbyUpdateRes(
        new Lobby("0800", new LobbySettings("Stromberg", "4321"),
            LobbyStatus.OPEN));
    Lobby lobby = lobbyUpdateRes.getLobby();
    LobbyUpdateRes parsedLobbyUpdateRes = null;
    Lobby parsedLobby = null;

    parsedLobbyUpdateRes = LobbyUpdateRes.fromSON(SON.parse(lobbyUpdateRes.asSON().asString()));
    assertNotNull(parsedLobbyUpdateRes);
    parsedLobby = parsedLobbyUpdateRes.getLobby();
    System.out.println(lobbyUpdateRes.asSON().asString());

    assertEquals(lobbyUpdateRes.getLobby().getID(), parsedLobbyUpdateRes.getLobby().getID());
    assertEquals(lobbyUpdateRes.getLobby().getStatus(),
        parsedLobbyUpdateRes.getLobby().getStatus());
    assertEquals(lobbyUpdateRes.getLobby().getSettings().getName(),
        parsedLobbyUpdateRes.getLobby().getSettings().getName());
    assertEquals(lobbyUpdateRes.getLobby().getSettings().getAdminID(),
        parsedLobbyUpdateRes.getLobby().getSettings().getAdminID());
  }
}
