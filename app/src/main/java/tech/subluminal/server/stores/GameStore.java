package tech.subluminal.server.stores;

import java.util.Optional;
import tech.subluminal.shared.records.game.Star;

public interface GameStore {

  Optional<Star> getStarByID(String id);

  void addStar(Star star);

  void updateStar(Star star);

  
}
