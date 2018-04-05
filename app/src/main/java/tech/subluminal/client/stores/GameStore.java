package tech.subluminal.client.stores;

import java.util.Optional;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

public interface GameStore {

  Optional<Synchronized<Star>> getStarByID(String id);

  void addStar(Star star);

  void addPlayer(Player player);

  Optional<Synchronized<Player>> getPlayerByID();



}
