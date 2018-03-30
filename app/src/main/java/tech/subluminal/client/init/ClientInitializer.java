package tech.subluminal.client.init;

import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import tech.subluminal.client.logic.ChatManager;
import tech.subluminal.client.logic.PingManager;
import tech.subluminal.client.logic.UserManager;
import tech.subluminal.client.presentation.ConsolePresenter;
import tech.subluminal.client.presentation.UiPresenter;
import tech.subluminal.client.stores.InMemoryPingStore;
import tech.subluminal.client.stores.InMemoryUserStore;
import tech.subluminal.client.stores.PingStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.SocketConnection;

/**
 * Assembles the client-side architecture.
 */
public class ClientInitializer {

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
    UiPresenter presenter = new UiPresenter();
    Application.launch(UiPresenter.class);

    UserManager userManager = new UserManager(connection, userStore, presenter);
    new ChatManager(userStore, presenter, connection);
    new PingManager(connection, pingStore);

    userManager.start(username);

    final Thread mainThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      connection.sendMessage(new LogoutReq());
      presenter.logoutSucceeded(); //TODO: handle in userManager
      try {
        mainThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }));

  }
}
