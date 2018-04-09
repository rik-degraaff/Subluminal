package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.ReadOnlySingleEntity;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.SlimLobby;

import java.util.List;

public class InMemoryLobbyStore implements LobbyStore {

    private final ReadOnlySingleEntity<Lobby> currentLobby = new SingleEntity<>();
    private final ReadOnlySingleEntity<List<SlimLobby>> lobbies = new SingleEntity<>();

    @Override
    public ReadOnlySingleEntity<Lobby> currentLobby() {
        return currentLobby;
    }

    @Override
    public ReadOnlySingleEntity<List<SlimLobby>> lobbies() {
        return lobbies;
    }
}
