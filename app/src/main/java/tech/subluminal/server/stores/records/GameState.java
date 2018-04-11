package tech.subluminal.server.stores.records;

import java.util.Set;
import tech.subluminal.shared.stores.records.Identifiable;

public class GameState extends Identifiable {

  public GameState(String id, Set<String> players) {
    super(id);
  }
}
