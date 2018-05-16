package tech.subluminal.server.logic;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Assigns ids to connections and makes sure that the individual connections can be accessed.
 */
public interface MessageDistributor {

  /**
   * Sends a broadcast to all connections.
   *
   * @param message the message to send.
   */
  void broadcast(SONRepresentable message);

  /**
   * Sends a message to one connection.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection to use.
   */
  void sendMessage(SONRepresentable message, String connectionID);

  /**
   * Sends a message to multiple connections.
   *
   * @param message the message to send.
   * @param connectionIDs the ids of the connections to use.
   */
  void sendMessage(SONRepresentable message, Collection<String> connectionIDs);

  /**
   * Sends a message to all but one connected clients.
   *
   * @param message the message to send.
   * @param connectionID the id of the connection not to send to.
   */
  void sendMessageToAllExcept(SONRepresentable message, String connectionID);

  /**
   * Close a connection managed by the distributor.
   *
   * @param connectionID the id of the connection that should be closed.
   */
  void closeConnection(String connectionID);

  /**
   * Adds a listener that can react to a connection being closed.
   *
   * @param listener the listener that will receive the id of the connection upon deletion.
   */
  void addConnectionClosedListener(Consumer<String> listener);

  /**
   * Allows users of this class to react to a new connection being created.
   *
   * @param listener a function which takes a connectionID and a connection and does something with
   * it.
   */
  void addConnectionOpenedListener(BiConsumer<String, Connection> listener);

  /**
   * Allows users of this class to react to a connection trying to log in various ways.
   *
   * @param type The message type which is to be regarded as a login message.
   * @param converter a function that can convert a SON object to an object of the specified message type.
   * @param handler the handler which should be called if a message of this type is received.
   */
  <T extends SONRepresentable> void addLoginHandler(Class<T> type, SONConverter<T> converter,
      BiFunction<T, Connection, Optional<String>> handler);
}
