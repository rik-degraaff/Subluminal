package tech.subluminal.shared.logic;

import tech.subluminal.shared.messages.Ping;
import tech.subluminal.shared.messages.Pong;
import tech.subluminal.shared.net.Connection;

public class PingResponder {

  public PingResponder() {
  }

  private void pingReceived(Ping ping, Connection connection) {
    connection.sendMessage(new Pong(ping.getId()));
  }

  public void attachHandlers(Connection connection) {
    connection.registerHandler(Ping.class, Ping::fromSON,
        ping -> pingReceived(ping, connection));
  }
}