package tech.subluminal.server.stores;

import java.util.HashMap;
import java.util.Map;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.logic.Lobby;


/**
 * Saves server-side information about open lobbies.
 */
public class InMemoryLobbyStore implements LobbyStore {

  private int maxLobbies = 10;
  private Map<String, Lobby> lobbyMap = new HashMap<>();

  /**
   * Add a lobby to the lobby store.
   * @param id is the id of the new lobby to add to the collection.
   */
  @Override
  public void add(String id, Lobby lobby) {

  }

  /**
   * Removes a lobby from the store.
   * @param id of the lobby to remove.
   */
  @Override
  public void destroy(String id) {

  }
}
