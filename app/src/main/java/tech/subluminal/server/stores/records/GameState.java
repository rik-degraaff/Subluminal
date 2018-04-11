package tech.subluminal.server.stores.records;

import java.util.Set;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Star;

/**
 * Represents the state of a game in progress.
 */
public class GameState extends Identifiable {

  private final double lightSpeed;
  private final Set<Star> stars;
  private final Set<Player> players;

  public GameState(String id, Set<Star> stars, Set<Player> players, double lightSpeed) {
    super(id);
    this.lightSpeed = lightSpeed;
    this.stars = stars;
    this.players = players;
  }

  /**
   * @return the light speed in units per second for this game.
   */
  public double getLightSpeed() {
    return lightSpeed;
  }

  /**
   * @return a set containing all stars in this game.
   */
  public Set<Star> getStars() {
    return stars;
  }

  /**
   * @return a set containing all players in this game.
   */
  public Set<Player> getPlayers() {
    return players;
  }
}
