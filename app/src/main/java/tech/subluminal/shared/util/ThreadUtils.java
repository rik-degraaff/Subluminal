package tech.subluminal.shared.util;

import javafx.application.Platform;

public class ThreadUtils {

  private static boolean isFirstCall = true;
  private static boolean isJavaFX = false;

  public static void runSafly(Runnable runnable) {
    if (isFirstCall) {
      checkForJavaFX();
    }

    if (isJavaFX) {
      Platform.runLater(runnable);
    } else {
      runnable.run();
    }
  }

  private static void checkForJavaFX() {
    try {
      Platform.runLater(() -> {
      });
      isJavaFX = true;
    } catch (IllegalStateException e) {
      isJavaFX = false;
    }
  }
}
