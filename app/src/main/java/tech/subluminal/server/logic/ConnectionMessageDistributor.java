package tech.subluminal.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.ConnectionManager;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Assigns ids to connections and makes sure that the individual connections can be accessed.
 */
public class ConnectionMessageDistributor implements MessageDistributor {

  private final ConnectionManager connectionManager;
  Map<String, Connection> connections = new HashMap<>();
  List<BiConsumer<String, Connection>> connectionOpenedHandlers = new ArrayList<>();

  /**
   * Creates a connection message mistributor from a connection manager.
   *
   * @param connectionManager the connection manager this distribution.
   */
  public ConnectionMessageDistributor(ConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
    connectionManager.addConnectionOpenedHandler(this::connectionOpened);
  }

  private void connectionOpened(Connection connection) {
    String id = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    connections.put(id, connection);
    connectionOpenedHandlers.forEach(handler -> handler.accept(id, connection));
  }

  /**
   * Sends a broadcast to all connections.
   *
   * @param message the message to send.
   */
  @Override
  public void broadcast(SONRepresentable message) {
    connections.values().forEach(c -> c.sendMessage(message));
  }

  /**
   * Sends a message to one connection.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection to use.
   */
  @Override
  public void sendMessage(SONRepresentable message, String connectionID) {
    Connection cnx = connections.get(connectionID);
    if (cnx == null) return;
    cnx.sendMessage(message);
  }

  /**
   * Sends a message to multiple connections.
   *
   * @param message the message to send.
   * @param connectionIDs the ids of teh connections to use.
   */
  @Override
  public void sendMessage(SONRepresentable message, Set<String> connectionIDs) {
    connectionIDs.forEach(id -> sendMessage(message, id));
  }

  /**
   * Allows user of this class to react to a new connection being created.
   *
   * @param handler a function which takes a connectionID and a connection and does something with
   * it.
   */
  @Override
  public void addConnectionOpenedHandler(BiConsumer<String, Connection> handler) {
    connectionOpenedHandlers.add(handler);
  }
}
