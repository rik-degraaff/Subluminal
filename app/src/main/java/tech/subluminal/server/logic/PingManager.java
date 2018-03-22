package tech.subluminal.server.logic;

import tech.subluminal.server.stores.PingStore;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Manages the pings that are sent to and received from the clients
 */
public class PingManager {

  private final PingStore pingStore;
  private final ReadOnlyUserStore userStore;
  private final MessageDistributor distributor;

  public PingManager(PingStore pingStore, ReadOnlyUserStore userStore, MessageDistributor distributor) {
    this.pingStore = pingStore;
    this.userStore = userStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedHandler(this::attachHandlers);

    Thread pingThread = new Thread(this::pingLoop);
    pingThread.setDaemon(true);
    pingThread.start();
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(Pong.class, Pong::fromSON, pong -> pongReceived(pong, id));
    connection.registerHandler(Ping.class, Ping::fromSON, ping -> pingReceived(ping, connection));
  }

  private void pongReceived(Pong pong, String userID) {
    synchronized (userStore) {
      pingStore.removePing(userID, pong.getId());
    }
  }

  private void pingReceived(Ping ping, Connection connection) {
    connection.sendMessage(new Pong(ping.getId()));
  }

  private void pingLoop() {
    
  }
}
