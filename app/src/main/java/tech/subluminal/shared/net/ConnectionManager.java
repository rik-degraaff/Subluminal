package tech.subluminal.shared.net;

import java.io.Closeable;
import java.util.function.Consumer;

/**
 * Manages a collection of Connections.
 */
public interface ConnectionManager extends Closeable {

  /**
   * Allows users of this class to react to a connection being opened.
   *
   * @param handler is called when a new Connection is opened.
   */
  void connectionOpenedHandler(Consumer<Connection> handler);
}
