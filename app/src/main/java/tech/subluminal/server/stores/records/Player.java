package tech.subluminal.server.stores.records;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.pmw.tinylog.Logger;
import tech.subluminal.server.logic.game.GameHistory;
import tech.subluminal.server.logic.game.GameHistoryEntry;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

/**
 * Represents a player in a game with all fleets and a mother ship.
 */
public class Player extends Identifiable {

  private final double lightSpeed;
  private final Set<String> playerIDs = new HashSet<>();
  private final GameHistory<Ship> motherShip;
  private final Map<String, GameHistory<Fleet>> fleets = new HashMap<>();
  private boolean alive;
  private boolean hasLeft;

  public Player(
      String id, Set<String> otherPlayerIDs, Ship motherShip, double lightSpeed, boolean alive,
      boolean hasLeft
  ) {
    super(id);

    this.lightSpeed = lightSpeed;
    this.alive = alive;
    this.hasLeft = hasLeft;

    playerIDs.addAll(otherPlayerIDs);
    playerIDs.add(id);

    this.motherShip = new GameHistory<>(otherPlayerIDs, GameHistoryEntry.foreverAgo(motherShip),
        lightSpeed);
    this.motherShip.add(new GameHistoryEntry<>(motherShip));
  }

  /**
   * @return the game history with all past mother ship states.
   */
  public GameHistory<Ship> getMotherShip() {
    return motherShip;
  }

  /**
   * @return a collection of game histories with all past fleet states.
   */
  public Map<String, GameHistory<Fleet>> getFleets() {
    return fleets;
  }

  /**
   * Adds or updates a fleet for this player, creates a new game history if necessary.
   *
   * @param fleet the fleet to add or update.
   */
  public void updateFleet(Fleet fleet) {
    GameHistory<Fleet> fleetGameHistory = getFleets().get(fleet.getID());
    if (fleetGameHistory == null) {
      getFleets().put(fleet.getID(),
          new GameHistory<>(playerIDs, new GameHistoryEntry<>(fleet), lightSpeed));
    } else {
      fleetGameHistory.add(new GameHistoryEntry<>(fleet));
    }
  }

  /**
   * @return true is the player is still contending in the game.
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Sets the alive status of the player to false.
   */
  public void kill() {
    alive = false;
  }

  /**
   * @return true if the player has left the game.
   */
  public boolean hasLeft() {
    return hasLeft;
  }

  /**
   * Sets the hasLeft state of the player to true.
   */
  public void leave() {
    hasLeft = true;
  }

  /**
   * Sets the hasLeft state of the player to false.
   */
  public void join() {
    hasLeft = false;
  }
}
