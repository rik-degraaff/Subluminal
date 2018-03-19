package tech.subluminal.client.presentation;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import jdk.internal.util.xml.impl.Input;
import tech.subluminal.client.stores.ReadOnlyUserStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.User;

/**
 * Handles
 */
public class ConsolePresenter implements UserPresenter {

  private InputStream in;
  private PrintStream out;
  private ReadOnlyUserStore userStore;

  public ConsolePresenter(InputStream in, PrintStream out, ReadOnlyUserStore userStore) {
    this.in = in;
    this.out = out;
    this.userStore = userStore;

    new Thread(this::inputLoop).start();
  }

  public void inputLoop() {
    Scanner scanner = new Scanner(in);

    while (true) {
      String command = scanner.nextLine();
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

}