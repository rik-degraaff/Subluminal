package tech.subluminal.client.stores;

import java.util.List;
import javafx.collections.ObservableList;
import tech.subluminal.shared.stores.ReadOnlySingleEntity;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.SlimLobby;

public interface LobbyStore {

  ObservableList<SlimLobby> observableLobbies();

  SingleEntity<Lobby> currentLobby();

  ReadOnlySingleEntity<List<SlimLobby>> lobbies();
}
