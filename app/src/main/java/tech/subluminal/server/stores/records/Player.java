package tech.subluminal.server.stores.records;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tech.subluminal.server.logic.game.GameHistory;
import tech.subluminal.server.logic.game.GameHistoryEntry;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

public class Player extends Identifiable {

  private final double lightSpeed;
  private final Set<String> playerIDs = new HashSet<>();
  private final GameHistory<Ship> motherShip;
  private final List<GameHistory<Fleet>> fleets = new ArrayList<>();

  public Player(String id, Set<String> otherPlayerIDs, Ship motherShip, double lightSpeed) {
    super(id);

    this.lightSpeed = lightSpeed;
    playerIDs.addAll(otherPlayerIDs);
    otherPlayerIDs.add(id);

    this.motherShip = new GameHistory<>(otherPlayerIDs, new GameHistoryEntry<>(motherShip), lightSpeed);
  }

  public GameHistory<Ship> getMotherShip() {
    return motherShip;
  }

  public List<GameHistory<Fleet>> getFleets() {
    return fleets;
  }
}
