package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.GameComponent;
import tech.subluminal.client.presentation.customElements.LobbyHostComponent;
import tech.subluminal.client.presentation.customElements.LobbyListComponent;
import tech.subluminal.client.presentation.customElements.MenuComponent;
import tech.subluminal.client.presentation.customElements.SettingsComponent;
import tech.subluminal.client.presentation.customElements.WindowContainerComponent;
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

  private LobbyHostComponent lobbyHost;

  @FXML
  private WindowContainerComponent windowContainer;

  @FXML
  private AnchorPane window;

  private GameComponent game;

  private UserStore userStore;


  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    userListViewController.setUserStore(userStore);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background = new BackgroundComponent(2000);
    spaceBackgroundDock.getChildren().add(background);

    menu = new MenuComponent(this);
    lobbyList = new LobbyListComponent(this);
    settings = new SettingsComponent(this);
    lobbyHost = new LobbyHostComponent(this);
    game = new GameComponent(this);

    //menuDock.getChildren().add(menu); //TODO: reactivate this
    onMapOpenHandle();
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

  public void onJoinHandle() {
    menuDock.getChildren().clear();

    windowContainer = new WindowContainerComponent(this, lobbyList);

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onHostOpenHandle() {
    menuDock.getChildren().remove(menu);

    windowContainer = new WindowContainerComponent(this, lobbyHost);

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();

  }

  public void onSettingOpenHandle() {
    menuDock.getChildren().remove(menu);

    windowContainer = new WindowContainerComponent(this, settings);

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onWindowClose() {
    menuDock.getChildren().clear();

    menuDock.getChildren().add(menu);
  }

  public void onMapOpenHandle(){
    menuDock.getChildren().clear();

    playArea.getChildren().add(game);
  }

  public void onMapCloseHandle(){
    playArea.getChildren().clear();

    menuDock.getChildren().add(menu);
  }
}
