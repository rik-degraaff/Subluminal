package tech.subluminal.client.presentation;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import tech.subluminal.client.stores.ReadOnlyUserStore;

/**
 * Handles the console Input and prints the received messages.
 */
public class ConsolePresenter implements UserPresenter, ChatPresenter {

  static volatile boolean keepRunning = true;
  private InputStream in;
  private PrintStream out;
  private ReadOnlyUserStore userStore;
  private ChatPresenter.Delegate chatDelegate;
  private UserPresenter.Delegate userDelegate;

  /**
   * Reads and prints to the console, checks the userstore.
   *
   * @param in is InputStream from the console.
   * @param out is OuputStream from the console.
   * @param userStore to check for the current User and all active ones.
   */
  public ConsolePresenter(InputStream in, PrintStream out, ReadOnlyUserStore userStore) {
    this.in = in;
    this.out = out;
    this.userStore = userStore;

    new Thread(this::inputLoop).start();
  }

  /**
   * Reads the console constantly and handles the input accordingly.
   */
  public void inputLoop() {
    Scanner scanner = new Scanner(in);

    while (keepRunning) {
      String line = scanner.nextLine();
      char command = line.charAt(0);

      if (command == '@') {
        handleDirectedChatMessage(line);
      } else if (command == '/') {
        handleCommand(line);
      } else {
        //send @all
        chatDelegate.sendGlobalMessage(line);
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
    String newUsername = extractMessageBody(line, channel);
    //removes all whitespaces //TODO: may change later
    String username = newUsername.replaceAll(" ", "");

    if (username.equals("")) {
      synchronized (out) {
        out.println("You did not enter a new username, I got you covered, fam.");
      }
      username = "ThisisPatrick!";
    }

    userDelegate.changeUsername(username);
  }

  private void handleDirectedChatMessage(String line) {
    String channel = getSpecifier(line);
    String message = extractMessageBody(line, channel);

    if (channel.equals("all") || channel.equals(("server"))) {
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
  public void logoutSucceeded() {
    synchronized (out) {
      out.println("Successfully logged out.");
    }
    keepRunning = false;
  }

  @Override
  public void nameChangeSucceeded() {
    String username = userStore.getCurrentUser().getUsername();

    synchronized (out) {
      out.println("Successfully changed name to " + username);
    }
  }

  /**
   * Sets a userDelegate to listen to.
   */
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
    out.println("Server|" + username + ": " + message);
  }

  /**
   * Fired when a personal message is received.
   *
   * @param message of the received whisper.
   * @param username of the sender.
   */
  @Override
  public void whisperMessageReceived(String message, String username) {
    out.println(username + "@you" + ": " + message);
  }

  /**
   * Fired when a message from the same game is received.
   *
   * @param message of the game message.
   * @param username fo the sender.
   */
  @Override
  public void gameMessageReceived(String message, String username) {
    out.println("game|" + username + ": " + message);
  }


  @Override
  public void setChatDelegate(ChatPresenter.Delegate delegate) {
    this.chatDelegate = delegate;
  }
}