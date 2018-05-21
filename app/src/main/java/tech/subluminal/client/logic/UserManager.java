package tech.subluminal.client.logic;

import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.io.IOException;
import java.util.Optional;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.GameLeaveReq;
import tech.subluminal.shared.messages.InitialUsers;
import tech.subluminal.shared.messages.LobbyLeaveReq;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.PlayerJoin;
import tech.subluminal.shared.messages.PlayerLeave;
import tech.subluminal.shared.messages.PlayerUpdate;
import tech.subluminal.shared.messages.ReconnectReq;
import tech.subluminal.shared.messages.UsernameReq;
import tech.subluminal.shared.messages.UsernameRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.User;

/**
 * Manages the information of the active user.
 */
public class UserManager implements UserPresenter.Delegate {

  private final UserStore userStore;
  private Connection connection;
  private UserPresenter userPresenter;

  /**
   * Is responsible to handle the active user.
   *
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
    userStore.reconnectID().update(optID -> {
      ifPresent(optID)
          .then(id -> connection.sendMessage(new ReconnectReq(username, id)))
          .els(() -> connection.sendMessage(new LoginReq(username)));
      return Optional.empty();
    });
  }

  private void attachHandlers() {
    connection.registerHandler(LoginRes.class, LoginRes::fromSON, this::onLogin);
    connection.registerHandler(UsernameRes.class, UsernameRes::fromSON, this::onUsernameChanged);
    connection.registerHandler(PlayerJoin.class, PlayerJoin::fromSON, this::onPlayerJoin);
    connection.registerHandler(PlayerLeave.class, PlayerLeave::fromSON, this::onPlayerLeave);
    connection.registerHandler(PlayerUpdate.class, PlayerUpdate::fromSON, this::onPlayerUpdate);
    connection.registerHandler(InitialUsers.class, InitialUsers::fromSON, this::onInitialUsers);
  }

  private void onInitialUsers(InitialUsers initialUsers) {
    userStore.users().sync(() -> {
      initialUsers.getUsers().forEach(userStore.users()::add);
    });
  }

  private void onPlayerUpdate(PlayerUpdate res) {
    String id = res.getId();
    String oldUsername = userStore.users().getByID(id).get().use(User::getUsername);
    String newUsername = res.getUsername();

    userStore.users().getByID(id)
        .ifPresent(syncUser -> syncUser.update(user -> new User(newUsername, id)));

    ifPresent(userStore.users().getByID(id).map(syncUser -> syncUser.use(User::getUsername)))
        .then(System.out::println)
        .els(() -> System.out.println("User ain't here bro."));

    userPresenter.onPlayerUpdate(oldUsername, newUsername);
  }

  private void onPlayerLeave(PlayerLeave res) {
    String id = res.getId();
    String username = userStore.users().getByID(id).get().use(user -> user.getUsername());
    userStore.users().removeByID(id);

    userPresenter.onPlayerLeave(username);
  }

  private void onPlayerJoin(PlayerJoin res) {
    User user = res.getUser();
    String username = user.getUsername();
    userStore.users().add(user);

    userPresenter.onPlayerJoin(username);
  }

  private String getCurrentId() {
    return userStore.currentUser().get().use(user -> user.map(User::getID))
        .orElseThrow(() -> new IllegalStateException("Current User is not in the Userstore."));
  }

  private void onUsernameChanged(UsernameRes res) {
    userStore.currentUser().set(new User(res.getUsername(), getCurrentId()));

    userPresenter.nameChangeSucceeded();
  }

  private void onLogin(LoginRes res) {
    userStore.currentUser().set(new User(res.getUsername(), res.getUserID()));
    userStore.reconnectID().update(old -> Optional.of(res.getUserID()));

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
    logoutNoShutdown();
    System.exit(0);
  }

  /**
   * Disconnects the client properly from the server, if client window is closed.
   */
  public void logoutNoShutdown() {
    userStore.reconnectID().update(old -> Optional.empty());
    connection.sendMessage(new GameLeaveReq());
    connection.sendMessage(new LobbyLeaveReq());
    connection.sendMessage(new LogoutReq());
    
    try {
      connection.close();
    } catch (IOException e) {
      Logger.error(e); //TODO: sensible stuff
    }
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // No problem, since we're shutting down anyway
    }
  }
}
