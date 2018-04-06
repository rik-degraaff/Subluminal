package tech.subluminal.client.stores;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

public class InMemoryGameStore implements GameStore {
  private Synchronized<Map<String, Synchronized<Star>>> starMap;
  private Synchronized<Map<String, Synchronized<Player>>> playerMap;

  @Override
  public Synchronized<Collection<Synchronized<Star>>> getStars() {
    return starMap.map(Map::values);
  }

  @Override
  public Optional<Synchronized<Star>> getStarByID(String id) {
    return Optional.ofNullable(starMap.use(map -> map.get(id)));
  }

  @Override
  public void addStar(Star star) {
    starMap.use(map -> map.put(star.getID(), new Synchronized<>(star)));
  }

  @Override
  public Synchronized<Collection<Synchronized<Player>>> getPlayers() {
    return playerMap.map(Map::values);
  }

  @Override
  public Optional<Synchronized<Player>> getPlayerByID(String id) {
    return Optional.ofNullable(playerMap.use(map -> map.get(id)));
  }

  @Override
  public void addPlayer(Player player) {
    playerMap.use(map -> map.put(player.getID(), new Synchronized<>(player)));
  }
}
