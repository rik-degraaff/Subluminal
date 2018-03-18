package tech.subluminal.client.logic;

import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.User;
import tech.subluminal.tech.subluminal.client.stores.UserStore;

/**
 * Manages the information of the active user.
 */
public class UserManager {

  Connection connection;
  UserStore userStore;

  /**
   *
   * @param connection to the server to communicate with.
   * @param userStore to hold the current users.
   */
  public UserManager(Connection connection, UserStore userStore) {
    this.connection = connection;
    this.userStore = userStore;

    attachHandlers();
  }

  /**
   * Starts the interaction with the server.
   *
   * @param username the initial username for the connection with the server.
   */
  public void start(String username) {
    connection.sendMessage(new LoginReq(username));
  }

  private void attachHandlers() {
    connection.registerHandler(LoginRes.class, LoginRes::fromSON, this::onLogin);
  }

  private void onLogin(LoginRes res) {
    userStore.setCurrentUser(new User(res.getUsername(), res.getUserID()));
  }
}
