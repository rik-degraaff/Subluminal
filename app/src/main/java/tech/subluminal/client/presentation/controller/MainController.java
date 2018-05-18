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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
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
  List<Node> tempMenu = new ArrayList<>();
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
  @FXML
  private AnchorPane menuHolder;
  @FXML
  private AnchorPane introPane;
  @FXML
  private HBox introBoxHolder;
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
  private DebugComponent debug;
  private MonitorComponent monitor;
  private HighscoreComponent highscore;
  private DisplayComponent display;
  private Timeline chatUpTl = new Timeline();

  private KeyMap keyMap = new KeyMap();

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
    introPane.setBackground(
        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    Label introText = new Label();
    introText.setTextAlignment(TextAlignment.CENTER);
    introText.setTranslateY(-100);
    introText.setWrapText(true);
    introText.getStyleClass().addAll("console-red", "intro-text");
    introBoxHolder.getChildren().addAll(introText);

    String introStory = "In a basement just around your corner, we once were created.\nAnd now we are set to conquer the galaxy...";

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

    window.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        clearIntro(timeTl);
      }
    });

    mainTl.setOnFinished(e -> {
      clearIntro(timeTl);
    });

    //animation.play();

    background = new BackgroundComponent(1000);
    spaceBackgroundDock.getChildren().add(background);

    CockpitComponent cockpit = new CockpitComponent();
    cockpitDock.getChildren().addAll(cockpit);
    cockpit.setVisible(true);

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

    userList = new UserListComponent(this, display);
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

    Button3dComponent settingButton = new Button3dComponent("S");
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

    buttonsDock.add(settingButton, 0, 0);
    buttonsDock.add(playerListButton, 0, 1);
    buttonsDock.add(nameChangeButton, 0, 2);

    bindDockButtons(settingButton);
    bindDockButtons(playerListButton);
    bindDockButtons(nameChangeButton);

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
      } else if (keyEvent.getCode() == KeyCode.C) {
        toggleChat();
      }
    });

    window.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
      if(keyEvent.getCode().getName().equals(keyMap.getKeyMap().get("chat").getValue())){
        togg
      }
    });

  }

  private void toggleChat() {
    if (chatDown) {
      openChat();
    } else {
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

  public void onWindowResizeHandle(int diffX, int diffY) {
    background.onWindowResize(diffX, diffY);
  }

  public void onLobbyOpenHandle() {
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, lobby, "Lobbies");
    //lobby.setUserActive();

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onSettingOpenHandle() {
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, settings, "Settings");

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
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

  public void onMapOpenHandle() {
    Platform.runLater(() -> {

      menuDock.getChildren().clear();

      menuHolder.setMouseTransparent(true);

      //rightSideDock.getChildren().clear();

      Button3dComponent leave = new Button3dComponent("LEAVE");
      leave.prefHeightProperty().bind(Bindings
          .createDoubleBinding(() -> buttonsDock.getHeight() / buttonsDock.getChildren().size(),
              buttonsDock.heightProperty(), buttonsDock.getChildren()));
      leave.prefWidthProperty().bind(buttonsDock.widthProperty());
      leave.setOnMouseClicked(event -> {
        gameController.leaveGame();
        Logger.debug("LEAVE PLZ");
      });

      buttonsDock.addRow(3);
      buttonsDock.add(leave, 0, 3);
      //rightSide.getChildren().add(new Label("this is a test"));

      chatController.setInGame(true);
      playArea.getChildren().add(game);
    });
  }

  public void onMapCloseHandle() {
    playArea.getChildren().clear();

    playArea.setMouseTransparent(true);
    gameController.clearMap();
    chatController.setInGame(false);

    menuHolder.setMouseTransparent(false);

    menuDock.getChildren().add(menu);
  }

  public void removeWindow() {
    menuDock.getChildren().clear();

    menuDock.getChildren().add(menu);

    if (menuHolder.isMouseTransparent()) {
      menuHolder.setMouseTransparent(false);
    }
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
    saveMenuState();

    windowContainer = new WindowContainerComponent(this, highscore, "Highscore");

    menuDock.getChildren().add(windowContainer);
    windowContainer.onWindowOpen();
  }

  public void onUpdateHighscoreHandle(List<HighScore> highScores) {
    highscore.update(highScores);
  }
}
