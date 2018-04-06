package tech.subluminal.shared.stores.records.game;

import java.util.List;

public class Ship extends Movable {
  public Ship(Coordinates coordinates, String id, List<String> targetIDs) {
    super(coordinates, id, targetIDs);
  }
}
