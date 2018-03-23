package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.ConnectionManager;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Assigns ids to connections and makes sure that the individual connections can be accessed.
 */
public class ConnectionMessageDistributor implements MessageDistributor {

  private final ConnectionManager connectionManager;
  private final Map<String, Connection> connections = new HashMap<>();
  private final Set<BiConsumer<String, Connection>> connectionOpenedHandlers = new HashSet<>();
  private final Set<Consumer<String>> connectionClosedListeners = new HashSet<>();

  /**
   * Creates a connection message distributor from a connection manager.
   *
   * @param connectionManager the connection manager this distribution.
   */
  public ConnectionMessageDistributor(ConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
    connectionManager.addConnectionOpenedHandler(this::connectionOpened);
  }

  private void connectionOpened(Connection connection) {
    String id = generateId(6);
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
    if (cnx == null) {
      return;
    }
    cnx.sendMessage(message);
  }

  /**
   * Sends a message to multiple connections.
   *
   * @param message the message to send.
   * @param connectionIDs the ids of teh connections to use.
   */
  @Override
  public void sendMessage(SONRepresentable message, Collection<String> connectionIDs) {
    connectionIDs.forEach(id -> sendMessage(message, id));
  }

  /**
   * Sends a message to all but one connected clients.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection not to send to.
   */
  @Override
  public void sendMessageToAllExcept(SONRepresentable message, String connectionID) {
    connections.entrySet().stream()
        .filter(e -> e.getKey() != connectionID)
        .map(Entry::getValue)
        .forEach(c -> c.sendMessage(message));
  }

  /**
   * Close a connection managed by the distributor.
   *
   * @param connectionID the id of the connection that should be closed.
   */
  @Override
  public void closeConnection(String connectionID) {
    Connection connection = connections.get(connectionID);
    if (connection != null) {
      try {
        connection.close();
      } catch (IOException e) {
        e.printStackTrace(); // TODO: do something that makes sense.
      }
    }
  }

  /**
   * Adds a listener that can react to a connection being closed.
   *
   * @param listener the listener that will receive the id of the connection upon deletion.
   */
  @Override
  public void addConnectionClosedListener(Consumer<String> listener) {
    connectionClosedListeners.add(listener);
  }

  /**
   * Allows user of this class to react to a new connection being created.
   *
   * @param listener a function which takes a connectionID and a newly opened connection and does
   * something with it.
   */
  @Override
  public void addConnectionOpenedListener(BiConsumer<String, Connection> listener) {
    connectionOpenedHandlers.add(listener);
  }
}
