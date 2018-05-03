package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.LobbySettings;
import tech.subluminal.shared.stores.records.SlimLobby;
import org.pmw.tinylog.Logger;


public class LobbyListResTest {

  @Test
  public void testStringifyAndParsing() {
    List<SlimLobby> slimLobbies = new LinkedList<>();
    SlimLobby slimLobby1 = new SlimLobby("9439", new LobbySettings("weoi", "3472"), LobbyStatus.FULL);
    SlimLobby slimLobby2 = new SlimLobby("1790", new LobbySettings("eruo", "1338"), LobbyStatus.LOCKED);
    SlimLobby slimLobby3 = new SlimLobby("1295", new LobbySettings("sdfu", "1237"), LobbyStatus.OPEN);
    slimLobbies.add(slimLobby1);
    slimLobbies.add(slimLobby2);
    slimLobbies.add(slimLobby3);
    LobbyListRes lobbyListRes = new LobbyListRes(slimLobbies);
    String lobbyListResMsg = lobbyListRes.asSON().asString();
    SlimLobby parsedSlimLobby1 = null;
    SlimLobby parsedSlimLobby2 = null;
    SlimLobby parsedSlimLobby3 = null;
    LobbyListRes parsedLobbyListRes = null;

    try {
      parsedLobbyListRes = LobbyListRes.fromSON(SON.parse(lobbyListResMsg));
      parsedSlimLobby1 = parsedLobbyListRes.getSlimLobbies().get(0);
      parsedSlimLobby2 = parsedLobbyListRes.getSlimLobbies().get(1);
      parsedSlimLobby3 = parsedLobbyListRes.getSlimLobbies().get(2);
    } catch (SONParsingError | SONConversionError error) {
      error.printStackTrace();
    }

    assertNotNull(parsedLobbyListRes);
    parsedLobbyListRes.getSlimLobbies().forEach(Assert::assertNotNull);

    assertEquals(slimLobby1.getPlayerCount(), parsedSlimLobby1.getPlayerCount());
    assertEquals(slimLobby1.getStatus().getColor(), parsedSlimLobby1.getStatus().getColor());
    assertEquals(slimLobby2.getSettings().getAdminID(), parsedSlimLobby2.getSettings().getAdminID());
    assertEquals(slimLobby2.getSettings().getMaxPlayers(), parsedSlimLobby2.getSettings().getMaxPlayers());
    assertEquals(slimLobby3.getPlayerCount(), parsedSlimLobby3.getPlayerCount());
    assertEquals(slimLobby3.getSettings().getName(), slimLobby3.getSettings().getName());

    System.out.println(lobbyListResMsg);
  }
}
