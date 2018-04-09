package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.SingleEntity;
import tech.subluminal.shared.stores.records.SlimLobby;

import java.util.List;

public interface LobbyStore {

    SingleEntity<Lobby> currentLobby();

    SingleEntity<List<SlimLobby>> lobbies();
}
