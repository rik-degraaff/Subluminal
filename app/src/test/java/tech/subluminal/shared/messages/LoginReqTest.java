package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class LoginReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    String username = "Bob";
    LoginReq loginReq = new LoginReq(username);
    LoginReq parsedLoginReq = LoginReq.fromSON(SON.parse(loginReq.asSON().asString()));
    assertNotNull(parsedLoginReq);
    assertEquals(username, parsedLoginReq.getUsername());
    System.out.println(loginReq.asSON().asString());
  }
}
