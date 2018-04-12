package tech.subluminal.client.stores;

import java.util.Collection;
import java.util.Optional;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

/**
 * The game store keeps the latest game state and is updated constantly.
 */
public interface GameStore {

  /**
   * @return a collection containing all stars of a game.
   */
  Synchronized<Collection<Synchronized<Star>>> getStars();

  /**
   * Returns a specific requested star or empty if the star does not exist.
   *
   * @param id the ID of a specific star.
   * @return the star matching the ID or empty, if the ID matches no star in this game.
   */
  Optional<Synchronized<Star>> getStarByID(String id);

  /**
   * Adds a star to a game.
   *
   * @param star the star to be added to the game.
   */
  void addStar(Star star);

  /**
   * Returns a collection of all players in this game.
   *
   * @return a collection containing all player of the current game.
   */
  Synchronized<Collection<Synchronized<Player>>> getPlayers();

  /**
   * Returns a specific requested player or empty if the player does not exist.
   *
   * @param id the ID of a specific player.
   * @return the player matching the ID or empty, if the ID matches no player in this game.
   */
  Optional<Synchronized<Player>> getPlayerByID(String id);

  /**
   * Adds a player to a game.
   *
   * @param player the player to be added to the game.
   */
  void addPlayer(Player player);
}
