package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.*;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.User;

import java.io.IOException;

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
     * @param userStore  to hold the current users.
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
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace(); //TODO: sensible stuff
        }
        System.exit(0);
    }
}
