package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.ReadOnlySingleEntity;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.SlimLobby;

import java.util.List;

public interface LobbyStore {

    ReadOnlySingleEntity<Lobby> currentLobby();

    ReadOnlySingleEntity<List<SlimLobby>> lobbies();
}
