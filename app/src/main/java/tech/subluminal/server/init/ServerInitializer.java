package tech.subluminal.server.init;

import org.pmw.tinylog.Logger;
import tech.subluminal.server.logic.ChatManager;
import tech.subluminal.server.logic.ConnectionMessageDistributor;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.logic.PingManager;
import tech.subluminal.server.logic.UserManager;
import tech.subluminal.server.net.SocketConnectionManager;
import tech.subluminal.server.stores.InMemoryPingStore;
import tech.subluminal.server.stores.InMemoryUserStore;
import tech.subluminal.server.stores.PingStore;
import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.net.ConnectionManager;

/**
 * Assembles the server-side architecture.
 */
public class ServerInitializer {

  /**
   * Inizializes the server and creates the needed objects.
   *
   * @param port to bind the server to.
   */
  public static void init(int port) {
    Logger.info("Starting server ...");

    ConnectionManager connectionManager = new SocketConnectionManager(port);

    MessageDistributor messageDistributor = new ConnectionMessageDistributor(connectionManager);

    UserStore userStore = new InMemoryUserStore();
    PingStore pingStore = new InMemoryPingStore();

    new UserManager(userStore, messageDistributor);
    new PingManager(pingStore, userStore, messageDistributor);
    new ChatManager(messageDistributor, userStore);
  }
}
