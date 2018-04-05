package tech.subluminal.shared.messages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.game.Star;

public class GameStateDelta implements SONRepresentable {
  private List<Player> players = new LinkedList<>();
  private List<Star> stars = new LinkedList<>();
  private List<String> removedPlayers = new LinkedList<>();
  private Map<String, List<String>> removedFleets = new HashMap<>();

  public List<Player> getPlayers() {
    return players;
  }

  public List<Star> getStars() {
    return stars;
  }

  public List<String> getRemovedPlayers() {
    return removedPlayers;
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void addStar(Star star) {
    stars.add(star);
  }

  public void addRemovedPlayer(String id) {
    removedPlayers.add(id);
  }

  public Map<String, List<String>> getRemovedFleets() {
    return removedFleets;
  }

  public void addRemovedFleet(String playerID, String fleetID) {
    List<String> fleets = removedFleets.get(playerID);
    if (fleets == null) {
      fleets = new LinkedList<>();
      removedFleets.put(playerID, fleets);
    }
    fleets.add(fleetID);
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return null;
  }

  public static GameStateDelta fromSON(SON son) {

  }
}
