package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.presentation.customElements.PlayerStatusComponent;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.PlayerStatus;
import tech.subluminal.shared.util.MapperList;


public class UserListController implements Initializable, UserPresenter {

  @FXML
  private ListView<PlayerStatusComponent> playerBoard;

  @FXML
  private AnchorPane playerList;

  private LinkedList<Label> players;

  private UserStore userStore;

  private UserPresenter.Delegate userDelegate;
  private MainController main;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }


  /**
   * Set the user store in this controller.
   * @param userStore the user store to be set.
   */
  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    Platform.runLater(() -> {
      playerBoard.setItems(new MapperList<>(userStore.users().observableList(),
          user -> new PlayerStatusComponent(user.getUsername(), PlayerStatus.INGAME, main)));

      playerBoard.maxHeightProperty().bind(playerList.heightProperty());
      playerBoard.maxWidthProperty().bind(playerList.widthProperty());
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
