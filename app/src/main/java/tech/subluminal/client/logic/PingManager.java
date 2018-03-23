package tech.subluminal.client.logic;

import tech.subluminal.shared.logic.PingResponder;
import tech.subluminal.shared.net.Connection;

public class PingManager {

  public PingManager(Connection connection) {
    new PingResponder().attachHandlers(connection);
    
  }

}
