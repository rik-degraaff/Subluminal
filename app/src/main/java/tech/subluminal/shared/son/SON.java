package tech.subluminal.shared.son;

import static tech.subluminal.shared.son.SONParsing.BOOLEAN_ID;
import static tech.subluminal.shared.son.SONParsing.DOUBLE_ID;
import static tech.subluminal.shared.son.SONParsing.ENTRY_DELIMITER;
import static tech.subluminal.shared.son.SONParsing.INTEGER_ID;
import static tech.subluminal.shared.son.SONParsing.KEY_VALUE_DELIMITER;
import static tech.subluminal.shared.son.SONParsing.LIST_ID;
import static tech.subluminal.shared.son.SONParsing.OBJECT_ID;
import static tech.subluminal.shared.son.SONParsing.OBJECT_LEFTBRACE;
import static tech.subluminal.shared.son.SONParsing.OBJECT_RIGHTBRACE;
import static tech.subluminal.shared.son.SONParsing.PartialParseResult;
import static tech.subluminal.shared.son.SONParsing.STRING_ID;
import static tech.subluminal.shared.son.SONParsing.keyValueString;
import static tech.subluminal.shared.son.SONParsing.partialParseBoolean;
import static tech.subluminal.shared.son.SONParsing.partialParseDouble;
import static tech.subluminal.shared.son.SONParsing.partialParseInt;
import static tech.subluminal.shared.son.SONParsing.partialParseString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Subluminal Object Notation:
 *
 * A structured object with keys associated with typed values.
 */
public class SON {

  private Map<String, Integer> intMap = new HashMap<>();
  private Map<String, String> stringMap = new HashMap<>();
  private Map<String, Double> doubleMap = new HashMap<>();
  private Map<String, Boolean> booleanMap = new HashMap<>();
  private Map<String, SON> sonMap = new HashMap<>();
  private Map<String, SONList> sonListMap = new HashMap<>();

  public static SON parse(String str) throws SONParsingError {
    return partialParse(str, 0).result;
  }

  private static PartialParseResult<SON> partialParse(String str, int start)
      throws SONParsingError {
    try {
      if (str.charAt(start) != OBJECT_LEFTBRACE) {
        throw new SONParsingError("Expected an object but found no left brace.");
      }

      SON son = new SON();

      int i = start;
      if (str.charAt(i + 1) == OBJECT_RIGHTBRACE) {
        return new PartialParseResult<>(son, i + 2);
      }

      do {
        PartialParseResult<String> keyRes = partialParseString(str, i + 1);
        i = keyRes.pos;
        if (str.charAt(i++) != KEY_VALUE_DELIMITER) {
          throw new SONParsingError("Expected a key-value pair, but found no colon.");
        }

        char typeID = str.charAt(i++);
        switch (typeID) {
          case INTEGER_ID:
            PartialParseResult<Integer> intRes = partialParseInt(str, i);
            i = intRes.pos;
            son.put(intRes.result, keyRes.result);
            break;
          case DOUBLE_ID:
            PartialParseResult<Double> doubleRes = partialParseDouble(str, i);
            i = doubleRes.pos;
            son.put(doubleRes.result, keyRes.result);
            break;
          case BOOLEAN_ID:
            PartialParseResult<Boolean> boolRes = partialParseBoolean(str, i);
            i = boolRes.pos;
            son.put(boolRes.result, keyRes.result);
            break;
          case STRING_ID:
            PartialParseResult<String> strRes = partialParseString(str, i);
            i = strRes.pos;
            son.put(strRes.result, keyRes.result);
            break;
          case OBJECT_ID:
            PartialParseResult<SON> objRes = partialParse(str, i);
            i = objRes.pos;
            son.put(objRes.result, keyRes.result);
            break;
          case LIST_ID:
            break;
          default:
            throw new SONParsingError("Expected a value, but found no type identifier.");
        }
      } while (str.charAt(i) == ENTRY_DELIMITER);

      if (str.charAt(i) != OBJECT_RIGHTBRACE) {
        throw new SONParsingError("SON object was not terminated.");
      }

      return new PartialParseResult<>(son, i + 1);
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("SON object was not terminated.");
    }
  }

