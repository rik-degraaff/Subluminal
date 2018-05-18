package tech.subluminal.server.stores.records;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import tech.subluminal.server.logic.game.GameHistory;
import tech.subluminal.server.logic.game.GameHistoryEntry;
import tech.subluminal.shared.stores.records.Identifiable;

/**
 * Represents the state of a game in progress.
 */
public class GameState extends Identifiable {

  private final double lightSpeed;
  private final double jump;
  private final double shipSpeed;
  private final Map<String, GameHistory<Star>> stars;
  private final Map<String, Player> players;
  private Set<Signal> signals = new HashSet<>();
  private final Set<String> spectators = new HashSet<>();

  public GameState(String id, Set<Star> stars, Set<Player> players, double lightSpeed, double jump,
      double shipSpeed) {
    super(id);
    this.lightSpeed = lightSpeed;
    this.jump = jump;
    this.shipSpeed = shipSpeed;

    this.players = players.stream().collect(Collectors.toMap(Player::getID, p -> p));

    Set<String> playerIDs = players.stream()
        .map(Player::getID)
        .collect(Collectors.toSet());

    this.stars = stars.stream()
        .map(GameHistoryEntry::foreverAgo)
        .map(s -> new GameHistory<>(playerIDs, s, lightSpeed))
        .collect(Collectors.toMap(h -> h.getCurrent().getState().getID(), h -> h));
  }

  /**
   * @return the light speed in units per second for this game.
   */
  public double getLightSpeed() {
    return lightSpeed;
  }

  /**
   * @return the ship speed in units per second for this game.
   */
  public double getShipSpeed() {
    return shipSpeed;
  }

  /**
   * @return the jump distance in units for this game.
   */
  public double getJump() {
    return jump;
  }

  /**
   * @return a set containing all stars in this game.
   */
  public Map<String, GameHistory<Star>> getStars() {
    return stars;
  }

  /**
   * @return a set containing all players in this game.
   */
  public Map<String, Player> getPlayers() {
    return players;
  }

  /**
   * @return all the unprocessed signals in this gameState.
   */
  public Set<Signal> getSignals() {
    return signals;
  }

  /**
   * @param signals the signals for this gamestate.
   */
  public void setSignals(Set<Signal> signals) {
    this.signals = signals;
  }

  /**
   * @return all spectators currently in this game.
   */
  public Set<String> getSpectators() {
    return spectators;
  }
}
