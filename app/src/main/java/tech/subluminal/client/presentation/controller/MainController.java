//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
  List<Node> tempMenu = new ArrayList();
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

  public MainController() {
  }

  public LobbyComponent getLobby() {
    return this.lobby;
  }

  public void setLobby(LobbyComponent lobby) {
    this.lobby = lobby;
  }

  public void setLobbyStore(LobbyStore lobbyStore) {
    this.lobbyStore = lobbyStore;
  }

  public boolean isAmountShown() {
    return this.amountShown.get();
  }

  public void setAmountShown(boolean amountShown) {
    this.amountShown.set(amountShown);
  }

  public BooleanProperty amountShownProperty() {
    return this.amountShown;
  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;
    this.userListController.setUserStore(userStore);
    this.userListController.setMainController(this);
  }

  public void initialize(URL location, ResourceBundle resources) {
    this.keyMap = new KeyMap();
    Timeline timeTl = this.initIntro();
    this.background = new BackgroundComponent(1000);
    this.spaceBackgroundDock.getChildren().add(this.background);
    this.initCockpit();
    this.initPlayArea();
    this.chat = new ChatComponent(this);
    this.chatController = this.chat.getChatcontroller();
    this.chatDock.getChildren().add(this.chat);
    this.initMiddleBoard();
    this.display = new DisplayComponent();
    this.userList = new UserListComponent(this, this.display);
    this.playerListButton = new ControlButton(this, "Players", this.userList, this.display);
    this.userList.prefWidthProperty().bind(this.display.widthProperty());
    this.userList.prefHeightProperty().bind(this.display.heightProperty());
    this.userListController = this.userList.getController();
    this.nameChange = new NameChangeComponent(this);
    this.nameChangeButton = new ControlButton(this, "NameChange", this.nameChange, this.display);
    this.menu = new MenuComponent(this);
    this.settings = new SettingsComponent(this, this.keyMap);
    this.highscore = new HighscoreComponent();
    this.lobby = new LobbyComponent();
    this.lobby.setMainController(this.getController());
    this.game = new GameComponent(this);
    this.gameController = this.game.getController();
    this.menuDock.getChildren().add(this.menu);
    this.initChatSide();
    this.initLeftBoard(timeTl);
  }

  private void initLeftBoard(Timeline timeTl) {
    VBox debugDock = this.initDebug();
    this.window.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
      if (!(this.scene.getScene().focusOwnerProperty().get() instanceof TextArea) && !(this.scene.getScene().focusOwnerProperty().get() instanceof TextField)) {
        if (keyEvent.getCode() == this.keyMap.get("FPS").get()) {
          if (debugDock.getChildren().contains(this.fpsTracker)) {
            debugDock.getChildren().remove(this.fpsTracker);
          } else {
            debugDock.getChildren().add(this.fpsTracker);
          }
        } else if (keyEvent.getCode() == this.keyMap.get("FPSMonitor").get()) {
          if (debugDock.getChildren().contains(this.fpsMonitor)) {
            debugDock.getChildren().remove(this.fpsMonitor);
          } else {
            debugDock.getChildren().add(this.fpsMonitor);
          }
        } else if (keyEvent.getCode() == this.keyMap.get("TPS").get()) {
          if (debugDock.getChildren().contains(this.tpsTracker)) {
            debugDock.getChildren().remove(this.tpsTracker);
          } else {
            debugDock.getChildren().add(this.tpsTracker);
          }
        } else if (keyEvent.getCode() == this.keyMap.get("TPSMonitor").get()) {
          if (debugDock.getChildren().contains(this.tpsMonitor)) {
            debugDock.getChildren().remove(this.tpsMonitor);
          } else {
            debugDock.getChildren().add(this.tpsMonitor);
          }
        } else if (keyEvent.getCode() == this.keyMap.get("Settings").get()) {
          this.onSettingOpenHandle();
        } else if (keyEvent.getCode() == this.keyMap.get("Skip").get()) {
          this.clearIntro(timeTl);
        } else if (keyEvent.getCode() == this.keyMap.get("Fullscreen").get()) {
          Platform.runLater(() -> {
            if (this.getScene().isFullScreen()) {
              this.getScene().setFullScreen(false);
            } else {
              this.getScene().setFullScreen(true);
            }

          });
        }

      }
    });
    this.window.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
      if (!(this.scene.getScene().focusOwnerProperty().get() instanceof TextArea) && !(this.scene.getScene().focusOwnerProperty().get() instanceof TextField)) {
        if (keyEvent.getCode() == this.keyMap.get("Chat").getValue()) {
          this.toggleChat();
        }

      }
    });
  }

  private void initPlayArea() {
    Rectangle clipNode = new Rectangle();
    clipNode.widthProperty().bind(this.playArea.widthProperty());
    clipNode.heightProperty().bind(this.playArea.heightProperty());
    this.playArea.setClip(clipNode);
    this.playArea.setBackground(new Background(new BackgroundImage[]{new BackgroundImage(new Image("/tech/subluminal/resources/Pixel_Overlay.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)}));
  }

  private void initCockpit() {
    CockpitComponent cockpit = new CockpitComponent();
    this.cockpitDock.getChildren().addAll(new Node[]{cockpit});
    cockpit.setVisible(true);
  }

  private VBox initDebug() {
    FpsUpdater updater = new FpsUpdater();
    this.fpsTracker = new DebugComponent(updater.averageFpsProperty(), "FPS");
    this.fpsMonitor = new MonitorComponent(updater.averageFpsProperty(), "FPS");
    this.tpsTracker = new DebugComponent(this.tpsProperty, "TPS");
    this.tpsMonitor = new MonitorComponent(this.tpsProperty, "TPS");
    VBox debugDock = new VBox();
    this.window.getChildren().add(debugDock);
    return debugDock;
  }

  private void initChatSide() {
    Rotate rotate = new Rotate(-60.0D, 0.0D, 0.0D, 0.0D, Rotate.X_AXIS);
    rotate.pivotYProperty().bind(this.boardComputerWrapper.heightProperty());
    this.boardComputer.getTransforms().add(rotate);
    Rotate rotateTl = new Rotate();
    rotateTl.pivotYProperty().bind(this.chat.heightProperty());
    rotateTl.setPivotX(0.0D);
    rotateTl.setPivotZ(0.0D);
    rotateTl.setAxis(Rotate.X_AXIS);
    this.chat.getTransforms().add(rotateTl);
    this.chatUpTl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.7D), new KeyValue[]{new KeyValue(rotateTl.angleProperty(), 60)}));
    this.chatDownTl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.7D), new KeyValue[]{new KeyValue(rotateTl.angleProperty(), 0)}));
    this.boardComputer.setOnMouseClicked((e) -> {
      this.toggleChat();
    });
    Button3dComponent settingButton = new Button3dComponent("Settings");
    settingButton.setOnMouseClicked((e) -> {
      Button3dComponent settingClose = new Button3dComponent("X");
      settingClose.setOnMouseClicked((event) -> {
        event.consume();
        if (this.tempMenu.isEmpty()) {
          this.tempMenu.add(this.menu);
        }

        this.onWindowClose();
        this.buttonsDock.getChildren().remove(settingButton);
        this.buttonsDock.add(settingButton, 0, 0);
      });
      this.buttonsDock.getChildren().remove(settingButton);
      this.buttonsDock.add(settingClose, 0, 0);
      this.onSettingOpenHandle();
      e.consume();
    });
    this.leaveButton = new Button3dComponent("Leave");
    this.leaveButton.setOnMouseClicked((event) -> {
      this.gameController.leaveGame();
      Logger.debug("LEAVE PLZ");
    });
    this.hideLeaveButton();
    this.buttonsDock.add(settingButton, 0, 0);
    this.buttonsDock.add(this.playerListButton, 0, 1);
    this.buttonsDock.add(this.nameChangeButton, 0, 2);
    this.buttonsDock.add(this.leaveButton, 0, 3);
    this.bindDockButtons(settingButton);
    this.bindDockButtons(this.playerListButton);
    this.bindDockButtons(this.nameChangeButton);
    this.bindDockButtons(this.leaveButton);
    this.monitorDock.add(this.display, 1, 0);
  }

  private void initMiddleBoard() {
    this.shipAmountDock.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)}));
    this.clearMiddleBoard();
    this.amountShown.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        this.middleBoardDock.setDisable(false);
      } else {
        this.middleBoardDock.setDisable(true);
      }

    });
  }

  private void clearMiddleBoard() {
    Button3dComponent standartMiddleButtonUp = new Button3dComponent("");
    Button3dComponent standartMiddleButtonDown = new Button3dComponent("");
    this.middleBoardDock.getChildren().clear();
    this.middleBoardDock.add(standartMiddleButtonUp, 0, 1);
    this.middleBoardDock.add(standartMiddleButtonDown, 0, 2);
  }

  private Timeline initIntro() {
    this.introPane.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)}));
    final Label introText = new Label();
    introText.setTextAlignment(TextAlignment.CENTER);
    introText.setTranslateY(-100.0D);
    introText.setWrapText(true);
    introText.getStyleClass().addAll(new String[]{"console-red", "intro-text"});
    this.introBoxHolder.getChildren().addAll(new Node[]{introText});
    final String introStory = "In a basement no more than a few parsecs from here, you were created.\nAnd now you and your peers are set to conquer the galaxy...";
    SequentialTransition mainTl = new SequentialTransition();
    PauseTransition pauseTl = new PauseTransition(Duration.seconds(2.0D));
    PauseTransition pauseTl2 = new PauseTransition(Duration.seconds(2.0D));
    Timeline timeTl = new Timeline();
    timeTl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5D), "0", (e) -> {
      if (introText.getText().length() >= introStory.length() && introText.getText().charAt(introText.getText().length() - 1) == ' ') {
        introText.setText(introText.getText().substring(0, introText.getText().length() - 1) + "|");
      } else {
        introText.setText(introText.getText() + "|");
      }

    }, new KeyValue[0]));
    timeTl.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0D), "1", (e) -> {
      if (introText.getText().length() >= introStory.length() - 1 && introText.getText().charAt(introText.getText().length() - 1) == ' ') {
        introText.setText(introText.getText().substring(0, introText.getText().length() - 1));
      } else {
        introText.setText(introText.getText().substring(0, introText.getText().length() - 1) + " ");
      }

    }, new KeyValue[0]));
    timeTl.setCycleCount(-1);
    Animation animation = new Transition() {
      {
        this.setCycleDuration(Duration.millis(5000.0D));
      }

      protected void interpolate(double frac) {
        int length = introStory.length();
        int n = Math.round((float)length * (float)frac);
        introText.setText(introStory.substring(0, n));
      }
    };
    FadeTransition fadeTl = new FadeTransition();
    fadeTl.setNode(this.introPane);
    fadeTl.setToValue(0.0D);
    fadeTl.setFromValue(1.0D);
    fadeTl.setDuration(Duration.seconds(2.0D));
    mainTl.getChildren().addAll(new Animation[]{pauseTl, animation, pauseTl2, fadeTl});
    timeTl.play();
    mainTl.play();
    mainTl.setOnFinished((e) -> {
      this.clearIntro(timeTl);
    });
    return timeTl;
  }

  private void toggleChat() {
    if (this.chatDown) {
      this.openChat();
    } else if (!this.chat.getTextField().isFocused()) {
      this.closeChat();
    }

  }

  private void closeChat() {
    this.chatUpTl.stop();
    this.chatDownTl.play();
    this.chatDown = true;
  }

  private void openChat() {
    this.chatDownTl.stop();
    this.chatUpTl.play();
    this.chatController.requestFocus();
    this.chatDown = false;
  }

  private void clearIntro(Timeline timeTl) {
    this.introPane.setMouseTransparent(true);
    this.introPane.setVisible(false);
    timeTl.stop();
  }

  private void bindDockButtons(Button3dComponent button) {
    button.prefWidthProperty().bind(this.buttonsDock.widthProperty());
    button.prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
      return this.buttonsDock.getHeight() / (double)this.buttonsDock.getChildren().size();
    }, new Observable[]{this.buttonsDock.heightProperty(), this.buttonsDock.getChildren()}));
  }

  private void bindDockButtons(ControlButton button) {
    button.prefWidthProperty().bind(this.buttonsDock.widthProperty());
    button.prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
      return this.buttonsDock.getHeight() / (double)this.buttonsDock.getChildren().size();
    }, new Observable[]{this.buttonsDock.heightProperty(), this.buttonsDock.getChildren()}));
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

  public void onLobbyOpenHandle() {
    this.saveMenuState();
    this.windowContainer = new WindowContainerComponent(this, this.lobby, "Lobbies");
    this.fitWindow();
    this.menuDock.getChildren().add(this.windowContainer);
    this.windowContainer.onWindowOpen();
  }

  public void onSettingOpenHandle() {
    this.saveMenuState();
    this.windowContainer = new WindowContainerComponent(this, this.settings, "Settings");
    this.fitWindow();
    this.tempMenu.remove(this.settings);
    this.menuDock.getChildren().add(this.windowContainer);
    this.windowContainer.onWindowOpen();
  }

  private void fitWindow() {
    Platform.runLater(() -> {
      this.windowContainer.prefHeightProperty().bind(this.playArea.prefHeightProperty());
      this.windowContainer.prefWidthProperty().bind(this.playArea.prefWidthProperty());
    });
  }

  private void saveMenuState() {
    if (this.menuDock.getChildren().size() != 0) {
      ObservableList var10000 = this.menuDock.getChildren();
      List var10001 = this.tempMenu;
      this.tempMenu.getClass();
      var10000.forEach(var10001::add);
      this.menuDock.getChildren().clear();
    }

    this.menuDock.getChildren().remove(this.menu);
    if (this.menuHolder.isMouseTransparent()) {
      this.menuHolder.setMouseTransparent(false);
    }

  }

  public void onWindowClose() {
    this.menuDock.getChildren().clear();
    if (this.chatController.isInGame()) {
      this.menuHolder.setMouseTransparent(true);
    } else if (this.tempMenu != null && this.tempMenu.size() != 0) {
      List var10000 = this.tempMenu;
      ObservableList var10001 = this.menuDock.getChildren();
      var10000.forEach(var10001::add);
      this.tempMenu.clear();
      this.menuHolder.setMouseTransparent(false);
    } else {
      this.menuHolder.setMouseTransparent(true);
    }

  }

  public AnchorPane getPlayArea() {
    return this.playArea;
  }

  public void onMapOpenHandle() {
    Platform.runLater(() -> {
      this.menuDock.getChildren().clear();
      this.menuHolder.setMouseTransparent(true);
      this.chatController.setInGame(true);
      this.playArea.getChildren().add(this.game);
      this.playArea.setMouseTransparent(false);
      this.showLeaveButton(false, "Leave");
    });
  }

  private void showLeaveButton(boolean b, String leave) {
    this.leaveButton.setDisable(b);
    this.leaveButton.setText(leave);
  }

  public void onMapCloseHandle() {
    this.playArea.getChildren().clear();
    this.playArea.setMouseTransparent(true);
    this.gameController.clearMap();
    this.chatController.setInGame(false);
    this.gameController.clearToastDock();
    this.menuHolder.setMouseTransparent(false);
    this.menuDock.getChildren().add(this.menu);
    this.hideLeaveButton();
  }

  private void hideLeaveButton() {
    this.showLeaveButton(true, "");
  }

  public void setTps(double tps) {
    Platform.runLater(() -> {
      this.tpsProperty.setValue(tps);
    });
  }

  public void removeWindow() {
    this.menuDock.getChildren().clear();
    this.menuDock.getChildren().add(this.menu);
    if (this.menuHolder.isMouseTransparent()) {
      this.menuHolder.setMouseTransparent(false);
    }

  }

  public void sendRecipiantToChat(String recipiant) {
    this.chatController.writeAt(recipiant);
  }

  public GameController getGameController() {
    return this.gameController;
  }

  public void onHighscoreHandle() {
    this.chatController.requestHighscores();
    this.saveMenuState();
    this.windowContainer = new WindowContainerComponent(this, this.highscore, "Highscore");
    this.highscore.prefWidthProperty().bind(this.windowContainer.widthProperty());
    this.highscore.setMaxHeight(400.0D);
    this.highscore.setPrefViewportHeight(400.0D);
    this.fitWindow();
    this.menuDock.getChildren().add(this.windowContainer);
    this.windowContainer.onWindowOpen();
  }

  public void onUpdateHighscoreHandle(List<HighScore> highScores) {
    this.highscore.update(highScores);
  }

  public Stage getScene() {
    return this.scene;
  }

  public void setScene(Stage scene) {
    this.scene = scene;
  }

  public void setAmountBox(TextField actual, Button3dComponent sendMother, Button3dComponent send) {
    this.amountShown.set(true);
    this.middleBoardDock.add(actual, 0, 0);
    actual.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)}));
    actual.setAlignment(Pos.CENTER);
    actual.setFont(new Font("PxPlus IBM VGA9", 30.0D));
    this.middleBoardDock.add(sendMother, 0, 1);
    this.middleBoardDock.add(send, 0, 2);
  }

  public void resetAmounBox() {
    this.amountShown.set(false);
    this.clearMiddleBoard();
  }

  public void setWindowTitle(String text) {
    this.windowContainer.setTitle(text);
  }
}
