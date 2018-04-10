package tech.subluminal.shared.records;

import javafx.scene.paint.Color;
import org.pmw.tinylog.Logger;

public enum LobbyStatus {
  OPEN, FULL, LOCKED;

  public Color getColor() {
    Color color = Color.GRAY;
    switch (this) {
      case FULL:
        color = Color.RED;
        break;
      case OPEN:
        color = Color.GREEN;
        break;
      case LOCKED:
        color = Color.GRAY;
        break;
      default:
        System.err.println("Lobby status does not exist!");
        Logger.error("Lobby status does not exist!");

    }

    return color;
  }
}
