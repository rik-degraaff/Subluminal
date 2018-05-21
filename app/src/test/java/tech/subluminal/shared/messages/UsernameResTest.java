package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class UsernameResTest {

  private UsernameRes usernameRes;
  private String username;

  @Before
  public void initialize() {
    this.username = "Donald";
    this.usernameRes = new UsernameRes(this.username);
    this.usernameRes.setUsername(this.username);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String usernameResMsg = this.usernameRes.asSON().asString();
    UsernameRes parsedUsernameRes = UsernameRes.fromSON(SON.parse(usernameResMsg));
    String parsedUsername = parsedUsernameRes.getUsername();
    assertEquals(parsedUsername, this.username);
    System.out.println(usernameResMsg);
  }

  @Test
  public void testSONConversionErrorThrowing() {
    boolean parsingSucceeded = true;
    String faultyUsernameResMsg = "{\"USERNAME\":s\"Donald\"}"; // the username key is supposed to be written with lowercase letters
    try {
      UsernameRes parsedUsernameRes = UsernameRes.fromSON(SON.parse(faultyUsernameResMsg));
    } catch (SONParsingError | SONConversionError e) {
      parsingSucceeded = false;
    }
    assertFalse(parsingSucceeded);
  }

}
