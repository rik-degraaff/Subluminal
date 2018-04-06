package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import tech.subluminal.server.stores.PingStore;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.logic.PingResponder;
import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.SentPing;
import tech.subluminal.shared.stores.records.User;

/**
 * Manages the pings that are sent to and received from the clients.
 */
public class PingManager {

  private static final int PING_TIMEOUT_MILLIS = 6000;
  private final PingStore pingStore;
  private final ReadOnlyUserStore userStore;
  private final MessageDistributor distributor;
  private final PingResponder pingResponder = new PingResponder();

  /**
   * Creates a ping manager with a ping store, users tore and a message distributor.
   *
   * @param pingStore the store the manager uses to read and write pings.
   * @param userStore the store the manager uses to read information about users from.
   * @param distributor the message distributor the manager receives and sends messages over.
   */
  public PingManager(PingStore pingStore, ReadOnlyUserStore userStore,
      MessageDistributor distributor) {
    this.pingStore = pingStore;
    this.userStore = userStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedListener(this::attachHandlers);

    Thread pingThread = new Thread(this::pingLoop);
    pingThread.setDaemon(true);
    pingThread.start();
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(Pong.class, Pong::fromSON, pong -> pongReceived(pong, id));
    pingResponder.attachHandlers(connection);
  }

  private void pongReceived(Pong pong, String userID) {
    synchronized (userStore) {
      pingStore.removePing(userID, pong.getId());
    }
  }

  private void pingLoop() {
    while (true) {
      try {
        Thread.sleep(PING_TIMEOUT_MILLIS);
      } catch (InterruptedException e) {
        e.printStackTrace(); // TODO: do something sensible
      }
      String id = generateId(6);
      Ping ping = new Ping(id);

      Set<String> users = userStore.connectedUsers().getAll().use(us ->
          us.stream()
          .map(syncUser -> syncUser.use(User::getId))
          .collect(Collectors.toCollection(HashSet::new))
      );

      synchronized (pingStore) {
        pingStore.getUsersWithPings().forEach(toCloseID -> distributor.closeConnection(toCloseID));
        SentPing sentPing = new SentPing(System.currentTimeMillis(), id);
        users.forEach(userId -> pingStore.addPing(userId, sentPing));
      }

      distributor.broadcast(ping);
    }
  }
}
