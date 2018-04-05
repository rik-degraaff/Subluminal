package tech.subluminal.shared.stores.records.game;

import java.util.List;

public abstract class Movable extends GameObject {

  private List<String> targetIDs;

  public Movable(Coordinates coordinates, String id, List<String> targetIDs) {
    super(coordinates, id);
    this.targetIDs = targetIDs;
  }

  public List<String> getTargetIDs() {
    return targetIDs;
  }

  public void setTargetIDs(List<String> targetIDs) {
    this.targetIDs = targetIDs;
  }
}
