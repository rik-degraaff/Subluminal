package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;
import tech.subluminal.server.stores.InMemoryLobbyStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.logic.Lobby;

/**
 * Manages the chat/game lobbies.
 */
public class LobbyManager {

  InMemoryLobbyStore lobbyStore;

  public LobbyManager() {
    this.lobbyStore = new InMemoryLobbyStore();
  }

  /**
   * Creates a new lobby and adds it to the
   * @param name is the common name of the lobby.
   * @param adminID references the user who created the lobby.
   */
  public void createLobby(String name, String adminID) {
    String id = generateId(6);
    lobbyStore.add(new Lobby(id, name, adminID));
  }

  /**
   * Removes a lobby from the lobby store and removes all player association.
   * @param id is the lobby to be destroyed.
   */
  public void destroyLobby(String id) {
    lobbyStore.destroy(id);
  }

  /**
   * Lists all open lobbies on this server.
   * @return array of open lobbies
   */
  public Lobby[] getOpenLobbies() {
    Lobby[] lobbies;
    //TODO: for earch lobby in lobbyStore {lobbies.append(lobby)}

    return lobbies;
  }
}
