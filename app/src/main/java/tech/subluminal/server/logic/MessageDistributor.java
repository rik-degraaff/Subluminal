package tech.subluminal.server.logic;

import java.util.Set;
import java.util.function.BiConsumer;
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
   * @param connectionIDs the ids of teh connections to use.
   */
  void sendMessage(SONRepresentable message, Set<String> connectionIDs);

  /**
   * Allows user of this class to react to a new connection being created.
   *
   * @param handler a function which takes a connectionID and a connection and does something with
   * it.
   */
  void addConnectionOpenedHandler(BiConsumer<String, Connection> handler);
}
