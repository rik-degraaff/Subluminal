package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyCreateResTest {

  @Test
  public void testStringifyAndParsing() {
    String ID = "4053";
    LobbyCreateRes lobbyCreateRes = new LobbyCreateRes(ID);
    LobbyCreateRes parsedLobbyCreateRes = null;

    try {
      parsedLobbyCreateRes = LobbyCreateRes.fromSON(SON.parse(lobbyCreateRes.asSON().asString()));
    } catch (SONParsingError | SONConversionError error) {
      System.out.println("An unexpected error occured here.");
    }

    assertNotNull(parsedLobbyCreateRes);
    assertEquals(ID, lobbyCreateRes.asSON().getString("id").orElse("not the wanted id"));
    System.out.println(lobbyCreateRes.asSON().asString());

    // now let's test the error throwing when the id key is wrong
    boolean pasingFailed = false;
    try {
      parsedLobbyCreateRes = LobbyCreateRes.fromSON(SON.parse(lobbyCreateRes.asSON().asString().replace("id", "ID")));
    } catch (SONParsingError | SONConversionError e) {
      pasingFailed = true;
    }

    assertTrue(pasingFailed);
  }
}
