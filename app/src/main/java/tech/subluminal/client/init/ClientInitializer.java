package tech.subluminal.client.init;

import java.io.IOException;
import java.net.Socket;
import tech.subluminal.client.logic.ChatManager;
import tech.subluminal.client.logic.UserManager;
import tech.subluminal.client.presentation.ConsolePresenter;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.client.stores.InMemoryUserStore;
import tech.subluminal.client.stores.UserStore;
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

    ConsolePresenter presenter = new ConsolePresenter(System.in, System.out, userStore);

    UserManager userManager = new UserManager(connection, userStore, presenter);
    new ChatManager(userStore, presenter, connection);

    userManager.start(username);
  }
}
