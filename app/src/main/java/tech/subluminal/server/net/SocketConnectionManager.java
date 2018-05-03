package tech.subluminal.server.net;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.ConnectionManager;
import tech.subluminal.shared.net.SocketConnection;

public class SocketConnectionManager implements ConnectionManager {

  private ServerSocket serverSocket;
  private Set<Consumer<Connection>> connectionListeners = new HashSet<>();
  private volatile boolean stop = false;

  /**
   * Builds a new socket on the specific port.
   *
   * @param port to bind the socket to.
   */
  public SocketConnectionManager(int port) {
    createSocket(port);
    new Thread(this::portListenLoop).start();
  }

  private void portListenLoop() {
    try {
      System.out.println("Waiting for connection on port " + serverSocket.getLocalPort() + "...");
      Logger.info("Waiting for connection on port " + serverSocket.getLocalPort() + "...");

      while (!stop) {
        Socket socket = serverSocket.accept();
        Connection connection = new SocketConnection(socket);
        connectionListeners.forEach(listener -> listener.accept(connection));
        connection.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Creates the server socket to listen for incoming connections.
   * @param port to listen on.
   */
  private void createSocket(int port) {
    try {
      serverSocket = new ServerSocket(port);
    } catch (BindException e) {
      System.out.println("Port " + port + " is already in use. Trying next port.");
      Logger.info("Port " + port + " is already in use. Trying next port.");
      createSocket(port+1);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Allows users of this class to react to a connection being opened.
   *
   * @param handler is called when a new Connection is opened.
   */
  @Override
  public void addConnectionOpenedHandler(Consumer<Connection> handler) {
    connectionListeners.add(handler);
  }

  /**
   * Closes this stream and releases any system tech.subluminal.resources associated with it. If the
   * stream is already closed then invoking this method has no effect.
   *
   * <p>As noted in {@link AutoCloseable#close()}, cases where the close may fail require careful
   * attention. It is strongly advised to relinquish the underlying tech.subluminal.resources and to
   * internally
   * <em>mark</em> the {@code Closeable} as closed, prior to throwing the {@code IOException}.</p>
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    stop = true;
  }
}
