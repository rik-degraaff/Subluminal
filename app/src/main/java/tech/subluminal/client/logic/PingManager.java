package tech.subluminal.client.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.io.IOException;
import tech.subluminal.client.stores.PingStore;
import tech.subluminal.shared.logic.PingResponder;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.SentPing;


public class PingManager {

  public static final int PING_TIMEOUT_KEY = 6000;
  private final PingStore pingStore;
  private final Connection connection;

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
          if (pingStore.getPing() != null) {
            connection.sendMessage(new LogoutReq());
            try {
              connection.close();
            } catch (IOException e) {
              e.printStackTrace();
              //TODO: handle this accordingly
            }
          } else {
            SentPing ping = new SentPing(System.currentTimeMillis(), generateId(6));
            pingStore.setPing(ping);
            connection.sendMessage(new Ping(ping.getId()));
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
    synchronized (pingStore) {
      //pingStore.getPing().getSentTime();
      //TODO: calculate ping and store in pingstore
      pingStore.setPing(null);
    }

  }

}
