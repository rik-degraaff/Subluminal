package tech.subluminal.shared.son;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.stream.Stream;
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
  public void stringifyAndParseTrickyStrings() {
    final String key = "key";

    Stream.of("", "\"", "\\", "\\\"")
        .forEach(s -> assertEquals(s,
            stringifyAndParseSON(new SON().put(s, key), "object with a tricky string")
                .getString(key).get()));
  }

  @Test
  public void storeComplexObject() {
    testComplexSON(getComplexSON());

  }

  @Test
  public void stringifyAndParseComplexObject() {
    testComplexSON(stringifyAndParseSON(getComplexSON(), "complex oject"));
  }

  private static SON getComplexSON() {
    return new SON()
        .put(new SON()
            .put(true, "bool")
            .put(new SONList()
                .add(1)
                .add(new SON())
                .add(2.0), "list"
            ), "object")
        .put("str", "str")
        .put("nested", "1", "2", "3", "4")
        .put("nested_deeper", "1", "2", "3", "4", "5");
  }

  private static void testComplexSON(SON son) {
    assertEquals("Failed to retrieve String value.", "str",
        son.getString("str").get());
    assertEquals("Failed to retrieve nested String value.", "nested",
        son.getString("1", "2", "3", "4").get());
    assertEquals("Failed to retrieve nested String value.", "nested_deeper",
        son.getString("1", "2", "3", "4", "5").get());

    assertEquals("Failed to retrieve nested Boolean", true,
        son.getBoolean("object", "bool").get());

    assertEquals("Failed to retrieve int from list", 1,
        son.getList("object", "list").get().getInt(0).get().intValue());
    assertNotNull("Failed to retrieve SON from list",
        son.getList("object", "list").get().getObject(1));
    assertEquals("Failed to retrieve double from list", 2.0,
        son.getList("object", "list").get().getDouble(2).get(), 0.0001);

  }

  @Test
  public void stringifyAndParseSimpleSONObject() {
    final String key = "key";

    assertEquals(
        "Stringifying and parsing a SON object containing a single String value didn't work",
        "Test",
        stringifyAndParseSON(new SON().put("Test", key), "object with string")
            .getString(key)
            .get()
    );

    assertEquals(
        "Stringifying and parsing a SON object containing a single Integer value didn't work",
        23,
        stringifyAndParseSON(new SON().put(23, key), "object with integer")
            .getInt(key)
            .get()
            .intValue()
    );

    assertEquals(
        "Stringifying and parsing a SON object containing a single Double value didn't work",
        2.0,
        stringifyAndParseSON(new SON().put(2.0, key), "object with double")
            .getDouble(key)
            .get(),
        0.0001
    );

    assertEquals(
        "Stringifying and parsing a SON object containing a single Boolean value didn't work",
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
      fail("Failed to parse " + sonType + ", error: " + sonParsingError.getMessage());
    }
    return null;
  }
}
