package tech.subluminal.shared.net;

import java.io.Closeable;
import java.util.function.Consumer;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a channel over which SONRepresentable objects can be sent and received.
 */
public interface Connection extends Closeable {

  /**
   * Registers a handler which is called when a provided type of SONRepresentable is received. A
   * call to this function should ideally look something like:
   * <pre>
   *     connection.registerHandler(MyMessage.class, MyMessage::fromSON, this::handleMyMessage)
   * </pre>
   *
   * @param type the class of the type of SONRepresentable this handler responds to.
   * @param converter a function that can convert a SON object to an object of the specified message type.
   * @param handler the handler which should be called if a message of this type is received.
   */
  <T extends SONRepresentable> void registerHandler(Class<T> type, SONConverter<T> converter,
      Consumer<T> handler);

  /**
   * Sends a message over the connection.
   *
   * @param message the message to send.
   */
  void sendMessage(SONRepresentable message);

  /**
   * Sends a message over the connection.
   *
   * @param type the type of the message.
   * @param message the message to send.
   */
  void sendMessage(String type, String message);

  /**
   * Adds a listener, which can react to this connection closing.
   *
   * @param listener will be run when the connection is closed.
   */
  void addCloseListener(Runnable listener);

  /**
   * Tells the connection that it can start listening for messages.
   */
  void start();
}
