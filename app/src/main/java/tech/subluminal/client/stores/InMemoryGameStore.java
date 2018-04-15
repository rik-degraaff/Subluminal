package tech.subluminal.client.stores;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.StoredSynchronized;
import tech.subluminal.shared.util.Synchronized;

/**
 * Keeps the current state of a game in a map of stars and a map of players.
 */
public class InMemoryGameStore implements GameStore {

  private Synchronized<Map<String, Synchronized<Star>>> starMap;
  private Synchronized<Map<String, Synchronized<Player>>> playerMap;

  /**
   * @return a collection containing all stars of a game.
   */
  @Override
  public Synchronized<Collection<Synchronized<Star>>> getStars() {
    return starMap.map(Map::values);
  }

  /**
   * Returns a specific requested star or empty if the star does not exist.
   *
   * @param id the ID of a specific star.
   * @return the star matching the ID or empty, if the ID matches no star in this game.
   */
  @Override
  public Optional<Synchronized<Star>> getStarByID(String id) {
    return Optional.ofNullable(starMap.use(map -> map.get(id)));
  }

  /**
   * Adds a star to a game.
   *
   * @param star the star to be added to the game.
   */
  @Override
  public void addStar(Star star) {
    starMap.use(map -> map.put(star.getID(), new StoredSynchronized<>(star)));
  }

  /**
   * Returns a collection of all players in this game.
   *
   * @return a collection containing all player of the current game.
   */
  @Override
  public Synchronized<Collection<Synchronized<Player>>> getPlayers() {
    return playerMap.map(Map::values);
  }

  /**
   * Returns a specific requested player or empty if the player does not exist.
   *
   * @param id the ID of a specific player.
   * @return the player matching the ID or empty, if the ID matches no player in this game.
   */
  @Override
  public Optional<Synchronized<Player>> getPlayerByID(String id) {
    return Optional.ofNullable(playerMap.use(map -> map.get(id)));
  }

  /**
   * Adds a player to a game.
   *
   * @param player the player to be added to the game.
   */
  @Override
  public void addPlayer(Player player) {
    playerMap.use(map -> map.put(player.getID(), new StoredSynchronized<>(player)));
  }
}
