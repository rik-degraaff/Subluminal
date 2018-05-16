package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.ChatComponent;
import tech.subluminal.client.presentation.customElements.ControlButton;
import tech.subluminal.client.presentation.customElements.DebugComponent;
import tech.subluminal.client.presentation.customElements.DisplayComponent;
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
import tech.subluminal.client.presentation.customElements.custom3DComponents.CockpitComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.server.stores.records.HighScore;

public class MainController implements Initializable {

  @FXML
  private AnchorPane spaceBackgroundDock;

  @FXML
  private AnchorPane menuDock;

  @FXML
  private AnchorPane playArea;

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
  private AnchorPane playerBoardDock;

  @FXML
  private VBox statusBoxDock;

  @FXML
  private AnchorPane boardComputer;

  @FXML
  private AnchorPane boardComputerWrapper;

  @FXML
  private Cylinder boardCylinder;

  @FXML
  private GridPane buttonsDock;

  @FXML
  private GridPane monitorDock;

  @FXML
  private AnchorPane glassPane;

  @FXML
  private AnchorPane arcLeftDock;

  @FXML
  private AnchorPane arcRightDock;

  @FXML
  private AnchorPane leverDock;

  @FXML
  private AnchorPane cockpitDock;

  private GameComponent game;

  private UserStore userStore;

  private LobbyStore lobbyStore;

  private GameController gameController;

  private ChatComponent chat;

  private ChatController chatController;

  private UserListComponent userList;

  private UserListController userListController;

  private boolean chatDown = true;
  private NameChangeComponent nameChange;
  private ControlButton playerListButton;
  private ControlButton nameChangeButton;
  private ControlButton settingsButton;
  private DebugComponent debug;
  private MonitorComponent monitor;
  private HighscoreComponent highscore;
  private DisplayComponent display;

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
    background = new BackgroundComponent(1000);
    spaceBackgroundDock.getChildren().add(background);

    CockpitComponent cockpit = new CockpitComponent();
    cockpitDock.getChildren().addAll(cockpit);

    Rectangle clipNode = new Rectangle();
    clipNode.widthProperty().bind(playArea.widthProperty());
    clipNode.heightProperty().bind(playArea.heightProperty());
    playArea.setClip(clipNode);

    playArea.setBackground(new Background(
        new BackgroundImage(new Image("/tech/subluminal/resources/Pixel_Overlay.png"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));


    chat = new ChatComponent(this);
    chatController = chat.getChatcontroller();
    chatDock.getChildren().add(chat);

    //LeverComponent lever = new LeverComponent();
    //leverDock.getChildren().addAll(lever);
    //leverDock.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));

    display = new DisplayComponent();

    userList = new UserListComponent(this);
    playerListButton = new ControlButton(this, "P", userList, display);
    userList.prefWidthProperty().bind(display.widthProperty());
    userList.prefHeightProperty().bind(display.heightProperty());

    userListController = userList.getController();

    nameChange = new NameChangeComponent(this);
    nameChangeButton = new ControlButton(this, "C", nameChange, display);
    //rightSideDock.getChildren().add(nameChangeButton);
    //rightSideDock.getChildren().add(nameChange);

    menu = new MenuComponent(this);
    settings = new SettingsComponent(this);
    settingsButton = new ControlButton(this, "S", settings, display);
    //rightSideDock.getChildren().add(settingsButton);

    highscore = new HighscoreComponent();

    lobby = new LobbyComponent();

    game = new GameComponent(this);
    gameController = game.getController();

    //playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);

    FpsUpdater updater = new FpsUpdater();

    debug = new DebugComponent(updater.averageFpsProperty());
    monitor = new MonitorComponent(updater.averageFpsProperty());

    VBox debugDock = new VBox();
    window.getChildren().add(debugDock);

    PerspectiveTransform perspect = new PerspectiveTransform();
    perspect.setUlx(50);
    perspect.setUly(10);
    perspect.urxProperty().bind(Bindings
        .createDoubleBinding(() -> boardComputerWrapper.getWidth() - 50,
            boardComputer.widthProperty()));
    perspect.setUry(10);

    perspect.setLlx(0);
    perspect.llyProperty().bind(boardComputer.heightProperty());
    perspect.lrxProperty().bind(boardComputerWrapper.widthProperty());
    perspect.lryProperty().bind(boardComputer.heightProperty());

    Rotate rotate = new Rotate(-60, 0, 0, 0, Rotate.X_AXIS);
    rotate.pivotYProperty().bind(boardComputerWrapper.heightProperty());
    boardComputer.getTransforms().add(rotate);

    Rotate rotateTl = new Rotate();
    rotateTl.pivotYProperty().bind(chat.heightProperty());
    rotateTl.setPivotX(0);
    rotateTl.setPivotZ(0);
    rotateTl.setAxis(Rotate.X_AXIS);
    chat.getTransforms().add(rotateTl);

    Timeline timeTlUp = new Timeline(
        //new KeyFrame(Duration.ZERO, new KeyValue(rotateTl.angleProperty(), chat.getRotate())),
        new KeyFrame(Duration.seconds(0.7), new KeyValue(rotateTl.angleProperty(), 60))
    );

    Timeline timeTlDown = new Timeline(
        //new KeyFrame(Duration.ZERO, new KeyValue(rotateTl.angleProperty(), chat.getRotate())),
        new KeyFrame(Duration.seconds(0.7), new KeyValue(rotateTl.angleProperty(), 0))
    );

    boardComputer.setOnMouseClicked((e) -> {
      if (chatDown) {
        timeTlDown.stop();
        timeTlUp.play();
        chatDown = false;
      } else {
        timeTlUp.stop();
        timeTlDown.play();
        chatDown = true;
      }
    });

    buttonsDock.add(settingsButton, 0, 0);
    buttonsDock.add(playerListButton, 0, 1);
    buttonsDock.add(nameChangeButton, 0, 2);

    monitorDock.add(display, 1, 0);

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

  public AnchorPane getPlayArea() {
    return playArea;
  }

  public void onMapOpenHandle() {
    Platform.runLater(() -> {
      menuDock.getChildren().clear();

      //playArea.setMouseTransparent(false);

      //rightSideDock.getChildren().clear();

      Button leave = new Button("X");
      leave.setOnAction(event -> {
        gameController.leaveGame();
        Logger.debug("LEAVE PLZ");
      });
      //rightSide.getChildren().add(new Label("this is a test"));

      chatController.setInGame(true);
      playArea.getChildren().add(game);
    });
  }

  public void onMapCloseHandle() {
    playArea.getChildren().clear();

    //playArea.setMouseTransparent(true);
    gameController.clearMap();
    chatController.setInGame(false);

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
