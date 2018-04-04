package tech.subluminal.shared.son;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SONTest {

  @Test
  public void stringifyAndParseEmptySONObject() {
    SON son = new SON();
    String sonType = "empty object";
    SON res = stringifyAndParseSON(son, sonType);
    assertNotNull("Stringifying and parsing of empty object resulted in null", res);
  }

  @Test
  public void storeSingleValueInSONObject() {
    final String key = "key";

    assertEquals("Storing a single String value didn't work",
        "Test",
        new SON()
            .put("Test", key)
            .getString(key)
            .get()
    );

    assertEquals("Storing a single Integer value didn't work",
        23,
        new SON()
            .put(23, key)
            .getInt(key)
            .get()
            .intValue()
    );

    assertEquals("Storing a single Double value didn't work",
        2.0,
        new SON()
            .put(2.0, key)
            .getDouble(key)
            .get(),
        0.0001
    );

    assertEquals("Storing a single Boolean value didn't work",
        true,
        new SON()
            .put(true, key)
            .getBoolean(key)
            .get()
    );

    assertTrue("Storing a single SON object didn't work",
        new SON().put(new SON(), key)
            .getObject(key)
            .isPresent()
    );

    assertTrue("Storing a single SON object didn't work",
        new SON().put(new SONList(), key)
            .getList(key)
            .isPresent()
    );
  }

  @Test
  public void stringifyAndParseSimpleSONObject() {
    final String key = "key";

    assertEquals("Stringifying and parsing a SON object containing a single String value didn't work",
        "Test",
        stringifyAndParseSON(new SON().put("Test", key), "object with string")
            .getString(key)
            .get()
    );

    assertEquals("Stringifying and parsing a SON object containing a single Integer value didn't work",
        23,
        stringifyAndParseSON(new SON().put(23, key), "object with integer")
            .getInt(key)
            .get()
            .intValue()
    );

    assertEquals("Stringifying and parsing a SON object containing a single Double value didn't work",
        2.0,
        stringifyAndParseSON(new SON().put(2.0, key), "object with double")
            .getDouble(key)
            .get(),
        0.0001
    );

    assertEquals("Stringifying and parsing a SON object containing a single Boolean value didn't work",
        true,
        stringifyAndParseSON(new SON().put(true, key), "object with boolean")
            .getBoolean(key)
            .get()
    );

    assertTrue("Stringifying and parsing a SON object containing a single SON object didn't work",
        stringifyAndParseSON(new SON().put(new SON(), key), "nested object")
            .getObject(key)
            .isPresent()
    );

    assertTrue("Stringifying and parsing a SON object containing a single SON object didn't work",
        stringifyAndParseSON(new SON().put(new SONList(), key), "object with list")
            .getList(key)
            .isPresent()
    );
  }

  private static SON stringifyAndParseSON(SON son, String sonType) {
    try {
      return SON.parse(son.asString());
    } catch (SONParsingError sonParsingError) {
      fail("Failed to parse " + sonType + " error: " + sonParsingError.getMessage());
    }
    return null;
  }
}
