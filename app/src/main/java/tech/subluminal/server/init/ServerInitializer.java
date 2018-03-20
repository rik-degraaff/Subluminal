package tech.subluminal.server.init;

import tech.subluminal.server.logic.ConnectionMessageDistributor;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.logic.UserManager;
import tech.subluminal.server.stores.InMemoryUserStore;
import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.net.ConnectionManager;

/**
 * Assembles the server-side architecture.
 */
public class ServerInitializer {

  public static void init(int port) {
    ConnectionManager connectionManager = null; // new ServerSocketConnectionManager(port) TODO: uncomment this

    MessageDistributor messageDistributor = new ConnectionMessageDistributor(connectionManager);

    UserStore userStore = new InMemoryUserStore();

    new UserManager(userStore, messageDistributor);
  }
}
