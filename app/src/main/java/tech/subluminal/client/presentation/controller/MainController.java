package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.ChatComponent;
import tech.subluminal.client.presentation.customElements.GameComponent;
import tech.subluminal.client.presentation.customElements.LobbyComponent;
import tech.subluminal.client.presentation.customElements.MenuComponent;
import tech.subluminal.client.presentation.customElements.SettingsComponent;
import tech.subluminal.client.presentation.customElements.WindowContainerComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;

public class MainController implements Initializable {

  @FXML
  private Parent userListView;
  @FXML
  private UserListController userListViewController;

  @FXML
  private AnchorPane spaceBackgroundDock;

  @FXML
  private AnchorPane menuDock;

  @FXML
  private AnchorPane playArea;

  @FXML
  private Rectangle chatHandle;

  private BackgroundComponent background;

  private MenuComponent menu;

  private SettingsComponent settings;


  private LobbyComponent lobby;

  @FXML
  private WindowContainerComponent windowContainer;

  @FXML
  private AnchorPane window;

  @FXML
  private AnchorPane chatDock;

  @FXML
  private AnchorPane chatWindow;

  private GameComponent game;

  private UserStore userStore;

  private LobbyStore lobbyStore;

  private GameController gameController;

  private ChatComponent chat;

  private ChatController chatController;

  private boolean chatOut = false;

  public LobbyComponent getLobby() {
    return lobby;
  }

  public void setLobby(LobbyComponent lobby) {
    this.lobby = lobby;
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
    background = new BackgroundComponent(2000);
    spaceBackgroundDock.getChildren().add(background);

    chat = new ChatComponent(this);
    chatController = chat.getChatcontroller();
    chatDock.getChildren().add(chat);

    menu = new MenuComponent(this);
    settings = new SettingsComponent(this);
    lobby = new LobbyComponent();

    game = new GameComponent(this);
    gameController = game.getController();

    playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);

    Platform.runLater(() -> {

      chatWindow.translateXProperty().bind(Bindings
          .createDoubleBinding(() -> chatDock.getWidth(), chatWindow.widthProperty(),
              chatDock.widthProperty()));

    });

    chatHandle.setOnMouseClicked(e -> {
      Logger.debug("pressed");
      chatWindow.translateXProperty().unbind();
      TranslateTransition transTl = new TranslateTransition(Duration.seconds(0.2), chatWindow);
      double width = chatDock.widthProperty().getValue();
      if (chatOut) {
        transTl.setToX(width);
        transTl.play();
        chatOut = false;
      } else {
        transTl.setToX(0);
        transTl.play();
        chatOut = true;
      }
    });

  }

  public MainController getController() {
    return this;
  }

  public ChatController getChatController() {
    return this.chatController;
  }

  public UserListController getUserListController() {
    return this.userListViewController;
  }

  public void onWindowResizeHandle(int diffX, int diffY) {
    background.onWindowResize(diffX, diffY);
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

  public GameController getGameController() {
    return gameController;
  }
}
