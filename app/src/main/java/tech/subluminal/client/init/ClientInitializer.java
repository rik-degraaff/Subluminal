package tech.subluminal.client.init;

import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.logic.ChatManager;
import tech.subluminal.client.logic.GameManager;
import tech.subluminal.client.logic.LobbyManager;
import tech.subluminal.client.logic.PingManager;
import tech.subluminal.client.logic.UserManager;
import tech.subluminal.client.presentation.controller.ChatController;
import tech.subluminal.client.presentation.controller.GameController;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.customElements.LobbyComponent;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.InMemoryGameStore;
import tech.subluminal.client.stores.InMemoryLobbyStore;
import tech.subluminal.client.stores.InMemoryPingStore;
import tech.subluminal.client.stores.InMemoryUserStore;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.PingStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.SocketConnection;

/**
 * Assembles the client-side architecture.
 */
public class ClientInitializer extends Application {

  public static MainController controller;
  public FXMLLoader loader;

  /**
   * Initializes the assembling.
   *
   * @param server address of the server(hostname or ip).
   * @param port of the server.
   * @param username initial username to request from the server.
   */
  public static void init(String server, int port, String username, boolean debug) {
    Logger.info("Starting client ...");
    Socket socket = null;
    try {
      socket = new Socket(server, port);
    } catch (IOException e) {
      //TODO: Proper error handling
      e.printStackTrace();
    }
    Connection connection = new SocketConnection(socket);
    connection.start();

    UserStore userStore = new InMemoryUserStore();
    PingStore pingStore = new InMemoryPingStore();
    LobbyStore lobbyStore = new InMemoryLobbyStore();

    ChatController chatPresenter = controller.getChatController();

    chatPresenter.setUserStore(userStore);
    controller.setUserStore(userStore);

    UserManager userManager = new UserManager(connection, userStore, chatPresenter);
    new ChatManager(userStore, chatPresenter, connection);
    new PingManager(connection, pingStore);

    LobbyComponent lobbyPresenter = controller.getLobby();
    LobbyManager lobbyManager = new LobbyManager(lobbyStore, connection, lobbyPresenter,
        controller);

    lobbyPresenter.setLobbyStore(lobbyStore);
    lobbyPresenter.setUserStore(userStore);

    GameStore gameStore = new InMemoryGameStore();
    GameController gamePresenter = controller.getGameController();
    GameManager gameManager = new GameManager(gameStore, connection, gamePresenter);
    gamePresenter.setUserStore(userStore);
    gamePresenter.setGameStore(gameStore);

    userManager.start(username);

    final Thread mainThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      userManager.logout();
      try {
        mainThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }));
  }


  /**
   * The main entry point for all JavaFX applications. The start method is called after the init
   * method has returned, and after the system is ready for the application to begin running.
   *
   * <p> NOTE: This method is called on the JavaFX Application Thread. </p>
   *
   * @param primaryStage the primary stage for this application, onto which the application scene
   * can be set. The primary stage will be embedded in the browser if the application was launched
   * as an applet. Applications may create other stages, if needed, but they will not be primary
   * stages and will not be embedded in the browser.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    loader = new FXMLLoader();
    loader.setLocation(
        getClass()
            .getResource("/tech/subluminal/client/presentation/customElements/MainView.fxml"));
    //loader.setController(new MainController());
    Parent root = loader.load();
    root.getStylesheets().add(
        getClass().getResource("/tech/subluminal/client/presentation/style/lobby.css")
            .toExternalForm());

    controller = (MainController) loader.getController();

    primaryStage.setTitle("Subluminal - The Game");
    primaryStage.setScene(new Scene(root));
    primaryStage.getIcons().add(new Image("/tech/subluminal/resources/Game_Logo_1.png"));
    primaryStage.setMaximized(true);
    primaryStage.show();

    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode() == KeyCode.F11) {
        if (primaryStage.isFullScreen()) {
          primaryStage.setFullScreen(false);
        } else {
          primaryStage.setFullScreen(true);
        }
      }
    });

    PerspectiveCamera camera = new PerspectiveCamera();
    primaryStage.getScene().setCamera(camera);
    //camera.setTranslateZ(-1000);


    String[] cmd = getParameters().getRaw().toArray(new String[4]);

    init(cmd[0], Integer.parseInt(cmd[1]), cmd[2], Boolean.getBoolean(cmd[3]));

    primaryStage.widthProperty().addListener((v, oldV, newV) -> {
      int diff = oldV.intValue() - newV.intValue();
      controller.onWindowResizeHandle(diff, 0);
    });

    primaryStage.heightProperty().addListener((v, oldV, newV) -> {
      int diff = oldV.intValue() - newV.intValue();
      controller.onWindowResizeHandle(0, diff);
    });
  }

  /**
   * Gets called when the window is closed.
   */
  public void stop() {
    //TODO: Log out
    //System.exit(0);
  }

}
