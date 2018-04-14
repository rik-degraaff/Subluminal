package tech.subluminal.client.stores;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.SlimLobby;

public class InMemoryLobbyStore implements LobbyStore {

  private final SingleEntity<Lobby> currentLobby = new SingleEntity<>();
  private final SingleEntity<ObservableList<SlimLobby>> lobbies = new SingleEntity<>();

  public InMemoryLobbyStore() {
    lobbies.set(FXCollections.observableArrayList());
  }

  @Override
  public ObservableList<SlimLobby> observableLobbies() {
    return lobbies.get().use(Optional::get);
  }

  @Override
  public SingleEntity<Lobby> currentLobby() {
    return currentLobby;
  }

  @Override
  public SingleEntity<List<SlimLobby>> lobbies() {
    //TODO: Implement Lobby GETTER
    SingleEntity<List<SlimLobby>> temp = new LinkedList<>();

    return lobbies.get().map(l -> (ObservableList<SlimLobby>) l);
  }
}
