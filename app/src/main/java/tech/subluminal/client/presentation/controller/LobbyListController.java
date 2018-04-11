package tech.subluminal.client.presentation.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import tech.subluminal.client.presentation.customElements.LobbyStatusComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.records.LobbyStatus;

import java.net.URL;
import java.util.ResourceBundle;
import tech.subluminal.shared.stores.records.SlimLobby;
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

  @FXML
  public void closePressed() {
    main.onLobbyListClose();
  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }

}
