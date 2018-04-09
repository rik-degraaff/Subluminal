package tech.subluminal.client.presentation;

/**
 * Presenter for the user interatcion.
 */
public interface UserPresenter {


    /**
     * Function that should be called when login succeeded.
     */
    void loginSucceeded();

    /**
     * Gets called when the client got logged out.
     */
    void logoutSucceeded();

    /**
     * Fired when a username got successfully changed.
     */
    void nameChangeSucceeded();

    void setUserDelegate(Delegate delegate);

    void onPlayerJoin(String username);

    void onPlayerLeave(String username);

    void onPlayerUpdate(String oldUsername, String newUsername);

    /**
     * Delegate the UserStore can subscribe to.
     */
    public static interface Delegate {

        /**
         * Fired when a user tries to change his name.
         *
         * @param username the desired new username.
         */
        void changeUsername(String username);


        /**
         * Fired when a user has to be logged out.
         */
        void logout();
    }

}
