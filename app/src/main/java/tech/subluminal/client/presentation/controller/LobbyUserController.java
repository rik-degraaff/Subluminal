package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.presentation.LobbyPresenter.Delegate;
import tech.subluminal.client.presentation.customElements.PlayerStatusComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.PlayerStatus;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.util.MapperList;


public class LobbyUserController implements Initializable {

  @FXML
  private AnchorPane lobbyHost;

  @FXML
  private AnchorPane playerList;

  @FXML
  private AnchorPane lobbySettings;

  @FXML
  private ListView<PlayerStatusComponent> userList;

  private UserStore userStore;

  private LobbyStore lobbyStore;

  private LobbyPresenter.Delegate lobbyDelegate;

  private FilteredList<User> filterdUsers;
  private MainController main;

  public void setLobbyDelegate(Delegate lobbyDelegate) {
    this.lobbyDelegate = lobbyDelegate;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //userList.getItems().add(new PlayerStatusComponent("tester", PlayerStatus.INLOBBY));
  }

  public void setLobbyStore(LobbyStore lobbyStore) {
    this.lobbyStore = lobbyStore;
  }

  /**
   * Sets the user store in this controller.
   * @param userStore to be attached to controller.
   */
  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    Logger.trace("LOBBYUSERVIEW GOT UPDATED");
    this.filterdUsers = new FilteredList<>(userStore.users().observableList());
    filterdUsers.setPredicate(u -> {
      Logger.trace("LOBBYUSERVIEW GOT UPDATED");
      return lobbyStore.currentLobby().get()
          .use(opt -> opt.map(l -> l.getPlayers().contains(u.getID())).orElse(false));

    });

    Platform.runLater(() -> {
      userList.setItems(new MapperList<>(filterdUsers,
          u -> new PlayerStatusComponent(u.getUsername(), PlayerStatus.INLOBBY, main, false)));
    });


  }

  @FXML
  private void onLobbyLeave() {
    lobbyDelegate.leaveLobby();
  }

  public void setMainController (MainController main ){
    this.main = main;
  }

  /**
   * Updates the playerlist in the current lobby.
   */
  public void lobbyUpdateReceived() {
    Logger.trace("LobbyUpdage got received here, watch out!");
    Platform.runLater(() -> {
      filterdUsers.setPredicate(u -> {
        Logger.trace("LOBBYUSERVIEW GOT UPDATED");
        return lobbyStore.currentLobby().get()
            .use(opt -> opt.map(l -> l.getPlayers().contains(u.getID())).orElse(false));

      });
    });
  }

  @FXML
  private void onGameStart() {
    lobbyDelegate.startGame();
  }

}
