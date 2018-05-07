package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LobbyJoinReqTest {

  @Test
  public void testStringifyAndParsing() {
    String ID = "4053";
    LobbyJoinReq lobbyJoinReq = new LobbyJoinReq(ID);
    LobbyJoinReq parsedLobbyJoinReq = null;
    String parsedID = null;

    try {
      parsedLobbyJoinReq = LobbyJoinReq.fromSON(SON.parse(lobbyJoinReq.asSON().asString()));
    } catch (SONParsingError | SONConversionError e) {
      System.out.println("This error should not have occurred.");
    }

    assertNotNull(parsedLobbyJoinReq);
    assertEquals(ID, parsedLobbyJoinReq.getId());
    System.out.println(lobbyJoinReq.asSON().asString());

    // now let's create a wrong id key in the SON object
    String faultyIdKey = lobbyJoinReq.asSON().asString().replace("id", "ID");

    boolean parsingFailed = false;
    try {
      parsedLobbyJoinReq = LobbyJoinReq.fromSON(SON.parse(faultyIdKey));
    } catch (SONParsingError | SONConversionError e) {
      parsingFailed = true;
    }

    assertTrue(parsingFailed);
  }

}
