package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONParsingError;

public class LogoutReqTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError{
    LogoutReq logoutReq = new LogoutReq();
    LogoutReq parsedLogoutReq = LogoutReq.fromSON(SON.parse(logoutReq.asSON().asString()));
    assertNotNull(parsedLogoutReq);
    System.out.println(logoutReq.asSON().asString());
  }
}
