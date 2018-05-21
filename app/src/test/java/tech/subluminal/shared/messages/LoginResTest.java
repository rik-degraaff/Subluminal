package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LoginResTest {

  @Test
  public void testStringifyAndParsing() throws SONConversionError, SONParsingError {
    String username = "Patrick";
    String ID = "1111";
    LoginRes loginRes = new LoginRes(username, ID);
    loginRes.setUserID(ID);
    loginRes.setUsername(username);
    LoginRes parsedLoginRes = null;

    parsedLoginRes = LoginRes.fromSON(SON.parse(loginRes.asSON().asString()));
    assertNotNull(parsedLoginRes);
    assertEquals(ID, parsedLoginRes.getUserID());
    assertEquals(username, parsedLoginRes.getUsername());
    System.out.println(loginRes.asSON().asString());

    // destroy the username key and the userID key to provoke errors
    boolean conversionFailed = false;
    try {
      parsedLoginRes = LoginRes.fromSON(SON.parse(loginRes.asSON().asString().replace("username", "USERNAME")));
    } catch (SONConversionError | SONParsingError e) {
      conversionFailed = true;
    }
    assertTrue(conversionFailed);

    conversionFailed = false;
    try {
      parsedLoginRes = LoginRes.fromSON(SON.parse(loginRes.asSON().asString().replace("userID", "userId")));
    } catch (SONConversionError | SONParsingError e) {
      conversionFailed = true;
    }
    assertTrue(conversionFailed);
  }
}
