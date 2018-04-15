package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.util.Duration;
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
        lobby -> new LobbyStatusComponent(lobby.getSettings().getName(), lobby.getID(),
            lobby.getSettings().getPlayerCount(), lobby.getSettings().getMaxPlayers(),
            LobbyStatus.OPEN)));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    openWindow();

    //lobbyList.getItems()
    //    .add(new LobbyStatusComponent("testLobby", "wefwef", 3, 8, LobbyStatus.OPEN));

  }

  public void openWindow() {
    ScaleTransition scaleTlX = new ScaleTransition(Duration.seconds(0.2), lobbyList.getParent());
    scaleTlX.setFromX(0);
    scaleTlX.setToX(1);

    ScaleTransition scaleTlY = new ScaleTransition(Duration.seconds(0.5), lobbyList.getParent());
    scaleTlY.setFromY(0);
    scaleTlY.setToY(1);

    ParallelTransition paraTl = new ParallelTransition();

    paraTl.getChildren().addAll(scaleTlX, scaleTlY);
    paraTl.play();
  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }

}
