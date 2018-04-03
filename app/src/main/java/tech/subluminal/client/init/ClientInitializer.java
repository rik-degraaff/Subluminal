package tech.subluminal.client.init;

import java.io.IOException;
import java.net.Socket;

import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tech.subluminal.client.logic.ChatManager;
import tech.subluminal.client.logic.PingManager;
import tech.subluminal.client.logic.UserManager;
import tech.subluminal.client.presentation.UiPresenter;
import tech.subluminal.client.presentation.controller.ChatController;
import tech.subluminal.client.presentation.controller.LobbyController;
import tech.subluminal.client.stores.InMemoryPingStore;
import tech.subluminal.client.stores.InMemoryUserStore;
import tech.subluminal.client.stores.PingStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.SocketConnection;

import static javafx.application.Application.launch;

/**
 * Assembles the client-side architecture.
 */
public class ClientInitializer extends Application{

  public static LobbyController controller;
  public FXMLLoader loader;
  /**
   * Initializes the assembling.
   *
   * @param server address of the server(hostname or ip).
   * @param port of the server.
   * @param username initial username to request from the server.
   */
  public static void init(String server, int port, String username) {
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

    //ConsolePresenter presenter = new ConsolePresenter(System.in, System.out, userStore);
    //initGui();
    //controller.getChatController().addMessageChat("test");
    System.out.println("test");
    ChatController presenter = controller.getChatController();
    presenter.setUserStore(userStore);

    UserManager userManager = new UserManager(connection, userStore, presenter);
    new ChatManager(userStore, presenter, connection);
    new PingManager(connection, pingStore);

    userManager.start(username);

    final Thread mainThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      connection.sendMessage(new LogoutReq());
      //presenter.logoutSucceeded(); //TODO: handle in userManager
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
    loader.setLocation(getClass().getResource("/tech/subluminal/client/presentation/view/LobbyView.fxml"));
    //loader.setController(new LobbyController());
    Parent root = loader.load();
    root.getStylesheets().add(getClass().getResource("/tech/subluminal/client/presentation/view/sample.css").toExternalForm());

    controller = (LobbyController)loader.getController();

    primaryStage.setTitle("Subluminal Lobby");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    init("localhost",1790,"tester");

    //controller.getChatController().addMessageChat("hello my little bitch");
  }

}
