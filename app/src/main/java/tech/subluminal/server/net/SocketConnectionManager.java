package tech.subluminal.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.net.ConnectionManager;
import tech.subluminal.shared.net.SocketConnection;

public class SocketConnectionManager implements ConnectionManager {
  ServerSocket serverSocket;

  public SocketConnectionManager(int port) {
    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    new Thread(this::portListenLoop).start();
  }

  private void portListenLoop() {
    try {
      System.out.println("Waiting for connection on port " + serverSocket.getLocalPort() + "...");

      while (true) {
        Socket socket = serverSocket.accept();
        new SocketConnection(socket);
      }
    } catch (IOException e) {
      e.printStackTrace();;
      System.exit(1);
    }
  }

  /**
   * Allows users of this class to react to a connection being opened.
   *
   * @param handler is called when a new Connection is opened.
   */
  @Override
  public void addConnectionOpenedHandler(Consumer<Connection> handler) {

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
