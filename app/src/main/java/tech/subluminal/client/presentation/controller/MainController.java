package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.KeyMap;
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
import tech.subluminal.client.presentation.customElements.custom3DComponents.Button3dComponent;
import tech.subluminal.client.presentation.customElements.custom3DComponents.CockpitComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.server.stores.records.HighScore;

public class MainController implements Initializable {

  private final Timeline chatDownTl = new Timeline();
  private final BooleanProperty amountShown = new SimpleBooleanProperty();
  List<Node> tempMenu = new ArrayList<>();
  Group shipAmountGroup;
  Box amountMonitor;
  private KeyMap keyMap;
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
  private AnchorPane leverDock;
  @FXML
  private AnchorPane cockpitDock;
  @FXML
  private AnchorPane menuHolder;
  @FXML
  private AnchorPane introPane;
  @FXML
  private HBox introBoxHolder;
  @FXML
  private AnchorPane shipAmountDock;
  @FXML
  private GridPane middleBoardDock;
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
  private DebugComponent fpsTracker;
  private MonitorComponent fpsMonitor;
  private DebugComponent tpsTracker;
  private MonitorComponent tpsMonitor;
  private HighscoreComponent highscore;
  private DisplayComponent display;
  private Timeline chatUpTl = new Timeline();
  private Stage scene;
  private DoubleProperty tpsProperty = new SimpleDoubleProperty();
  private Button3dComponent leaveButton;

  public LobbyComponent getLobby() {
    return lobby;
  }

  public void setLobby(LobbyComponent lobby) {
    this.lobby = lobby;
  }

  public void setLobbyStore(LobbyStore lobbyStore) {
    this.lobbyStore = lobbyStore;
  }

  public boolean isAmountShown() {
    return amountShown.get();
  }

  public void setAmountShown(boolean amountShown) {
    this.amountShown.set(amountShown);
  }

