package tech.subluminal.client.presentation;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import tech.subluminal.client.stores.ReadOnlyUserStore;

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
        handleDirectedChatMessage(line);
      } else if (command == '/') {
        handleCommand(line);
      } else {
        //send @all
        chatDelegate.sendGlobalMessage(line);
        System.out.println("GlobalMessage");
      }
    }
  }

  /**
   * Handles all possible commands input by the user.
   *
   * @param line is the whole command input by the user.
   */
  private void handleCommand(String line) {
    //send /cmd
    String channel = getSpecifier(line);
    if (channel.equals("logout")) {
      userDelegate.logout();
    } else if (channel.equals("changename")) {
      handleNameChangeCmd(line, channel);
    } else if (channel.equals("changelobby")) {
      //TODO: add functionality to change lobby
    }
  }

  private void handleNameChangeCmd(String line, String channel) {
    String new_username = extractMessageBody(line, channel);
    //removes all whitespaces //TODO: may change later
    String username = new_username.replaceAll(" ", "");

    userDelegate.changeUsername(username);
  }

  private void handleDirectedChatMessage(String line) {
    String channel = getSpecifier(line);
    String message = extractMessageBody(line, channel);

    if (channel.equals("all")) {
      //send @all

      chatDelegate.sendGlobalMessage(message);
    } else if (channel.equals("game")) {
      //send @game
      chatDelegate.sendGameMessage(message);
    } else {
      //send @player
      if (userStore.getUserByUsername(channel) != null) {

        chatDelegate.sendWhisperMessage(message, channel);
      }
    }
  }

  private String getSpecifier(String line) {
    return line.split(" ", 2)[0].substring(1).toLowerCase();
  }

  private String extractMessageBody(String line, String channel) {
    return line.substring(channel.length() + 1);
  }


  /**
   * Function that should be called when login succeeded.
   */
  @Override
  public void loginSucceeded() {
    String username = userStore.getCurrentUser().getUsername();

    synchronized (out) {
      out.println("Successfully logged in as " + username);
    }
  }

  @Override
  public void nameChangeSucceeded() {
    String username = userStore.getCurrentUser().getUsername();

    synchronized (out) {
      out.println("Successfully changed name to" + username);
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
  public void globalMessageReceived(String message, String username) {
    out.println("Server /" + username + ": " + message);
  }

  /**
   * Fired when a personal message is received.
   *
   * @param message of the received whisper.
   * @param username of the sender.
   */
  @Override
  public void whisperMessageReceived(String message, String username) {
    out.println(username + "@ you" + ": " + message);
  }

  /**
   * Fired when a message from the same game is received.
   *
   * @param message of the game message.
   * @param username fo the sender.
   */
  @Override
  public void gameMessageReceived(String message, String username) {
    out.println("Game /" + username + ": " + message);
  }

  /**
   *
   * @param delegate
   */
  @Override
  public void setChatDelegate(ChatPresenter.Delegate delegate) {
    this.chatDelegate = delegate;
  }
}