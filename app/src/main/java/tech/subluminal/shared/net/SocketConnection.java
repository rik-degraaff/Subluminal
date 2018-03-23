package tech.subluminal.shared.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.son.SONRepresentable;

public class SocketConnection implements Connection {

  private Socket socket;
  private Map<String, Set<Consumer<SON>>> handlers = new HashMap<>();
  private Set<Runnable> closeListeners = new HashSet<>();
  private volatile boolean stop = false;
  private Thread readThread;

  public SocketConnection(Socket socket) {
    this.socket = socket;
  }

  private void inStreamLoop() {

    try {
      Scanner scanner = new Scanner(socket.getInputStream());

      while (!stop) {
        try {
          String message = scanner.nextLine();
          String[] parts = message.split(" ", 2);
          if (parts.length == 2) {
            SON son = SON.parse(parts[1]);
            Set<Consumer<SON>> consumers = handlers.get(parts[0]);
            if (consumers != null) {
              consumers.forEach(c -> c.accept(son));
            }
          }
        } catch (SONParsingError e) {
          System.out.println("Parsing of " + e.getMessage() + "failed"); //TODO: log better
        }
      }
    } catch (IOException e) {
      //TODO: Handle client disconnect
      System.err.println(e.toString());
      System.exit(1);
    }
    closeListeners.forEach(Runnable::run);
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
    String method = type.getSimpleName();
    if (handlers.get(method) == null) {
      handlers.put(method, new HashSet<>());
    }
    handlers.get(method).add(son -> handleMessage(converter, handler, son));
  }

  private static <T extends SONRepresentable> void handleMessage(SONConverter<T> converter,
      Consumer<T> handler, SON son) {
    try {
      handler.accept(converter.convert(son));
    } catch (SONConversionError sonConversionError) {
      System.err.println(
          "Structure of " + sonConversionError.getMessage() + "packets was incorrect, son.");
    }
  }

  /**
   * Sends a message over the connection.
   *
   * @param message the message to send.
   */
  @Override
  public void sendMessage(SONRepresentable message) {
    try {
      OutputStream out = socket.getOutputStream();
      String typeName = message.getClass().getSimpleName();
      String msg = message.asSON().asString();
      synchronized (out) {
        new PrintStream(out).println(typeName + " " + msg);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a listener, which can react to this connection closing.
   *
   * @param listener will be run when the connection is closed.
   */
  @Override
  public void addCloseListener(Runnable listener) {
    closeListeners.add(listener);
  }

  /**
   * Tells the connection that it can start listening for messages.
   */
  @Override
  public void start() {
    readThread = new Thread(this::inStreamLoop);
    readThread.start();
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
    stop = true;
    readThread.interrupt();
  }
}