  public BooleanProperty amountShownProperty() {
    return amountShown;
  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;

    userListController.setUserStore(userStore);
    userListController.setMainController(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    keyMap = new KeyMap();

    Timeline timeTl = initIntro();

    background = new BackgroundComponent(1000);
    spaceBackgroundDock.getChildren().add(background);

    initCockpit();

    initPlayArea();

    chat = new ChatComponent(this);
    chatController = chat.getChatcontroller();

    chatDock.getChildren().add(chat);

    initMiddleBoard();

    display = new DisplayComponent();

    userList = new UserListComponent(this, display);
    playerListButton = new ControlButton(this, "Players", userList, display);

    userList.prefWidthProperty().bind(display.widthProperty());
    userList.prefHeightProperty().bind(display.heightProperty());

    userListController = userList.getController();

    nameChange = new NameChangeComponent(this);
    nameChangeButton = new ControlButton(this, "NameChange", nameChange, display);
    //rightSideDock.getChildren().add(nameChangeButton);
    //rightSideDock.getChildren().add(nameChange);

    menu = new MenuComponent(this);
    settings = new SettingsComponent(this, keyMap);
    //rightSideDock.getChildren().add(settingsButton);

    highscore = new HighscoreComponent();

    lobby = new LobbyComponent();
    lobby.setMainController(getController());

    game = new GameComponent(this);
    gameController = game.getController();

    //playArea.setMouseTransparent(true);

    menuDock.getChildren().add(menu);

    initChatSide();

    initLeftBoard(timeTl);

  }

  private void initLeftBoard(Timeline timeTl) {
    VBox debugDock = initDebug();

    window.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode() == keyMap.get("FPS").get()) {
        if (debugDock.getChildren().contains(fpsTracker)) {
          debugDock.getChildren().remove(fpsTracker);
        } else {
          debugDock.getChildren().add(fpsTracker);
        }
      } else if (keyEvent.getCode() == keyMap.get("FPSMonitor").get()) {
        if (debugDock.getChildren().contains(fpsMonitor)) {
          debugDock.getChildren().remove(fpsMonitor);
        } else {
          debugDock.getChildren().add(fpsMonitor);
        }
      } else if (keyEvent.getCode() == keyMap.get("TPS").get()) {
        if (debugDock.getChildren().contains(tpsTracker)) {
          debugDock.getChildren().remove(tpsTracker);
        } else {
          debugDock.getChildren().add(tpsTracker);
        }
      } else if (keyEvent.getCode() == keyMap.get("TPSMonitor").get()) {
        if (debugDock.getChildren().contains(tpsMonitor)) {
          debugDock.getChildren().remove(tpsMonitor);
        } else {
          debugDock.getChildren().add(tpsMonitor);
        }
      } else if (keyEvent.getCode() == keyMap.get("Settings").get()) {
        onSettingOpenHandle();
      } else if (keyEvent.getCode() == keyMap.get("Skip").get()) {
        clearIntro(timeTl);
      } else if (keyEvent.getCode() == keyMap.get("Fullscreen").get()) {
        Platform.runLater(() -> {
          if (getScene().isFullScreen()) {
            getScene().setFullScreen(false);
          } else {
            getScene().setFullScreen(true);
          }
        });
      }
    });

    window.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
      if (keyEvent.getCode() == keyMap.get("Chat").getValue()) {
        toggleChat();
      }
    });
  }

  private void initPlayArea() {
    Rectangle clipNode = new Rectangle();
    clipNode.widthProperty().bind(playArea.widthProperty());
    clipNode.heightProperty().bind(playArea.heightProperty());
    playArea.setClip(clipNode);

    playArea.setBackground(new Background(
        new BackgroundImage(new Image("/tech/subluminal/resources/Pixel_Overlay.png"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
  }

  private void initCockpit() {
    CockpitComponent cockpit = new CockpitComponent();
    cockpitDock.getChildren().addAll(cockpit);
    cockpit.setVisible(true);
  }

  private VBox initDebug() {
    FpsUpdater updater = new FpsUpdater();

    fpsTracker = new DebugComponent(updater.averageFpsProperty(), "FPS");
    fpsMonitor = new MonitorComponent(updater.averageFpsProperty(), "FPS");

    tpsTracker = new DebugComponent(tpsProperty, "TPS");
    tpsMonitor = new MonitorComponent(tpsProperty, "TPS");

    VBox debugDock = new VBox();
    window.getChildren().add(debugDock);
    return debugDock;
  }

  private void initChatSide() {
    Rotate rotate = new Rotate(-60, 0, 0, 0, Rotate.X_AXIS);
    rotate.pivotYProperty().bind(boardComputerWrapper.heightProperty());
    boardComputer.getTransforms().add(rotate);

    Rotate rotateTl = new Rotate();
    rotateTl.pivotYProperty().bind(chat.heightProperty());
    rotateTl.setPivotX(0);
    rotateTl.setPivotZ(0);
    rotateTl.setAxis(Rotate.X_AXIS);
    chat.getTransforms().add(rotateTl);

    chatUpTl.getKeyFrames().add(
        new KeyFrame(Duration.seconds(0.7), new KeyValue(rotateTl.angleProperty(), 60))
    );

    chatDownTl.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(0.7), new KeyValue(rotateTl.angleProperty(), 0)));

    boardComputer.setOnMouseClicked((e) -> {
      toggleChat();
    });

    Button3dComponent settingButton = new Button3dComponent("Settings");
    settingButton.setOnMouseClicked((e) -> {
      Button3dComponent settingClose = new Button3dComponent("X");
      settingClose.setOnMouseClicked(event -> {
        onWindowClose();
        event.consume();
        buttonsDock.getChildren().remove(settingButton);
        buttonsDock.add(settingButton, 0, 0);
      });
      buttonsDock.getChildren().remove(settingButton);
      buttonsDock.add(settingClose, 0, 0);
      onSettingOpenHandle();
      e.consume();
    });

    leaveButton = new Button3dComponent("Leave");

    leaveButton.setOnMouseClicked(event -> {
      gameController.leaveGame();
      Logger.debug("LEAVE PLZ");
    });

    hideLeaveButton();

    buttonsDock.add(settingButton, 0, 0);
    buttonsDock.add(playerListButton, 0, 1);
    buttonsDock.add(nameChangeButton, 0, 2);
    buttonsDock.add(leaveButton, 0, 3);

    bindDockButtons(settingButton);
    bindDockButtons(playerListButton);
    bindDockButtons(nameChangeButton);
    bindDockButtons(leaveButton);

    monitorDock.add(display, 1, 0);
  }

  private void initMiddleBoard() {
    shipAmountDock.setBackground(
        new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    clearMiddleBoard();

    amountShown.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        middleBoardDock.setDisable(false);
      } else {
        middleBoardDock.setDisable(true);
      }
    });
  }

  private void clearMiddleBoard() {
    Button3dComponent standartMiddleButtonUp = new Button3dComponent("");
    Button3dComponent standartMiddleButtonDown = new Button3dComponent("");

    middleBoardDock.getChildren().clear();

    middleBoardDock.add(standartMiddleButtonUp, 0, 1);
    middleBoardDock.add(standartMiddleButtonDown, 0, 2);
  }

  private Timeline initIntro() {
    introPane.setBackground(
        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    Label introText = new Label();
    introText.setTextAlignment(TextAlignment.CENTER);
    introText.setTranslateY(-100);
    introText.setWrapText(true);
    introText.getStyleClass().addAll("console-red", "intro-text");
    introBoxHolder.getChildren().addAll(introText);

    String introStory = "In a basement no more than a few parsecs from here, you were created.\nAnd now you and your peers are set to conquer the galaxy...";

    SequentialTransition mainTl = new SequentialTransition();
    PauseTransition pauseTl = new PauseTransition(Duration.seconds(2));
    PauseTransition pauseTl2 = new PauseTransition(Duration.seconds(2));

    Timeline timeTl = new Timeline();

    timeTl.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(0.5), "0", e -> {
          if (introText.getText().length() >= introStory.length() && introText.getText()
              .charAt(introText.getText().length() - 1) == ' ') {
            introText
                .setText(introText.getText().substring(0, introText.getText().length() - 1) + "|");
          } else {
            introText.setText(introText.getText() + "|");
          }

        }));
    timeTl.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(1), "1", e -> {
          if (introText.getText().length() >= introStory.length() - 1 && introText.getText()
              .charAt(introText.getText().length() - 1) == ' ') {
            introText.setText(introText.getText().substring(0, introText.getText().length() - 1));
          } else {
            introText
                .setText(introText.getText().substring(0, introText.getText().length() - 1) + " ");
          }
        }));

    timeTl.setCycleCount(Timeline.INDEFINITE);

    final Animation animation = new Transition() {
      {
        setCycleDuration(Duration.millis(5000));
      }

      protected void interpolate(double frac) {
        final int length = introStory.length();
        final int n = Math.round(length * (float) frac);
        introText.setText(introStory.substring(0, n));
      }

    };

    FadeTransition fadeTl = new FadeTransition();
    fadeTl.setNode(introPane);
    fadeTl.setToValue(0);
    fadeTl.setFromValue(1);
    fadeTl.setDuration(Duration.seconds(2));

    mainTl.getChildren().addAll(pauseTl, animation, pauseTl2, fadeTl);
    timeTl.play();
    mainTl.play();

    mainTl.setOnFinished(e -> {
      clearIntro(timeTl);
    });
    return timeTl;
  }

  private void toggleChat() {
    if (chatDown) {
      openChat();
    } else if (!chat.getTextField().isFocused()) {
      closeChat();
    }
  }

  private void closeChat() {
    chatUpTl.stop();
    chatDownTl.play();
    chatDown = true;
  }

  private void openChat() {
    chatDownTl.stop();
    chatUpTl.play();
    chatController.requestFocus();
    chatDown = false;
  }

  private void clearIntro(Timeline timeTl) {
    introPane.setMouseTransparent(true);
    introPane.setVisible(false);
    timeTl.stop();
  }

  private void bindDockButtons(Button3dComponent button) {
    button.prefWidthProperty().bind(buttonsDock.widthProperty());
    button.prefHeightProperty().bind(Bindings
        .createDoubleBinding(() -> buttonsDock.getHeight() / buttonsDock.getChildren().size(),
            buttonsDock.heightProperty(), buttonsDock.getChildren()));
  }

  private void bindDockButtons(ControlButton button) {
    button.prefWidthProperty().bind(buttonsDock.widthProperty());
    button.prefHeightProperty().bind(Bindings
        .createDoubleBinding(() -> buttonsDock.getHeight() / buttonsDock.getChildren().size(),
            buttonsDock.heightProperty(), buttonsDock.getChildren()));
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

  /**
   * Creates a windows when the play button is pressed.
   */
  public void onLobbyOpenHandle() {
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, lobby, "Lobbies");

    fitWindow();

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  /**
   * Creates a window when the settings button is pressed.
   */
  public void onSettingOpenHandle() {
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, settings, "Settings");

    fitWindow();

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  private void fitWindow() {
    Platform.runLater(() -> {
      windowContainer.prefHeightProperty().bind(playArea.prefHeightProperty());
      windowContainer.prefWidthProperty().bind(playArea.prefWidthProperty());

    });
  }

  private void saveMenuState() {
    if (menuDock.getChildren().size() != 0) {
      menuDock.getChildren().forEach(tempMenu::add);
      menuDock.getChildren().clear();
    }
    menuDock.getChildren().remove(menu);

    if (menuHolder.isMouseTransparent()) {
      menuHolder.setMouseTransparent(false);
    }
  }

  /**
   * Removes a window from the dock.
   */
  public void onWindowClose() {
    menuDock.getChildren().clear();

    if (tempMenu != null && tempMenu.size() != 0) {
      tempMenu.forEach(menuDock.getChildren()::add);
      tempMenu.clear();
      menuHolder.setMouseTransparent(false);
    } else {
      menuHolder.setMouseTransparent(true);
    }


  }

  public AnchorPane getPlayArea() {
    return playArea;
  }

  /**
   * Adds all the windows and buttons on game start.
   */
  public void onMapOpenHandle() {
    Platform.runLater(() -> {

      menuDock.getChildren().clear();

      menuHolder.setMouseTransparent(true);

      //rightSideDock.getChildren().clear();

      chatController.setInGame(true);

      playArea.getChildren().add(game);
      playArea.setMouseTransparent(false);

      showLeaveButton(false, "Leave");
    });
  }

  private void showLeaveButton(boolean b, String leave) {
    leaveButton.setDisable(b);
    leaveButton.setText(leave);
  }

  /**
   * Removes the game windows components.
   */
  public void onMapCloseHandle() {
    playArea.getChildren().clear();

    playArea.setMouseTransparent(true);
    gameController.clearMap();
    chatController.setInGame(false);
    gameController.clearToastDock();

    menuHolder.setMouseTransparent(false);

    menuDock.getChildren().add(menu);

    hideLeaveButton();
  }

  private void hideLeaveButton() {
    showLeaveButton(true, "");
  }

  public void setTps(double tps) {
    Platform.runLater(() -> tpsProperty.setValue(tps));
  }

  /**
   * Removes a window component from the menu dock.
   */
  public void removeWindow() {
    menuDock.getChildren().clear();
    menuDock.getChildren().add(menu);

    if (menuHolder.isMouseTransparent()) {
      menuHolder.setMouseTransparent(false);
    }
  }

  public void sendRecipiantToChat(String recipiant) {
    chatController.writeAt(recipiant);
  }


  public GameController getGameController() {
    return gameController;
  }

  /**
   * Gets triggered when opening the highscore view via a button.
   */
  public void onHighscoreHandle() {
    chatController.requestHighscores();
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, highscore, "Highscore");
    highscore.prefWidthProperty().bind(windowContainer.widthProperty());
    highscore.setMaxHeight(400);
    highscore.setPrefViewportHeight(400);
    //highscore.maxHeightProperty().bind(windowContainer.heightProperty());

    fitWindow();

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onUpdateHighscoreHandle(List<HighScore> highScores) {
    highscore.update(highScores);
  }

  public Stage getScene() {
    return scene;
  }

  public void setScene(Stage scene) {
    this.scene = scene;
  }

  public void setAmountBox(TextField actual, Button3dComponent sendMother, Button3dComponent send) {
    amountShown.set(true);
    middleBoardDock.add(actual, 0, 0);

    actual.setBackground(
        new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    actual.setAlignment(Pos.CENTER);
    actual.setFont(new Font("PxPlus IBM VGA9", 30));

    middleBoardDock.add(sendMother, 0, 1);
    middleBoardDock.add(send, 0, 2);

  }

  public void resetAmounBox() {
    amountShown.set(false);
    clearMiddleBoard();
  }

  public void setWindowTitle(String text) {
    windowContainer.setTitle(text);
  }
}
