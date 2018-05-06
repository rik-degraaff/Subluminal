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

  public GameHistory<Ship> getMotherShip() {
    return motherShip;
  }

  public Map<String, GameHistory<Fleet>> getFleets() {
    return fleets;
  }

  public void updateFleet(Fleet fleet) {
    GameHistory<Fleet> fleetGameHistory = getFleets().get(fleet.getID());
    if (fleetGameHistory == null) {
      getFleets().put(fleet.getID(),
          new GameHistory<>(playerIDs, new GameHistoryEntry<>(fleet), lightSpeed));
    } else {
      fleetGameHistory.add(new GameHistoryEntry<>(fleet));
    }
  }

  public boolean isAlive() {
    return alive;
  }

  public void kill() {
    alive = false;
  }

  public boolean hasLeft() {
    return hasLeft;
  }

  public void leave() {
    hasLeft = true;
  }
}
