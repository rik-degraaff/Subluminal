package tech.subluminal.shared.messages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsing;
import tech.subluminal.shared.son.SONParsingError;

public class PlayerUpdateTest {
  private String username;
  private String id;
  private PlayerUpdate playerUpdate;

  @Before
  public void initialize() {
    this.username = "Rik";
    this.id = "007";
    this.playerUpdate = new PlayerUpdate(this.id, this.username);
  }

  @Test
  public void testParsing() {
    String playerUpdateMsg = this.playerUpdate.asSON().asString();
    String parsedUsername = "";
    String parsedID = "";
    try {
      PlayerUpdate parsedPlayerUpdate = PlayerUpdate.fromSON(SON.parse(playerUpdateMsg));
      parsedUsername = parsedPlayerUpdate.getUsername();
      parsedID = parsedPlayerUpdate.getId();
    } catch (SONConversionError | SONParsingError e) {
      e.printStackTrace();
    }

    Assert.assertEquals(this.id, parsedID);
    Assert.assertEquals(this.username, parsedUsername);
    System.out.println(playerUpdateMsg);
    }

    @Test
    public void testSONConversionErrorThrowing() {
      boolean IDSuccessfullyParsed = true;
      boolean usernameSuccessfullyParsed = true;
      String faultyID = "{\"ID\":s\"007\",\"username\":s\"Rik\"}";
      String faultyUsername = "{\"id\":s\"007\",\"USERNAME\":s\"Rik\"}";
      // the keys are supposed to be "id" and "username", so SONConversionErrors are supposed to be thrown here
      try {
        PlayerUpdate playerUpdate = PlayerUpdate.fromSON(SON.parse(faultyID));
      } catch (SONConversionError | SONParsingError e) {
        e.printStackTrace();
        IDSuccessfullyParsed = false;
      }
      try {
        PlayerUpdate playerUpdate = PlayerUpdate.fromSON(SON.parse(faultyUsername));
      } catch (SONParsingError | SONConversionError e) {
        e.printStackTrace();
        usernameSuccessfullyParsed = false;
      }
      Assert.assertFalse(IDSuccessfullyParsed);
      Assert.assertFalse(usernameSuccessfullyParsed);
    }

}
