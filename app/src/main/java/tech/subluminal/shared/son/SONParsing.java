package tech.subluminal.shared.son;

import org.pmw.tinylog.Logger;

public class SONParsing {

  static final char KEY_VALUE_DELIMITER = ':';
  static final char ENTRY_DELIMITER = ',';
  static final char OBJECT_LEFTBRACE = '{';
  static final char OBJECT_RIGHTBRACE = '}';
  static final char LIST_LEFTBRACE = '[';
  static final char LIST_RIGHTBRACE = ']';
  static final char INTEGER_ID = 'i';
  static final char DOUBLE_ID = 'd';
  static final char BOOLEAN_ID = 'b';
  static final char STRING_ID = 's';
  static final char OBJECT_ID = 'o';
  static final char LIST_ID = 'l';
  private static final char STRING_DELIMITER = '"';
  private static final char STRING_ESCAPE = '\\';

  /**
   * Parses a Integer to a representing String format.
   *
   * @param key specifies the keyword for deparsing the object later.
   * @return the Integer in the SON specific String format.
   */
  static String keyValueString(String key, Integer value) {
    return stringString(key) + KEY_VALUE_DELIMITER + INTEGER_ID + integerString(value);
  }

  /**
   * Parses a Double to a representing String format.
   *
   * @param key specifies the key to deparsing the object later.
   * @return the Double in the SON specific String format.
   */
  static String keyValueString(String key, Double value) {
    return stringString(key) + KEY_VALUE_DELIMITER + DOUBLE_ID + doubleString(value);
  }

  /**
   * Parses a Boolean to a representing String format.
   *
   * @param key specifies the key to deparsing the object later.
   * @return the Boolean in the SON specific String format.
   */
  static String keyValueString(String key, Boolean value) {
    return stringString(key) + KEY_VALUE_DELIMITER + BOOLEAN_ID + booleanString(value);
  }

  /**
   * Parses a String to a representing String format.
   *
   * @param key specifies the key to deparsing the object later.
   * @return the String in the SON specific String format.
   */
  static String keyValueString(String key, String value) {
    return stringString(key) + KEY_VALUE_DELIMITER + STRING_ID + stringString(value);
  }

  /**
   * Parses a SON to a representing String format.
   *
   * @param key specifies the key to deparsing the object later.
   * @return the String in SON the SON specific String format.
   */
  static String keyValueString(String key, SON value) {
    return stringString(key) + KEY_VALUE_DELIMITER + OBJECT_ID + value.asString();
  }

  /**
   * Parses a SONList to a representing String format.
   *
   * @param key specifies the key to deparsing the object later.
   * @return the String in SON the SON specific String format.
   */
  static String keyValueString(String key, SONList value) {
    return stringString(key) + KEY_VALUE_DELIMITER + LIST_ID + value.asString();
  }

  static String integerString(Integer value) {
    return value.toString();
  }

  /**
   * Parses a Integer from its SON specific String form.
   *
   * @param str is the sequence contaning the Integer.
   */
  static PartialParseResult<Integer> partialParseInt(String str, int i) throws SONParsingError {
    try {
      StringBuilder builder = new StringBuilder();
      char nextChar = str.charAt(i);
      while (nextChar == '-' || nextChar >= '0' && nextChar <= '9') {
        //loop throught string and append
        builder.append(nextChar);
        nextChar = str.charAt(++i);
      }

      try {
        int value = Integer.parseInt(builder.toString());
        return new PartialParseResult<>(value, i);
      } catch (NumberFormatException e) {
        throw new SONParsingError("Invalid integer value.");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("Integer was not terminated.");
    }
  }

  static String doubleString(Double value) {
    return value.toString();
  }

  /**
   * Parses a Double from its SON specific String form.
   *
   * @param str is the sequence containing the Double.
   */
  static PartialParseResult<Double> partialParseDouble(String str, int i) throws SONParsingError {
    try {
      StringBuilder builder = new StringBuilder();
      char nextChar = str.charAt(i);
      while (nextChar == '-' || nextChar == '.' || nextChar == 'e' || nextChar == 'E'
          || nextChar >= '0' && nextChar <= '9' ) {
        builder.append(nextChar);
        nextChar = str.charAt(++i);
      }

      if (builder.toString().length() == 0) {
        Logger.error(str.substring(0, 20));
      }

      //create double from StringBuilder
      try {
        double value = Double.parseDouble(builder.toString());
        return new PartialParseResult<>(value, i);
      } catch (NumberFormatException e) {
        throw new SONParsingError("Invalid double value: " + builder.toString());
      }
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("Double was not terminated.");
    }
  }

  static String booleanString(Boolean value) {
    return value.toString();
  }

  /**
   * Parses a Boolean from its SON specific String form.
   *
   * @param str is the sequence containing the Boolean.
   */
  static PartialParseResult<Boolean> partialParseBoolean(String str, int i) throws SONParsingError {
    if (str.startsWith("true", i)) {
      return new PartialParseResult<>(true, i + 4);
    }

    if (str.startsWith("false", i)) {
      return new PartialParseResult<>(false, i + 5);
    }

    throw new SONParsingError("Invalid boolean value.");
  }

  static String stringString(String str) {
    return STRING_DELIMITER + escapeString(str) + STRING_DELIMITER;
  }

  /**
   * Parses a String from its SON specific String form.
   *
   * @param str is the sequence containing the Double.
   */
  static PartialParseResult<String> partialParseString(String str, int start)
      throws SONParsingError {
    try {
      if (str.charAt(start) != STRING_DELIMITER) {
        throw new SONParsingError("Expected a string but didn't find a string delimiter.");
      }

      //build string wait for DELIMITER and escape ESCAPE
      StringBuilder builder = new StringBuilder();
      int i = start + 1;
      char nextChar = str.charAt(i);
      while (nextChar != STRING_DELIMITER) {
        if (str.charAt(i) == STRING_ESCAPE) {
          nextChar = str.charAt(++i);
          if (nextChar != STRING_DELIMITER && nextChar != STRING_ESCAPE && nextChar != 'n') {
            throw new SONParsingError("Invalid escape character.");
          }
          if (nextChar == 'n') {
            builder.append('\n');
          } else {
            builder.append(nextChar);
          }
        } else {
          builder.append(nextChar);
        }
        nextChar = str.charAt(++i);
      }

      return new PartialParseResult<>(builder.toString(), i + 1);
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("String was not terminated.");
    }
  }

  private static String escapeString(String str) {
    return str
        .replace("" + STRING_ESCAPE, "" + STRING_ESCAPE + STRING_ESCAPE)
        .replace("" + STRING_DELIMITER, "" + STRING_ESCAPE + STRING_DELIMITER)
        .replace("\n", STRING_ESCAPE + "n");
  }

  static class PartialParseResult<T> {

    final T result;
    final int pos;

    PartialParseResult(T result, int pos) {
      this.result = result;
      this.pos = pos;
    }
  }
}