  private SON getNested(String key, String[] additionalKeys) {
    SON son = getSONForced(key);
    for (int i = 0; i < additionalKeys.length - 1; i++) {
      son = son.getSONForced(additionalKeys[i]);
    }
    return son;
  }

  private SON getSONForced(String key) {
    return getObject(key).orElseGet(() -> {
      SON s = new SON();
      put(s, key);
      return s;
    });
  }

  /**
   * Adds an integer value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(int value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
    }
    intMap.put(key, value);
    return this;
  }

  /**
   * Adds a double value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(double value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      getNested(key, additionalKeys).put(value, additionalKeys[additionalKeys.length - 1]);
    } else {
      doubleMap.put(key, value);
    }
    return this;
  }

  /**
   * Adds a boolean value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(boolean value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      getNested(key, additionalKeys).put(value, additionalKeys[additionalKeys.length - 1]);
    } else {
      booleanMap.put(key, value);
    }
    return this;
  }

  /**
   * Adds a string value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */

  public SON put(String value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      getNested(key, additionalKeys)
          .put(value, additionalKeys[additionalKeys.length - 1]);
    } else {
      stringMap.put(key, value);
    }
    return this;
  }

  /**
   * Adds a SON object for a given key (or sequence of keys). Creates a hierarchy of SON objects if
   * necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(SON value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      getNested(key, additionalKeys)
          .put(value, additionalKeys[additionalKeys.length - 1]);
    } else {
      sonMap.put(key, value);
    }
    return this;
  }

  /**
   * Adds a SON list for a given key (or sequence of keys). Creates a hierarchy of SON objects if
   * necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(SONList value, String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      getNested(key, additionalKeys)
          .put(value, additionalKeys[additionalKeys.length - 1]);
    } else {
      sonListMap.put(key, value);
    }
    return this;
  }

  private Optional<SON> getOptionalNested(String key, String... additionalKeys) {
    Optional<SON> son = getObject(key);
    for (int i = 0; i < additionalKeys.length - 1; i++) {
      int ii = i;
      son = son.flatMap(s -> s.getObject(additionalKeys[ii]));
    }
    return son;
  }

  /**
   * Gets an integer value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Integer> getInt(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getInt(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(intMap.get(key));
  }

  /**
   * Gets a double value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Double> getDouble(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getDouble(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(doubleMap.get(key));
  }

  /**
   * Gets a boolean value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Boolean> getBoolean(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getBoolean(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(booleanMap.get(key));
  }

  /**
   * Gets a string value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<String> getString(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getString(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(stringMap.get(key));
  }

  /**
   * Gets a SON object for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SON> getObject(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getObject(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(sonMap.get(key));
  }

  /**
   * Gets a SON list for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SONList> getList(String key, String... additionalKeys) {
    if (additionalKeys.length != 0) {
      return getOptionalNested(key, additionalKeys)
          .flatMap(s -> s.getList(additionalKeys[additionalKeys.length - 1]));
    }
    return Optional.ofNullable(sonListMap.get(key));
  }

  /**
   * @return a string representation of this son object that can be parsed again.
   */
  public String asString() {
    StringBuilder builder = new StringBuilder();

    builder.append(OBJECT_LEFTBRACE);

    Stream.of(
        intMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())),
        doubleMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())),
        booleanMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())),
        stringMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())),
        sonMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())),
        sonListMap.entrySet().stream().map(e -> keyValueString(e.getKey(), e.getValue())))
        .flatMap(s -> s)
        .flatMap(s -> Stream.of(ENTRY_DELIMITER, s))
        .skip(1)
        .forEach(builder::append);

    builder.append(OBJECT_RIGHTBRACE);

    return builder.toString();
  }
}
