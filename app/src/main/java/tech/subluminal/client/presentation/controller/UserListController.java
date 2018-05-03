package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.presentation.customElements.PlayerStatusComponent;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.PlayerStatus;
import tech.subluminal.shared.util.MapperList;


public class UserListController implements Initializable, UserPresenter {

  @FXML
  private ListView<PlayerStatusComponent> playerBoard;
  @FXML
  private VBox playerBoardWrapper;
  @FXML
  private Button handlePlayerBoard;

  private LinkedList<Label> players;

  private boolean isBoardShown = false;
  private UserStore userStore;

  private UserPresenter.Delegate userDelegate;
  private MainController main;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (!isBoardShown) {
      playerBoard.setVisible(false);
    }

  }


  public void switchPlayerBoard(ActionEvent actionEvent) {
    if (isBoardShown) {
      playerBoard.setVisible(false);
      playerBoard.setMouseTransparent(true);
      isBoardShown = false;
      handlePlayerBoard.setText("P");
    } else {
      playerBoard.setVisible(true);
      playerBoard.setMouseTransparent(false);
      isBoardShown = true;
      handlePlayerBoard.setText("X");
    }

  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    Platform.runLater(() -> {
      playerBoard.setItems(new MapperList<>(userStore.users().observableList(),
          user -> new PlayerStatusComponent(user.getUsername(), PlayerStatus.INGAME, main)));
    });

  }

  public void setMainController(MainController main){
    this.main = main;
  }

  @Override
  public void loginSucceeded() {

  }

  @Override
  public void logoutSucceeded() {

  }

  @Override
  public void nameChangeSucceeded() {

  }

  @Override
  public void setUserDelegate(Delegate delegate) {

  }

  @Override
  public void onPlayerJoin(String username) {

  }

  @Override
  public void onPlayerLeave(String username) {

  }

  @Override
  public void onPlayerUpdate(String oldUsername, String newUsername) {

  }

  public UserListController getController() {
    return this;
  }

}
