package tech.subluminal.server.logic;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.net.Connection;
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
   * Allows user of this class to react to a new connection being created.
   *
   * @param handler a function which takes a connectionID and a connection and does something with
   * it.
   */
  void addConnectionOpenedHandler(BiConsumer<String, Connection> handler);
}
