package tech.subluminal.client.init;

import tech.subluminal.client.logic.UserManager;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.client.stores.InMemoryUserStore;
import tech.subluminal.client.stores.UserStore;

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
    Connection connection = null; // new ClientSocketConnection(server, port); TODO: uncomment this
    UserStore userStore = new InMemoryUserStore();

    UserPresenter userPresenter = null; //new ConsoleUserPresenter(userStore);

    UserManager userManager = new UserManager(connection, userStore);

    userManager.start(username);
  }
}
