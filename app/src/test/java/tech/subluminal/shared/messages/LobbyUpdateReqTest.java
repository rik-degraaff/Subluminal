package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.LobbySettings;

public class LobbyUpdateReqTest {

  @Test
  public void testStringifyAndParsing() throws SONConversionError, SONParsingError, IllegalAccessException {
    LobbyUpdateReq lobbyUpdateReq = new LobbyUpdateReq(new LobbySettings("Rolf", "8000", 3, 5, 9.81, 3.14));
    LobbySettings lobbySettings = lobbyUpdateReq.getSettings();
    LobbySettings parsedLobbySettings = null;
    LobbyUpdateReq parsedLobbyUpdateReq = null;

    parsedLobbyUpdateReq = LobbyUpdateReq.fromSON(SON.parse(lobbyUpdateReq.asSON().asString()));
    parsedLobbySettings = parsedLobbyUpdateReq.getSettings();

    assertNotNull(parsedLobbyUpdateReq);
    Field[] settingsFields = lobbyUpdateReq.getSettings().getClass().getFields();
    Field[] parsedSettingsFields = parsedLobbySettings.getClass().getFields();

    for (int i = 0; i < settingsFields.length; i++) {
      assertEquals(settingsFields[i].get(lobbySettings), parsedSettingsFields[i].get(parsedLobbySettings));
    }

    System.out.println(lobbyUpdateReq.asSON().asString());

    // destroy the settings key to provoke a SONConversionError
    boolean conversionFailed = false;
    try {
      parsedLobbyUpdateReq = LobbyUpdateReq.fromSON(SON.parse(lobbyUpdateReq.asSON().asString().replace("settings", "SETTINGS")));
    } catch (SONConversionError e) {
      conversionFailed = true;
    }

    assertTrue(conversionFailed);
  }
}
