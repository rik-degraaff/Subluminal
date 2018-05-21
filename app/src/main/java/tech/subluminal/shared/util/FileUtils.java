package tech.subluminal.shared.util;

public class FileUtils {

  public static String removeExtension(String file) {
    int i = file.lastIndexOf(".");
    if (i > 0) {
      return file.substring(0, i);
    }
    return file;
  }

  public static String getExtension(String file) {
    int i = file.lastIndexOf(".");
    if (i > 0 && i < file.length() - 1) {
      return file.substring(i + 1, file.length());
    }
    return "";
  }
}
