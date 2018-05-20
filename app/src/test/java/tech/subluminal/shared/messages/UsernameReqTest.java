package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class UsernameReqTest {

  private UsernameReq usernameReq;
  private String username;

  @Before
  public void initialize() {
    this.username = "Pingu";
    this.usernameReq = new UsernameReq(this.username);
    this.usernameReq.setUsername(this.username);
  }

  @Test
  public void testParsing() throws SONConversionError, SONParsingError {
    String usernameReqMsg = this.usernameReq.asSON().asString();
    try {
      UsernameReq parsedUsernameReq = UsernameReq.fromSON(SON.parse(usernameReqMsg));
      String parsedUsername = parsedUsernameReq.getUsername();
      assertEquals(parsedUsername, this.username);
    } catch (SONConversionError | SONParsingError e) {
      e.printStackTrace();
    }
    System.out.println(usernameReqMsg);
  }

  @Test
  public void testSONConversionErrorThrowing() {
    boolean parsingSucceeded = true;
    String faultyUsernameReqMsg = "{\"USERNAME\":s\"Pingu\"}"; // the username key is supposed to be written with lowercase letters
    try {
      UsernameReq parsedUsernameReq = UsernameReq.fromSON(SON.parse(faultyUsernameReqMsg));
      System.out.println(parsingSucceeded);
    } catch (SONParsingError | SONConversionError e) {
      parsingSucceeded = false;
    }
    assertFalse(parsingSucceeded);
  }
}
