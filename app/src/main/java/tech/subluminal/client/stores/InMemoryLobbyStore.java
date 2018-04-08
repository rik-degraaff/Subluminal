package tech.subluminal.client.stores;

import java.util.List;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.SingleEntity;
import tech.subluminal.shared.stores.records.SlimLobby;

public class InMemoryLobbyStore implements LobbyStore {

  private final SingleEntity<Lobby> currentLobby = new SingleEntity<>();
  private final SingleEntity<List<SlimLobby>> lobbies = new SingleEntity<>();

  @Override
  public SingleEntity<Lobby> currentLobby() {
    return currentLobby;
  }

  @Override
  public SingleEntity<List<SlimLobby>> lobbies() {
    return lobbies;
  }
}
