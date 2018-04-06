package tech.subluminal.server.stores;

import tech.subluminal.shared.records.Lobby;
import tech.subluminal.shared.stores.IdentifiableCollection;


/**
 * Saves server-side information about open lobbies.
 */
public class InMemoryLobbyStore implements LobbyStore {

  private LobbyCollection lobbies = new LobbyCollection();

  @Override
  public LobbyCollection lobbies() {
    return lobbies;
  }
}
