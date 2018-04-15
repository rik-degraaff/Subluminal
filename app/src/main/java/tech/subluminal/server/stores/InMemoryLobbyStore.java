package tech.subluminal.server.stores;


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
