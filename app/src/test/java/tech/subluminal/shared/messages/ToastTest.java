package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class ToastTest {
  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    Toast toast1 = new Toast("I'm a message");
    Toast toast2 = new Toast("Snoop Dogg", true);
    Toast parsedToast1 = Toast.fromSON(SON.parse(toast1.asSON().asString()));
    Toast parsedToast2 = Toast.fromSON(SON.parse(toast2.asSON().asString()));
    assertEquals(toast1.getMessage(), parsedToast1.getMessage());
    assertEquals(toast2.getMessage(), parsedToast2.getMessage());
    assertEquals(toast2.isPermanent(), toast2.isPermanent());
    System.out.println(toast1.asSON().asString());
    System.out.println(toast2.asSON().asString());
  }
}
