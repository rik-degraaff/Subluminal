package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.presentation.LobbyPresenter.Delegate;
import tech.subluminal.client.presentation.customElements.LobbyStatusComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.stores.records.SlimLobby;
import tech.subluminal.shared.util.MapperList;

public class LobbyListController implements Initializable {

  private final ListProperty<SlimLobby> slimLobbies = new SimpleListProperty<>();
  @FXML
  private ListView<LobbyStatusComponent> lobbyList;

  @FXML
  private TextField lobbyName;

  private LobbyPresenter.Delegate lobbyDelegate;

  public void setLobbyDelegate(Delegate lobbyDelegate) {
    this.lobbyDelegate = lobbyDelegate;
  }

  public ObservableList<SlimLobby> getSlimLobbies() {
    return slimLobbies.get();
  }

  public void setSlimLobbies(ObservableList<SlimLobby> slimLobbies) {
    this.slimLobbies.set(slimLobbies);
  }

  public ListProperty<SlimLobby> slimLobbiesProperty() {
    return slimLobbies;
  }

  public void setLobbyStore(LobbyStore store) {
    Logger.trace("Setting lobbystore of lobbyListComponent");
    lobbyList.setItems(new MapperList<>(store.observableLobbies(),
        lobby -> new LobbyStatusComponent(lobby.getSettings().getName(), lobby.getID(),
            lobby.getPlayerCount(), lobby.getSettings().getMaxPlayers(),
            lobby.getStatus(), lobbyDelegate)));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    lobbyList.getItems()
        .forEach(l -> l.lobbyToJoinProperty().addListener(e -> Logger.trace("help me !!!")));
  }

  @FXML
  private void onLobbyCreate() {
    Logger.trace("Creating lobby");
    String name = lobbyName.getText();
    if (name.equals("")){
      name = "404 lobby not found...";
    }
    lobbyDelegate.createLobby(name);

  }

  @FXML
  private void onLobbyRefresh() {
    lobbyDelegate.getLobbyList();
  }


}
