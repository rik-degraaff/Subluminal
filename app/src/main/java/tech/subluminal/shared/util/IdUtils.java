package tech.subluminal.shared.util;

import java.util.UUID;

public class IdUtils {

  /**
   * Generates a random string of case sensitive alphanumeric characters.
   *
   * @param length the length of the generated string.
   * @return the generated string.
   */
  public static String generateId(int length) {
    return UUID.randomUUID().toString().replace("-", "").substring(0, length);
  }
}
