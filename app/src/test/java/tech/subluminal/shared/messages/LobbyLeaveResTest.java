package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyLeaveResTest {

  @Test
  public void testStringifyAndParsing() throws SONConversionError, SONParsingError {
    LobbyLeaveRes lobbyLeaveRes = new LobbyLeaveRes();
    LobbyLeaveRes parsedLobbyLeaveRes = null;

    parsedLobbyLeaveRes = LobbyLeaveRes.fromSON(SON.parse(lobbyLeaveRes.asSON().asString()));

    assertNotNull(parsedLobbyLeaveRes);
    System.out.println(lobbyLeaveRes.asSON().asString());
  }
}
