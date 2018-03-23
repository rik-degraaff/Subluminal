package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.User;
import tech.subluminal.client.stores.UserStore;

/**
 * Manages the information of the active user.
 */
public class UserManager implements UserPresenter.Delegate {

  private Connection connection;
  private UserStore userStore;
  private UserPresenter userPresenter;

  /**
   * @param connection to the server to communicate with.
   * @param userStore to hold the current users.
   */
  public UserManager(Connection connection, UserStore userStore, UserPresenter userPresenter) {
    this.connection = connection;
    this.userStore = userStore;
    this.userPresenter = userPresenter;

    userPresenter.setUserDelegate(this);

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

  /**
   * Fired when a user has to be logged out.
   */
  @Override
  public void logout() {
    // TODO: initialize logout
  }
}
