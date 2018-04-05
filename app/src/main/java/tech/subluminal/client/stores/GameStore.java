package tech.subluminal.client.stores;

import java.util.Optional;
import tech.subluminal.shared.stores.records.game.Star;

public interface GameStore {

  Optional<Star> getStarByID(String id);

  void addStar(Star star);

  void updateStar(Star star);

  void addPlayer(Player player);

  void updatePlayer(Player player);


}
