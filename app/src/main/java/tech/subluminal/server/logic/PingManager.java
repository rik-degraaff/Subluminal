package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.util.Set;
import java.util.stream.Collectors;
import org.pmw.tinylog.Logger;
import tech.subluminal.server.stores.PingStore;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.logic.PingResponder;
import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.GlobalSettings;
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
    connection.registerHandler(Pong.class, Pong::fromSON, this::pongReceived);
    pingResponder.attachHandlers(connection);
  }

  private void pongReceived(Pong pong) {
    pingStore.sentPings().removeByID(pong.getId());
  }

  private void pingLoop() {
    while (true) {
      try {
        Thread.sleep(PING_TIMEOUT_MILLIS);
      } catch (InterruptedException e) {
        Logger.error(e);
      }

      pingStore.sentPings().sync(() -> {
        Set<SentPing> pings = userStore.connectedUsers().getAll().use(us ->
            us.stream()
                .map(syncUser -> syncUser.use(User::getID))
                .map(id -> new SentPing(System.currentTimeMillis(), id, generateId(GlobalSettings.SHARED_UUID_LENGTH)))
                .collect(Collectors.toSet())
        );

        pings.forEach(p -> {
          distributor.sendMessage(new Ping(p.getID()), p.getUserID());
        });

        Set<String> usersWithPings = pingStore.sentPings().getUsersWithPings();
        usersWithPings.forEach(distributor::closeConnection);
        pingStore.sentPings().clear();
        pings.forEach(pingStore.sentPings()::add);
      });
    }
  }
}
