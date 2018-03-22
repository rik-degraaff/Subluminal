package tech.subluminal.client.presentation;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import tech.subluminal.client.stores.ReadOnlyUserStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.User;

/**
 * Handles the console Input and prints the received messages.
 */
public class ConsolePresenter implements UserPresenter, ChatPresenter {

  private InputStream in;
  private PrintStream out;
  private ReadOnlyUserStore userStore;
  private ChatPresenter.Delegate chatDelegate;
  private UserPresenter.Delegate userDelegate;

  public ConsolePresenter(InputStream in, PrintStream out, ReadOnlyUserStore userStore) {
    this.in = in;
    this.out = out;
    this.userStore = userStore;

    new Thread(this::inputLoop).start();
  }

  public void inputLoop() {
    Scanner scanner = new Scanner(in);

    while (true) {
      String line = scanner.nextLine();
      char command = line.charAt(0);
      if (command == '@') {

        String channel = line.split(" ", 1)[0].substring(1).toLowerCase();
        if (channel.equals("all")) {
          //send @all
        } else if (channel.equals("game")) {

        } else {
          if ( !=null){
            chatDelegate.
            //TODO: Implement this
          }
        }
      } else if (command == '/') {
        chatDelegate.
      } else {
        //send @all
        chatDelegate.sendGlobalMessage(line);
      }
    }
  }


  /**
   * Function that should be called when login succeeded.
   */
  @java.lang.Override
  public void loginSucceeded() {
    String username = userStore.getCurrentUser().getUsername();

    synchronized (out) {
      out.println("Successfully logged in as " + username);
    }
  }

  @Override
  public void setUserDelegate(UserPresenter.Delegate delegate) {
    this.userDelegate = delegate;
  }

  /**
   * Fired when a someone sends a message to all users on the server.
   *
   * @param message is the text of the message.
   * @param username from the sender of the message.
   */
  @Override
  public void GlobalMessageReceived(String message, String username) {
    out.println("Server /" + username + ": " + message);
  }

  /**
   * Fired when a personal message is received.
   *
   * @param message of the received whisper.
   * @param username of the sender.
   */
  @Override
  public void WhisperMessageReceived(String message, String username) {
    out.println(username + "@ you" + ": " + message);
  }

  /**
   * Fired when a message from the same game is received.
   *
   * @param message of the game message.
   * @param username fo the sender.
   */
  @Override
  public void GameMessageReceived(String message, String username) {
    out.println("Game /" + username + ": " + message);
  }

  /**
   *
   * @param delegate
   */
  @Override
  public void setChatDelegate(Delegate delegate) {
    this.chatDelegate = delegate;
  }
}