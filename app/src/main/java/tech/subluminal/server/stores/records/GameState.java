package tech.subluminal.server.stores.records;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import tech.subluminal.server.logic.game.GameHistory;
import tech.subluminal.server.logic.game.GameHistoryEntry;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Star;

/**
 * Represents the state of a game in progress.
 */
public class GameState extends Identifiable {

  private final double lightSpeed;
  private final Set<GameHistory<Star>> stars;
  private final Set<Player> players;
  private final PriorityQueue<Signal> signals;

  public GameState(String id, Set<Star> stars, Set<Player> players, double lightSpeed) {
    super(id);
    this.lightSpeed = lightSpeed;
    this.players = players;

    this.signals = new PriorityQueue<>(Comparator.reverseOrder());

    Set<String> playerIDs = players.stream()
        .map(Player::getID)
        .collect(Collectors.toSet());

    this.stars = stars.stream()
        .map(GameHistoryEntry::foreverAgo)
        .map(s -> new GameHistory<>(playerIDs, s, lightSpeed))
        .collect(Collectors.toSet());
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
  public Set<GameHistory<Star>> getStars() {
    return stars;
  }

  /**
   * @return a set containing all players in this game.
   */
  public Set<Player> getPlayers() {
    return players;
  }

  /**
   * @return all the unprocessed signals in this gameState.
   */
  public PriorityQueue<Signal> getSignals() {
    return signals;
  }
}
