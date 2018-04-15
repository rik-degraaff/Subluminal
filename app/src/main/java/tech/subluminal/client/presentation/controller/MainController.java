package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.GameComponent;
import tech.subluminal.client.presentation.customElements.LobbyComponent;
import tech.subluminal.client.presentation.customElements.LobbyListComponent;
import tech.subluminal.client.presentation.customElements.LobbyUserComponent;
import tech.subluminal.client.presentation.customElements.MenuComponent;
import tech.subluminal.client.presentation.customElements.SettingsComponent;
import tech.subluminal.client.presentation.customElements.WindowContainerComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;

public class MainController implements Initializable {

  @FXML
  private Parent chatView;
  @FXML
  private ChatController chatViewController;

  @FXML
  private Parent userListView;
  @FXML
  private UserListController userListViewController;

  @FXML
  private AnchorPane spaceBackgroundDock;

  @FXML
  private AnchorPane chatDock;

  @FXML
  private AnchorPane menuDock;

  @FXML
  private AnchorPane playArea;

  private BackgroundComponent background;

  private MenuComponent menu;

  private LobbyListComponent lobbyList;

  private SettingsComponent settings;

  private LobbyUserComponent lobbyUser;

  private LobbyComponent lobby;

  @FXML
  private WindowContainerComponent windowContainer;

  @FXML
  private AnchorPane window;

  private GameComponent game;

  private UserStore userStore;

  private LobbyStore lobbyStore;

  public LobbyComponent getLobby() {
    return lobby;
  }

  public void setLobbyStore(LobbyStore lobbyStore) {
    this.lobbyStore = lobbyStore;
  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    userListViewController.setUserStore(userStore);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background = new BackgroundComponent(200);
    spaceBackgroundDock.getChildren().add(background);

    menu = new MenuComponent(this);
    settings = new SettingsComponent(this);
    lobby = new LobbyComponent();

    game = new GameComponent(this);

    playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu); //TODO: reactivate this
    //onMapOpenHandle();
  }

  public MainController getController() {
    return this;
  }

  public ChatController getChatController() {
    return this.chatViewController;
  }

  public UserListController getUserListController() {
    return this.userListViewController;
  }

  public void onWindowResizeHandle(int diffX, int diffY) {
    background.onWindowResize(diffX, diffY);
  }

  public void setLobby(LobbyComponent lobby) {
    this.lobby = lobby;
  }

  public void onLobbyOpenHandle() {
    menuDock.getChildren().clear();

    windowContainer = new WindowContainerComponent(this, lobby, "Lobbies");
    //lobby.setUserActive();

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onSettingOpenHandle() {
    menuDock.getChildren().remove(menu);

    windowContainer = new WindowContainerComponent(this, settings, "Settings");

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onWindowClose() {
    menuDock.getChildren().clear();

    menuDock.getChildren().add(menu);
  }

  public void onMapOpenHandle() {
    Platform.runLater(() -> {
      menuDock.getChildren().clear();

      playArea.setMouseTransparent(false);
      playArea.getChildren().add(game);
    });
  }

  public void onMapCloseHandle() {
    playArea.getChildren().clear();
    playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);
  }

  public void removeWindow() {
    menuDock.getChildren().clear();

    menuDock.getChildren().add(menu);
  }

  public void onLobbyCreateHandle() {

  }

}
