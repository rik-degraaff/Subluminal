package tech.subluminal.server.logic;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.ConnectionManager;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Assigns ids to connections and makes sure that the individual connections can be accessed.
 */
public class ConnectionMessageDistributor implements MessageDistributor {

  private final ConnectionManager connectionManager;
  private final Map<String, Connection> connections = new HashMap<>();
  private final Set<BiConsumer<String, Connection>> connectionOpenedHandlers = new HashSet<>();
  private final Set<Consumer<String>> connectionClosedListeners = new HashSet<>();
  private final Set<BiConsumer<Connection, Consumer<String>>> loginHandlers = new HashSet<>();

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
    loginHandlers.forEach(loginHandler -> {
      loginHandler.accept(connection, id -> {
        connections.put(id, connection);
        connectionOpenedHandlers.forEach(handler -> handler.accept(id, connection));
        connection.addCloseListener(() -> {
          connections.remove(id);
          connectionClosedListeners.forEach(l -> l.accept(id));
        });
      });
    });

  }

  /**
   * Sends a broadcast to all connections.
   *
   * @param message the message to send.
   */
  @Override
  public void broadcast(SONRepresentable message) {
    String type = message.getClass().getSimpleName();
    String msg = message.asSON().asString();
    connections.values().forEach(c -> c.sendMessage(type, msg));
  }

  /**
   * Sends a message to one connection.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection to use.
   */
  @Override
  public void sendMessage(SONRepresentable message, String connectionID) {
    String type = message.getClass().getSimpleName();
    String msg = message.asSON().asString();
    sendMessage(type, msg, connectionID);
  }

  private void sendMessage(String type, String message, String connectionID) {
    Connection cnx = connections.get(connectionID);
    if (cnx == null) {
      return;
    }
    cnx.sendMessage(type, message);
  }

  /**
   * Sends a message to multiple connections.
   *
   * @param message the message to send.
   * @param connectionIDs the ids of teh connections to use.
   */
  @Override
  public void sendMessage(SONRepresentable message, Collection<String> connectionIDs) {
    String type = message.getClass().getSimpleName();
    String msg = message.asSON().asString();
    connectionIDs.forEach(id -> sendMessage(type, msg, id));
  }

  /**
   * Sends a message to all but one connected clients.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection not to send to.
   */
  @Override
  public void sendMessageToAllExcept(SONRepresentable message, String connectionID) {
    String type = message.getClass().getSimpleName();
    String msg = message.asSON().asString();
    connections.entrySet().stream()
        .filter(e -> e.getKey() != connectionID)
        .map(Entry::getValue)
        .forEach(c -> c.sendMessage(type, msg));
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
        Logger.error(e);
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

  /**
   * Allows users of this class to react to a connection trying to log in various ways.
   *
   * @param type The message type which is to be regarded as a login message.
   * @param converter a function that can convert a SON object to an object of the specified message
   * type.
   * @param handler the handler which should be called if a message of this type is received.
   */
  @Override
  public <T extends SONRepresentable> void addLoginHandler(Class<T> type, SONConverter<T> converter,
      BiFunction<T, Connection, Optional<String>> handler) {
    loginHandlers.add((connection, loginHandler) -> {
      connection.registerHandler(type, converter, msg -> {
        handler.apply(msg, connection).ifPresent(loginHandler);
      });
    });
  }
}
