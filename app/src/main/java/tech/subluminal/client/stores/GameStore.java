package tech.subluminal.client.stores;

import java.util.Collection;
import java.util.Optional;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

public interface GameStore {

  Synchronized<Collection<Synchronized<Star>>> getStars();

  Optional<Synchronized<Star>> getStarByID(String id);

  void addStar(Star star);

  Synchronized<Collection<Synchronized<Player>>> getPlayers();

  Optional<Synchronized<Player>> getPlayerByID(String id);

  void addPlayer(Player player);
}
