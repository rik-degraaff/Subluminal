package tech.subluminal.shared.son;

class SONParsing {
  private static final char STRING_DELIMITER = '"';
  private static final char STRING_ESCAPE = '\\';
  private static final char KEY_VALUE_DELIMITER = ':';
  static final char ENTRY_DELIMITER = ',';

  static final char OBJECT_LEFTBRACE = '{';
  static final char OBJECT_RIGHTBRACE = '}';
  static final char LIST_LEFTBRACE = '[';
  static final char LIST_RIGHTBRACE = ']';

  private static final char INTEGER_ID = 'i';
  private static final char DOUBLE_ID = 'd';
  private static final char BOOLEAN_ID = 'b';
  private static final char STRING_ID = 's';
  private static final char OBJECT_ID = 'o';
  private static final char LIST_ID = 'l';

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

  private static String booleanString(Boolean value) {
    return value.toString();
  }

  private static String doubleString(Double value) {
    return value.toString();
  }

  private static String stringString(String str) {
    return STRING_DELIMITER + escapeString(str) + STRING_DELIMITER;
  }

  private static String integerString(Integer value) {
    return value.toString();
  }

  private static String escapeString(String str) {
    return str.replace("" + STRING_DELIMITER, "" + STRING_ESCAPE + STRING_DELIMITER);
  }
}
