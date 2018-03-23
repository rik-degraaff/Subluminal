package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.UsernameReq;
import tech.subluminal.shared.messages.UsernameRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.User;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Manages the information of the active user.
 */
public class UserManager implements UserPresenter.Delegate {

  private Connection connection;
  private final UserStore userStore;
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
    connection.registerHandler(UsernameRes.class, UsernameRes::fromSON, this::onUsernameChanged);
  }

  private void onUsernameChanged(UsernameRes res) {
    synchronized (userStore){
      userStore.setCurrentUser(new User(res.getUsername(), userStore.getCurrentUser().getId()));
    }
    userPresenter.nameChangeSucceeded();
  }

  private void onLogin(LoginRes res) {
    synchronized (userStore){
      userStore.setCurrentUser(new User(res.getUsername(), res.getUserID()));
    }
    userPresenter.loginSucceeded();

  }

  /**
   * Sends a UsernameReq to the server.
   *
   * @param username the desired new username.
   */
  @Override
  public void changeUsername(String username) {
    connection.sendMessage(new UsernameReq(username));
  }

  /**
   * Fired when a user has to be logged out.
   */
  @Override
  public void logout() {
    connection.sendMessage(new LogoutReq());
  }
}
