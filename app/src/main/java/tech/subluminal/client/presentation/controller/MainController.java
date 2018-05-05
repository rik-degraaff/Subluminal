package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.ChatComponent;
import tech.subluminal.client.presentation.customElements.ControlButton;
import tech.subluminal.client.presentation.customElements.DebugComponent;
import tech.subluminal.client.presentation.customElements.FpsUpdater;
import tech.subluminal.client.presentation.customElements.GameComponent;
import tech.subluminal.client.presentation.customElements.HighscoreComponent;
import tech.subluminal.client.presentation.customElements.LobbyComponent;
import tech.subluminal.client.presentation.customElements.MenuComponent;
import tech.subluminal.client.presentation.customElements.MonitorComponent;
import tech.subluminal.client.presentation.customElements.NameChangeComponent;
import tech.subluminal.client.presentation.customElements.SettingsComponent;
import tech.subluminal.client.presentation.customElements.UserListComponent;
import tech.subluminal.client.presentation.customElements.WindowContainerComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.server.stores.records.HighScore;

public class MainController implements Initializable {

  @FXML
  private AnchorPane spaceBackgroundDock;

  @FXML
  private VBox leftSideDock;

  @FXML
  private VBox rightSideDock;

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

  @FXML
  private AnchorPane playerBoardDock;

  @FXML
  private VBox statusBoxDock;

  private GameComponent game;

  private UserStore userStore;

  private LobbyStore lobbyStore;

  private GameController gameController;

  private ChatComponent chat;

  private ChatController chatController;

  private UserListComponent userList;

  private UserListController userListController;

  private boolean chatOut = false;
  private NameChangeComponent nameChange;
  private ControlButton playerListButton;
  private ControlButton nameChangeButton;
  private ControlButton settingsButton;
  private DebugComponent debug;
  private MonitorComponent monitor;
  private HighscoreComponent highscore;

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

    userListController.setUserStore(userStore);
    userListController.setMainController(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background = new BackgroundComponent(100);
    spaceBackgroundDock.getChildren().add(background);

    chat = new ChatComponent(this);
    chatController = chat.getChatcontroller();
    chatDock.getChildren().add(chat);

    userList = new UserListComponent(this);
    playerListButton = new ControlButton(this, "P", userList, statusBoxDock);
    rightSideDock.getChildren().add(playerListButton);

    userListController = userList.getController();

    nameChange = new NameChangeComponent(this);
    nameChangeButton = new ControlButton(this, "C", nameChange, statusBoxDock);
    rightSideDock.getChildren().add(nameChangeButton);
    //rightSideDock.getChildren().add(nameChange);

    menu = new MenuComponent(this);
    settings = new SettingsComponent(this);
    settingsButton = new ControlButton(this, "S", settings, statusBoxDock);
    rightSideDock.getChildren().add(settingsButton);

    highscore = new HighscoreComponent();

    lobby = new LobbyComponent();

    game = new GameComponent(this);
    gameController = game.getController();

    playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);

    FpsUpdater updater = new FpsUpdater();

    debug = new DebugComponent(updater.averageFpsProperty());
    monitor = new MonitorComponent(updater.averageFpsProperty());

    VBox debugDock = new VBox();
    window.getChildren().add(debugDock);

    window.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode() == KeyCode.F4) {
        if (debugDock.getChildren().contains(debug)) {
          debugDock.getChildren().remove(debug);
        } else {
          debugDock.getChildren().add(debug);
        }
      } else if (keyEvent.getCode() == KeyCode.F5) {
        if (debugDock.getChildren().contains(monitor)) {
          debugDock.getChildren().remove(monitor);
        } else {
          debugDock.getChildren().add(monitor);
        }
      }
    });

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
        transTl.setToX(-30);
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
    return this.userListController;
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

      rightSideDock.getChildren().clear();
      Pane leftSide = new Pane();
      leftSide.setPrefWidth(rightSideDock.getWidth());
      leftSide.prefHeightProperty().bind(chat.getScene().heightProperty());
      Background bg = new Background(
          new BackgroundImage(
              new Image("/tech/subluminal/resources/tile_texture.jpg", 50, 50, true, false),
              BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
              BackgroundSize.DEFAULT));
      leftSide.setBackground(bg);
      VBox rightSide = new VBox();
      rightSide.setPrefWidth(leftSideDock.getWidth());
      rightSide.prefHeightProperty().bind(chat.getScene().heightProperty());
      rightSide.setBackground(bg);
      leftSideDock.getChildren().add(leftSide);
      rightSideDock.getChildren().add(rightSide);

      rightSideDock.getChildren().removeAll(playerListButton, nameChangeButton, settingsButton);
      rightSide.getChildren().addAll(settingsButton, playerListButton, nameChangeButton);
      //rightSide.getChildren().add(new Label("this is a test"));

      chatController.setInGame(true);
      playArea.getChildren().add(game);
    });
  }

  public void onMapCloseHandle() {
    playArea.getChildren().clear();
    rightSideDock.getChildren().clear();
    leftSideDock.getChildren().clear();
    playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);
  }

  public void removeWindow() {
    menuDock.getChildren().clear();

    menuDock.getChildren().add(menu);
  }

  public void onLobbyCreateHandle() {

  }

  public void sendRecipiantToChat(String recipiant) {
    chatController.writeAt(recipiant);
    if (!chatOut) {
      openChat();
    }
  }

  public void openChat() {
    fireMouseClick(chatHandle);
  }

  private void fireMouseClick(Node node) {
    if (!chatOut) {
      MouseEvent event = new MouseEvent(MouseEvent.MOUSE_CLICKED,
          node.getLayoutX(), node.getLayoutY(), node.getLayoutX(), node.getLayoutY(),
          MouseButton.PRIMARY, 1,
          true, true, true, true, true, true, true, true, true, true, null);
      Event.fireEvent(chatHandle, event);
    }
  }

  public GameController getGameController() {
    return gameController;
  }

  public void onHighscoreHandle() {
    chatController.requestHighscores();
    menuDock.getChildren().remove(menu);

    windowContainer = new WindowContainerComponent(this, highscore, "Highscore");

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onUpdateHighscoreHandle(List<HighScore> highScores) {
    highscore.update(highScores);
  }
}
