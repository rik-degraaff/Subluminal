package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyLeaveResTest {

  @Test
  public void testStringifyAndParsing() {
    LobbyLeaveRes lobbyLeaveRes = new LobbyLeaveRes();
    LobbyLeaveRes parsedLobbyLeaveRes = null;

    try {
      parsedLobbyLeaveRes = LobbyLeaveRes.fromSON(SON.parse(lobbyLeaveRes.asSON().asString()));
    } catch (SONParsingError e) {
      System.out.println("There should be no error here");
    }

    assertNotNull(parsedLobbyLeaveRes);
    System.out.println(lobbyLeaveRes.asSON().asString());
  }
}
