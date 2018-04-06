package tech.subluminal.server.stores;

import tech.subluminal.shared.records.Lobby;

/**
 * Saves server-side information about open lobbies.
 */
public interface LobbyStore {

  /**
   * Add a lobby to the lobby store
   * @param id
   */
  void add(String id, Lobby lobby);


  void destroy(String id);

}
