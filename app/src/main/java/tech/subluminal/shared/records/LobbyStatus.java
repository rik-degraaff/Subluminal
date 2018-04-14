package tech.subluminal.shared.records;

import javafx.scene.paint.Color;
import org.pmw.tinylog.Logger;

public enum LobbyStatus {

  OPEN(Color.GREEN), FULL(Color.RED), LOCKED(Color.GRAY);

  private final Color color;

  LobbyStatus(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }
}
