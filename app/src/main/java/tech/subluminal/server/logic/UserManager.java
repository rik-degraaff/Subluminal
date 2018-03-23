package tech.subluminal.server.logic;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.UsernameReq;
import tech.subluminal.shared.messages.UsernameRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.User;

/**
 * Manages the information of connected users.
 */
public class UserManager {

  private UserStore userStore;

  /**
   * Creates a user manager from a user store and a message distributor.
   *
   * @param userStore the user store to save users in.
   * @param distributor the message distributor this manager communicates over.
   */
  public UserManager(UserStore userStore, MessageDistributor distributor) {
    this.userStore = userStore;


    distributor.addConnectionOpenedListener(this::attachHandlers);
    distributor.addConnectionClosedListener(this::onConnectionClosed);
  }

  private void onConnectionClosed(String id) {
    synchronized (userStore){
      userStore.removeUserByID(id);
    }
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(LoginReq.class, LoginReq::fromSON,
        req -> onLogin(id, connection, req));
    connection.registerHandler(UsernameReq.class, UsernameReq::fromSON,
        req -> onUsernameChange(id, connection, req));
    connection.registerHandler(LogoutReq.class, LogoutReq::fromSON,
        req -> onLogout(connection));
  }

  private void onLogout(Connection connection) {
    try {
      connection.close();
    } catch (IOException e) {
      //TODO: handle this accordingly
    }
  }

  /**
   * This function handles a user trying to change their username on the server.
   *
   * @param id the id of the user/connection as determined by the message distributor.
   * @param connection the connection belonging to the user.
   * @param usernameReq the username change request sent by the user.
   */
  private void onUsernameChange(String id, Connection connection, UsernameReq usernameReq) {
    String username = usernameReq.getUsername();

    username = getUnusedUsername(username);
    userStore.updateUser(new User(username, id));

    connection.sendMessage(new UsernameRes(username));

  }

  /**
   * This function handles a user trying to log in to the server.
   *
   * @param id the id of the user/connection as determined by the message distributor.
   * @param connection the connection belonging to the user.
   * @param loginReq the login request sent by the user.
   */
  private void onLogin(String id, Connection connection, LoginReq loginReq) {
    String username = loginReq.getUsername();

    username = getUnusedUsername(username);
    userStore.addUser(new User(username, id));

    connection.sendMessage(new LoginRes(username, id));
  }

  private String getUnusedUsername(String requestedUsername) {
    Set<String> users = userStore.getUsers()
        .stream()
        .map(User::getUsername)
        .collect(Collectors.toSet());

    String username = requestedUsername;
    int i = 1;
    while (users.contains(username)) {
      username = requestedUsername + i;
      i++;
    }
    return username;
  }
}
