package tech.subluminal.shared.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

public class SocketConnection implements Connection {

  Socket socket;

  public SocketConnection(Socket socket) {
    this.socket = socket;
    new Thread(this::inStreamLoop).start();
  }

  private void inStreamLoop() {

    try {
      Scanner scanner = new Scanner(socket.getInputStream());

      while (true) {
        String message = scanner.nextLine();
        //= message.split(" ")[0];
      }
    } catch (IOException e) {
      System.err.println(e.toString());
      System.exit(1);
    }
  }

  /**
   * Registers a handler which is called when a provided type of SONRepresentable is received. A
   * call to this function should ideally look something like this:
   * <pre>
   *     connection.registerHandler(MyMessage.class, MyMessage::fromSON, this::handleMyMessage)
   * </pre>
   *
   * @param type the class of the type of SONRepresentable this handler responds to.
   */
  @Override
  public <T extends SONRepresentable> void registerHandler(Class<T> type, SONConverter<T> converter,
      Consumer<T> handler) {

  }

  /**
   * Sends a message over the connection.
   *
   * @param message the message to send.
   */
  @Override
  public void sendMessage(SONRepresentable message) {

  }

  /**
   * Closes this stream and releases any system resources associated with it. If the stream is
   * already closed then invoking this method has no effect.
   *
   * <p> As noted in {@link AutoCloseable#close()}, cases where the close may fail require careful
   * attention. It is strongly advised to relinquish the underlying resources and to internally
   * <em>mark</em> the {@code Closeable} as closed, prior to throwing the {@code IOException}.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {

  }
}
