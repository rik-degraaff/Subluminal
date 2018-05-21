package tech.subluminal.shared.messages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.User;

public class PlayerJoinTest {

  private PlayerJoin playerJoin;
  private User user;
  private String username;
  private String id;

  @Before
  public void initialize() {
    this.username = "Luc";
    this.id = "4107";
    this.user = new User(this.username, this.id);
    this.playerJoin = new PlayerJoin(this.user);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String PlayerJoinTestMsg = this.playerJoin.asSON().asString();
    String parsedUsername = "";
    String parsedID = "";
    PlayerJoin parsedPlayerJoin = null;
    parsedPlayerJoin = PlayerJoin.fromSON(SON.parse(PlayerJoinTestMsg));
    Assert.assertEquals(parsedPlayerJoin.getUser().getUsername(), this.username);
    Assert.assertEquals(parsedPlayerJoin.getUser().getID(), this.id);
    System.out.println(PlayerJoinTestMsg);
  }

  @Test
  public void testErrorThrowing() {
    String faultyUserKey = "{\"User\":o{\"username\":s\"Luc\",\"identifiable\":o{\"id\":s\"4107\"}}}";
    // user key should be "user", so an error should be thrown
    boolean userSuccessfullyParsed = true;
    String faultyUsernameKey = "{\"user\":o{\"userName\":s\"Luc\",\"identifiable\":o{\"id\":s\"4107\"}}}";
    // username key has typo, so an error should be thrown
    boolean usernameSuccessfullyParsed = true;
    String faultyIdentifiableKey = "{\"user\":o{\"username\":s\"Luc\",\"identifiablE\":o{\"id\":s\"4107\"}}}";
    // identifiable key has typo, so an error should be thrown
    boolean identifiableSuccessfullyParsed = true;
    String faultyIDKey = "{\"user\":o{\"username\":s\"Luc\",\"identifiable\":o{\"ID\":s\"4107\"}}}";
    // ID key should be "id", so an error should be thrown
    boolean IDSuccessfullyParsed = true;
    PlayerJoin parsedPlayerJoin = null;

    try {
      parsedPlayerJoin = PlayerJoin.fromSON(SON.parse(faultyUserKey));
    } catch(SONParsingError | SONConversionError e) {
      userSuccessfullyParsed = false;
    }

    try {
      parsedPlayerJoin = PlayerJoin.fromSON(SON.parse(faultyUsernameKey));
    } catch(SONParsingError | SONConversionError e) {
      usernameSuccessfullyParsed = false;
    }

    try {
      parsedPlayerJoin = PlayerJoin.fromSON(SON.parse(faultyIdentifiableKey));
    } catch(SONParsingError | SONConversionError e) {
      identifiableSuccessfullyParsed = false;
    }

    try {
      parsedPlayerJoin = PlayerJoin.fromSON(SON.parse(faultyIDKey));
    } catch(SONParsingError | SONConversionError e) {
      IDSuccessfullyParsed = false;
    }

    Assert.assertFalse(userSuccessfullyParsed);
    Assert.assertFalse(usernameSuccessfullyParsed);
    Assert.assertFalse(identifiableSuccessfullyParsed);
    Assert.assertFalse(IDSuccessfullyParsed);
  }

}
