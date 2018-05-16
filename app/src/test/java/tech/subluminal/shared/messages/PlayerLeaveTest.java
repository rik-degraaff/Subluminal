package tech.subluminal.shared.messages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsing;
import tech.subluminal.shared.son.SONParsingError;

public class PlayerLeaveTest {

  private String ID;
  private PlayerLeave playerLeave;

  @Before
  public void initialize() {
    this.ID = "911";
    this.playerLeave = new PlayerLeave(this.ID);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String playerLeaveMsg = this.playerLeave.asSON().asString();
    String parsedID = "";
    PlayerLeave parsedPlayerLeave = PlayerLeave.fromSON(SON.parse(playerLeaveMsg));
    parsedID = parsedPlayerLeave.getId();

    Assert.assertEquals(this.ID, parsedID);
    System.out.println(playerLeaveMsg);
  }

  @Test
  public void testSONConversionErrorThrowing() {
    boolean IDSuccessfullyParsed = true;
    String faultyPlayerLeaveMsg = "{\"iD\":s\"911\"}";
    // the id key is supposed to be "id", so a SONConversionError should be thrown
    try {
      PlayerLeave parsedPlayerLeave = PlayerLeave.fromSON(SON.parse(faultyPlayerLeaveMsg));
    } catch (SONConversionError | SONParsingError e) {
      IDSuccessfullyParsed = false;
    }

    Assert.assertFalse(IDSuccessfullyParsed);
  }

}
