package tech.subluminal.client.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.io.IOException;
import java.util.Optional;
import tech.subluminal.client.stores.PingStore;
import tech.subluminal.shared.logic.PingResponder;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.GlobalSettings;
import tech.subluminal.shared.stores.records.SentPing;


public class PingManager {

  public static final int PING_TIMEOUT_KEY = GlobalSettings.SERVER_PING_TIMEOUT;
  private final PingStore pingStore;
  private final Connection connection;

  /**
   * Hand the sent Pings to the store, Waits for the Pongs and answers the received Pongs from the
   * server.
   *
   * @param pingStore stores the sent Pings.
   */
  public PingManager(Connection connection, PingStore pingStore) {
    this.pingStore = pingStore;
    this.connection = connection;
    attachHandlers(connection);
    Thread pingThread = new Thread(this::pingLoop);

    pingThread.setDaemon(true);
    pingThread.start();
  }

  private void pingLoop() {
    while (true) {
      try {
        Thread.sleep(PING_TIMEOUT_KEY);
        synchronized (pingStore) {
          if (pingStore.lastPing().get().use(Optional::isPresent)) {
            connection.sendMessage(new LogoutReq());
            try {
              connection.close();
            } catch (IOException e) {
              e.printStackTrace();
              //TODO: handle this accordingly
            }
          } else {
            SentPing ping = new SentPing(System.currentTimeMillis(), null, generateId(GlobalSettings.SHARED_UUID_LENGTH));
            pingStore.lastPing().set(ping);
            connection.sendMessage(new Ping(ping.getID()));
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        //TODO: handle sensible
      }
    }
  }

  private void attachHandlers(Connection connection) {
    new PingResponder().attachHandlers(connection);
    connection.registerHandler(Pong.class, Pong::fromSON, this::onPongReceived);
  }

  private void onPongReceived(Pong pong) {
    //pingStore.getPing().getSentTime();
    //TODO: calculate ping and store in pingstore
    pingStore.lastPing().remove();

  }

}
