package tech.subluminal.shared.son;

class SONParsing {

  private static final char STRING_DELIMITER = '"';
  private static final char STRING_ESCAPE = '\\';
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

  static class PartialParseResult<T> {

    final T result;
    final int pos;

    PartialParseResult(T result, int pos) {
      this.result = result;
      this.pos = pos;
    }
  }

  static String keyValueString(String key, Integer value) {
    return stringString(key) + KEY_VALUE_DELIMITER + INTEGER_ID + integerString(value);
  }

  static String keyValueString(String key, Double value) {
    return stringString(key) + KEY_VALUE_DELIMITER + DOUBLE_ID + doubleString(value);
  }

  static String keyValueString(String key, Boolean value) {
    return stringString(key) + KEY_VALUE_DELIMITER + BOOLEAN_ID + booleanString(value);
  }

  static String keyValueString(String key, String value) {
    return stringString(key) + KEY_VALUE_DELIMITER + STRING_ID + stringString(value);
  }

  static String keyValueString(String key, SON value) {
    return stringString(key) + KEY_VALUE_DELIMITER + OBJECT_ID + value.asString();
  }

  static String keyValueString(String key, SONList value) {
    return stringString(key) + KEY_VALUE_DELIMITER + LIST_ID + value.asString();
  }

  private static String integerString(Integer value) {
    return value.toString();
  }

  static PartialParseResult<Integer> partialParseInt(String str, int i) throws SONParsingError {
    try {
      StringBuilder builder = new StringBuilder();
      char nextChar = str.charAt(i);
      while (nextChar == '-' || nextChar >= '0' && nextChar <= '9') {
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

  private static String doubleString(Double value) {
    return value.toString();
  }

  static PartialParseResult<Double> partialParseDouble(String str, int i) throws SONParsingError {
    try {
      StringBuilder builder = new StringBuilder();
      char nextChar = str.charAt(i);
      while (nextChar == '-' || nextChar == '.' || nextChar == 'e' || nextChar == 'E'
          || nextChar >= '0' && nextChar <= '9') {
        builder.append(nextChar);
        nextChar = str.charAt(++i);
      }

      try {
        double value = Double.parseDouble(builder.toString());
        return new PartialParseResult<>(value, i);
      } catch (NumberFormatException e) {
        throw new SONParsingError("Invalid double value.");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("Double was not terminated.");
    }
  }

  private static String booleanString(Boolean value) {
    return value.toString();
  }

  static PartialParseResult<Boolean> partialParseBoolean(String str, int i) throws SONParsingError {
    if (str.startsWith("true", i)) {
      return new PartialParseResult<>(true, i + 4);
    }

    if (str.startsWith("false", i)) {
      return new PartialParseResult<>(false, i + 5);
    }

    throw new SONParsingError("Invalid boolean value.");
  }

  private static String stringString(String str) {
    return STRING_DELIMITER + escapeString(str) + STRING_DELIMITER;
  }

  static PartialParseResult<String> partialParseString(String str, int start)
      throws SONParsingError {
    try {
      if (str.charAt(start) != STRING_DELIMITER) {
        throw new SONParsingError("Expected a string but didn't find a string delimiter.");
      }

      StringBuilder builder = new StringBuilder();
      int i = start + 1;
      char nextChar = str.charAt(i);
      while (nextChar != STRING_DELIMITER) {
        if (str.charAt(i) == STRING_ESCAPE) {
          nextChar = str.charAt(++i);
          if (nextChar != STRING_DELIMITER && nextChar != STRING_ESCAPE) {
            throw new SONParsingError("Invalid escape character.");
          }
        }
        builder.append(nextChar);
        nextChar = str.charAt(++i);
      }

      return new PartialParseResult<>(builder.toString(), i + 1);
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("String was not terminated.");
    }
  }

  private static String escapeString(String str) {
    return str
        .replace("" + STRING_DELIMITER, "" + STRING_ESCAPE + STRING_DELIMITER)
        .replace("" + STRING_ESCAPE, "" + STRING_ESCAPE + STRING_ESCAPE);
  }
}
