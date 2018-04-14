package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tech.subluminal.client.presentation.customElements.LobbyStatusComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.util.MapperList;

public class LobbyListController implements Initializable, Observer {

  @FXML
  private ListView<LobbyStatusComponent> lobbyList;

  private MainController main;

  public void setLobbyStore(LobbyStore store) {
    lobbyList.setItems(new MapperList<>(store.observableLobbies(),
        lobby -> new LobbyStatusComponent(lobby.getName(), lobby.getID(), lobby.getPlayerCount(),
            lobby.getMaxPlayers(), LobbyStatus.OPEN)));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    lobbyList.getItems()
        .add(new LobbyStatusComponent("testLobby", "wefwef", 3, 8, LobbyStatus.OPEN));
    lobbyList.getItems().forEach(i -> i.lobbyToJoinProperty().addListener(event -> {
      main.getLobby().onLobbyJoin(i.lobbyToJoinProperty().getValue());
    }));
  }

  @FXML
  private void onLobbyCreate() {
    main.onLobbyCreateHandle();
  }


  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }

}
