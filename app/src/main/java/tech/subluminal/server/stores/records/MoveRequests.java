package tech.subluminal.server.stores.records;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tech.subluminal.shared.messages.MoveReq;
import tech.subluminal.shared.stores.records.Identifiable;

public class MoveRequests extends Identifiable {

  private Map<String, List<MoveReq>> requests = new HashMap<>();

  public MoveRequests(String id) {
    super(id);
  }

  /**
   * Adds a move request to for a specified player.
   *
   * @param playerID the id of the player that made the request.
   * @param request the request to move the mother ship or a fleet.
   */
  public void add(String playerID, MoveReq request) {
    List<MoveReq> playerRequests = requests.get(playerID);
    if (playerRequests == null) {
      playerRequests = new LinkedList<>();
      requests.put(playerID, playerRequests);
    }
    playerRequests.add(request);
  }

  /**
   * Reads and removes all movement requests made by a player.
   *
   * @param playerID the player whose request will be read.
   * @return all requests made by the player.
   */
  public List<MoveReq> getAndRemoveForPlayer(String playerID) {
    List<MoveReq> playerRequests = requests.get(playerID);
    requests.remove(playerID);
    return playerRequests != null ? playerRequests : Collections.emptyList();
  }
}
